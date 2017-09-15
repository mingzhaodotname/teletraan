# Copyright 2016 Pinterest, Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#  
#     http://www.apache.org/licenses/LICENSE-2.0
#    
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

import argparse
from deployd.common.config import Config
from deployd.common.status_code import Status
from deployd.download.download_helper_factory import DownloadHelperFactory
import os
from os import listdir
from os.path import isfile
import re
import subprocess
import sys
import tarfile
import zipfile
import logging
import traceback

log = logging.getLogger(__name__)


INSTALL_PACKAGES = '''#!/bin/bash

set -e

apt install {}
'''


class DeployException(Exception):
    def __init__(self, message):
        super(DeployException, self).__init__(message)


class Downloader(object):

    def __init__(self, config, build, url, env_name, packages=None):
        self._matcher = re.compile(r'^.*?[.](?P<ext>tar\.gz|tar\.bz2|\w+)$')
        self._base_dir = config.get_builds_directory()  # /tmp/deployd/builds/
        self._build_name = env_name
        self._build = build
        self._url = url
        self._config = config
        self._packages = packages

    def _get_extension(self, url):
        return self._matcher.match(url).group('ext')

    def check_package_deps(self, build_dir):
        pkg_dir = os.path.join(build_dir, 'packages')
        pkg_files = [os.path.join(pkg_dir, file) for file in listdir(pkg_dir)
                     if isfile(os.path.join(pkg_dir, file))]

        dry_run_cmd = ['apt', 'install', '--dry-run']
        dry_run_cmd.extend(pkg_files)
        log.info('minglog: check dependencies cmd: {}'.format(dry_run_cmd))
        output = subprocess.check_output(dry_run_cmd)
        log.info('minglog: check dependencies cmd output: {}'.format(output))

        MORE_PACKAGES_NEEDED = 'The following additional packages will be installed'

        if MORE_PACKAGES_NEEDED in output:
            more_packages = ''
            lines = output.splitlines()
            for i in range(len(lines)):
                if MORE_PACKAGES_NEEDED in lines[i] and (i+1) < len(lines):
                    more_packages = lines[i+1].strip()
            raise DeployException('More package dependencies needed: {}'.format(more_packages))
        else:
            log.info('minglog: good, no need for more dependencies')

    def create_teletraan_content(self, build_dir):
        teletraan_dir = os.path.join(build_dir, 'teletraan/')
        if not os.path.exists(teletraan_dir):
            log.info('minglog: Create directory {}.'.format(teletraan_dir))
            os.mkdir(teletraan_dir)

            pkg_dir = os.path.join(build_dir, 'packages')
            pkg_files = [os.path.join(pkg_dir, file) for file in listdir(pkg_dir)
                         if isfile(os.path.join(pkg_dir, file))]

            # packages = ['../packages/helloworld.deb']
            restarting_file = os.path.join(teletraan_dir, 'RESTARTING')
            with open(restarting_file, 'w') as file:
                file.write(INSTALL_PACKAGES.format(' '.join(pkg_files)))
                log.info('minglog: wrote restarting_file: {}'.format(restarting_file))
        else:
            log.info('minglog: NOOP: teletraan directory already existed: {}'.format(teletraan_dir))

    def download_packages(self, build_dir):
        """Download packages to path/to/build dir/packages/"""
        if self._packages is None:
            return

        pkg_dir = os.path.join(build_dir, 'packages')
        if not os.path.exists(pkg_dir):
            log.info('Create directory {}.'.format(pkg_dir))
            os.mkdir(pkg_dir)

        pkg_urls = self._packages.split(',')
        for pkg_url in pkg_urls:
            pkg_name = pkg_url.split('/')[-1]
            local_full_fn = os.path.join(build_dir, 'packages', pkg_name)
            downloader = DownloadHelperFactory.gen_downloader(pkg_url, self._config)
            if downloader:
                status = downloader.download(local_full_fn)
                if status != Status.SUCCEEDED:
                    raise Exception('Failed to download package: ' + pkg_url)
            else:
                raise Exception('Failed to download package: ' + pkg_url)

    def download(self):
        extension = self._get_extension(self._url.lower())
        local_fn = u'{}-{}.{}'.format(self._build_name, self._build, extension)
        local_full_fn = os.path.join(self._base_dir, local_fn)
        extracted_file = os.path.join(self._base_dir, '{}.extracted'.format(self._build))
        if os.path.exists(extracted_file):
            log.info("{} exists. tarball have already been extracted.".format(extracted_file))
            return Status.SUCCEEDED

        # working_dir: /tmp/deployd/builds/xxxxxx/
        working_dir = os.path.join(self._base_dir, self._build)
        if not os.path.exists(working_dir):
            log.info('Create directory {}.'.format(working_dir))
            os.mkdir(working_dir)

        downloader = DownloadHelperFactory.gen_downloader(self._url, self._config)
        if downloader:
            status = downloader.download(local_full_fn)
            if status != Status.SUCCEEDED:
                return status
        else:
            return Status.FAILED

        curr_working_dir = os.getcwd()
        os.chdir(working_dir)  # working_dir is build_dir, e.g. /tmp/deployd/builds/xxxxxx/
        try:
            if extension == 'zip':
                log.info("unzip files to {}".format(working_dir))
                with zipfile.ZipFile(local_full_fn) as zfile:
                    zfile.extractall(working_dir)
            if extension == 'deb':
                pass
            else:
                # tar file.
                log.info("untar files to {}".format(working_dir))
                with tarfile.open(local_full_fn) as tfile:
                    tfile.extractall(working_dir)

            # minglog -
            self.download_packages(working_dir)

            self.check_package_deps(working_dir)

            self.create_teletraan_content(working_dir)

            # change the working directory back
            os.chdir(curr_working_dir)
            with file(extracted_file, 'w'):
                pass
            log.info("Successfully extracted {} to {}".format(local_full_fn, working_dir))
        except tarfile.TarError as e:
            status = Status.FAILED
            log.error("Failed to extract files: {}".format(e.message))
        except OSError as e:
            status = Status.FAILED
            log.error("Failed: {}".format(e.message))
        except Exception:
            status = Status.FAILED
            log.error(traceback.format_exc())
        finally:
            return status


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('-f', '--config-file', dest='config_file', required=False,
                        help='the deploy agent config file path.')
    parser.add_argument('-v', '--build-version', dest='build', required=True,
                        help="the current deploying build version for the current environment.")
    parser.add_argument('-u', '--url', dest='url', required=True,
                        help="the url of the source code where the downloader would download from. "
                             "The url can start"
                             "with s3:// or https://")
    parser.add_argument('-e', '--env-name', dest='env_name', required=True,
                        help="the environment name currently in deploy.")
    parser.add_argument('-p', '--packages', dest='packages', required=False,
                        help="packages to deploy.")
    args = parser.parse_args()
    config = Config(args.config_file)
    logging.basicConfig(level=config.get_log_level())

    log.info("Start to download the build.")
    status = Downloader(config, args.build, args.url, args.env_name, args.packages).download()
    if status != Status.SUCCEEDED:
        log.error("Download failed.")
        sys.exit(1)
    else:
        log.info("Download succeeded.")
        sys.exit(0)

if __name__ == '__main__':
    main()
