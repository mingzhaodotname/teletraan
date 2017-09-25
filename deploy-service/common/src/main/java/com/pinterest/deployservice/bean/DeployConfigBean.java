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
package com.pinterest.deployservice.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * Keep the bean and table in sync
 * <p/>
 * CREATE TABLE IF NOT EXISTS deploy_configs (
 * deploy_id   VARCHAR(22)         NOT NULL,
 * config_name  VARCHAR(64)         NOT NULL,
 * config_type  VARCHAR(64)         NOT NULL,
 * config_value  VARCHAR(1024)         NOT NULL,
 * PRIMARY KEY (deploy_id, config_name)
 * )
 *
 */

public class DeployConfigBean implements Updatable {
    @JsonProperty("id")
    private String deploy_id;

    @JsonProperty("configName")
    private String config_name;

    @JsonProperty("configType")
    private String config_type;

    @JsonProperty("configValue")
    private DeployType config_value;

    @JsonProperty("createDate")
    private Long create_date;

    @JsonProperty("updateDate")
    private Long update_date;

    @JsonProperty("updateUser")
    private Long update_user;

    public String getDeploy_id() {
        return deploy_id;
    }

    public void setDeploy_id(String deploy_id) {
        this.deploy_id = deploy_id;
    }


    @Override
    public SetClause genSetClause() {
        SetClause clause = new SetClause();
        clause.addColumn("deploy_id", deploy_id);
        clause.addColumn("config_name", config_name);
        clause.addColumn("config_type", config_type);
        clause.addColumn("config_value", config_value);
        clause.addColumn("create_date", create_date);
        clause.addColumn("update_date", update_date);
        clause.addColumn("update_user", update_user);
        return clause;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
