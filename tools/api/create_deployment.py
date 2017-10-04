#!/usr/bin/python
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

import os
import subprocess
import traceback
import time
import string
import random
import requests

def create_deploy(env_name, stage_name, build_id, description, priority):
    publish_deploy_url = "http://localhost:8080/v1/envs/{}/{}/deploys?".format(env_name, stage_name)
    publish_deploy_url += 'buildId=' + build_id
    publish_deploy_url += '&priority=' + priority

    headers = {'Content-type': 'application/json'}
#    deploy = {}
#    deploy['name'] = deploy_name
#    deploy['repo'] = 'local'
#    deploy['branch'] = branch
#    deploy['commit'] = commit
#    deploy['commitDate'] = int(round(time.time()))
#    deploy['artifactUrl'] = deploy_path
#    deploy['publishInfo'] = deploy_path
#    r = requests.post(publish_deploy_url, json=deploy, headers=headers)

    r = requests.post(publish_deploy_url, headers=headers)
    if 200 <= r.status_code < 300:
        print "Successfully create deployment" 
    else:
        print "Error creating deployment. Status code = %s, response = %s" % (str(r.status_code),
                                                                                                 str(r.text))
    #return deploy


def main():
    try:
        create_deploy('deploy-sentinel', 'alpha', 'lKRGngcBTCKTiyTXHEbzhA_0639166', 'test description', 'my priority')
    except Exception as e:
        print traceback.format_exc()
        return None, e.message, 1


if __name__ == "__main__":
    main()
