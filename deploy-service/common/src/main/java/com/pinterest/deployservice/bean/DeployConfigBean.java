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
 * create_date     BIGINT              NOT NULL,
 * create_user  VARCHAR(64)         NOT NULL,
 * update_date     BIGINT              NOT NULL,
 * update_user  VARCHAR(64)         NOT NULL,
 * PRIMARY KEY (deploy_id, config_name)
 * );
 *
 */

public class DeployConfigBean implements Updatable {
    @JsonProperty("deployId")
    private String deploy_id;

    @JsonProperty("configName")
    private String config_name;

    @JsonProperty("configType")
    private String config_type;

    @JsonProperty("configValue")
    private String config_value;

    @JsonProperty("createDate")
    private Long create_date;

    @JsonProperty("createUser")
    private String create_user;

    @JsonProperty("updateDate")
    private Long update_date;

    @JsonProperty("updateUser")
    private String update_user;

    public String getDeploy_id() {
        return deploy_id;
    }

    public void setDeploy_id(String deploy_id) {
        this.deploy_id = deploy_id;
    }

    public String getConfig_name() {
        return config_name;
    }

    public void setConfig_name(String config_name) {
        this.config_name = config_name;
    }

    public String getConfig_type() {
        return config_type;
    }

    public void setConfig_type(String config_type) {
        this.config_type = config_type;
    }

    public String getConfig_value() {
        return config_value;
    }

    public void setConfig_value(String config_value) {
        this.config_value = config_value;
    }


    public Long getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Long create_date) {
        this.create_date = create_date;
    }

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    public Long getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Long update_date) {
        this.update_date = update_date;
    }

    public String getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(String update_user) {
        this.update_user = update_user;
    }

    @Override
    public SetClause genSetClause() {
        SetClause clause = new SetClause();
        clause.addColumn("deploy_id", deploy_id);
        clause.addColumn("config_name", config_name);
        clause.addColumn("config_type", config_type);
        clause.addColumn("config_value", config_value);
        clause.addColumn("create_date", create_date);
        clause.addColumn("create_user", create_user);
        clause.addColumn("update_date", update_date);
        clause.addColumn("update_user", update_user);
        return clause;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
