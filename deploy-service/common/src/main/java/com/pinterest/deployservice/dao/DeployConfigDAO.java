/**
 * Copyright 2016 Pinterest, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *     http://www.apache.org/licenses/LICENSE-2.0
 *    
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pinterest.deployservice.dao;

import com.pinterest.deployservice.bean.DeployBean;
import com.pinterest.deployservice.bean.DeployConfigBean;
import com.pinterest.deployservice.bean.DeployQueryResultBean;
import com.pinterest.deployservice.bean.UpdateStatement;
import com.pinterest.deployservice.db.DeployQueryFilter;
import org.joda.time.Interval;

import java.util.List;

/**
 * A collection of methods to help read table DEPLOY_CONFIGS
 */
public interface DeployConfigDAO {
    void insert(DeployConfigBean deployConfigBean) throws Exception;

    DeployConfigBean getById(String deployId) throws Exception;

    void update(String deployId, DeployConfigBean deployConfigBean) throws Exception;
//    UpdateStatement genUpdateStatement(String deployId, DeployConfigBean deployConfigBean);
//    UpdateStatement genInsertStatement(DeployConfigBean deployConfigBean);

    void delete(String deployId) throws Exception;

}
