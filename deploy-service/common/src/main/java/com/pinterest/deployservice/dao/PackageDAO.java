/**
 * Copyright 2016 Pinterest, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pinterest.deployservice.dao;

import com.pinterest.deployservice.bean.PackageBean;

import java.util.List;

/**
 * A collection of methods to help interact with table BUILDS
 */
public interface PackageDAO {
    void insert(PackageBean PackageBean) throws Exception;
    void delete(String packageId) throws Exception;

    PackageBean getById(String packageId) throws Exception;


//    List<String> getBuildNames(String nameFilter, int pageIndex, int pageSize) throws Exception;

    List<PackageBean> getByGroupId(String groupId) throws Exception;
}
