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
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Keep the bean and table in sync
 * <p>
 * CREATE TABLE builds (
 * );
 */
public class Pg2PackageBean implements Updatable {
    @JsonProperty("packageId")
    private String package_id;

    @JsonProperty("packageName")
    private String package_name;

    @JsonProperty("packageVersion")
    private String package_version;

    @JsonProperty("packageUrl")
    private String package_url;

    @JsonProperty("buildId")
    private String build_id;

    @JsonProperty("groupId")
    private String group_id;

    @JsonProperty("publishInfo")
    private String publish_info;

    @JsonProperty("publisher")
    private String publisher;

    @JsonProperty("publishDate")
    private Long publish_date;


    public String getPackage_id() {
        return package_id;
    }

    public void setPackage_id(String package_id) {
        this.package_id = package_id;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getPackage_version() {
        return package_version;
    }

    public void setPackage_version(String package_version) {
        this.package_version = package_version;
    }

    public String getPackage_url() {
        return package_url;
    }

    public void setPackage_url(String package_url) {
        this.package_url = package_url;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getBuild_id() {
        return build_id;
    }

    public void setBuild_id(String build_id) {
        this.build_id = build_id;
    }

    public String getPublish_info() {
        return publish_info;
    }

    public void setPublish_info(String publish_info) {
        this.publish_info = publish_info;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Long getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(Long publish_date) {
        this.publish_date = publish_date;
    }

    @Override
    public SetClause genSetClause() {
        SetClause clause = new SetClause();
        clause.addColumn("package_id", package_id);
        clause.addColumn("package_name", package_name);
        clause.addColumn("package_version", package_version);
        clause.addColumn("package_url", package_url);
        clause.addColumn("build_id", build_id);
        clause.addColumn("group_id", group_id);
        clause.addColumn("publish_info", publish_info);
        clause.addColumn("publisher", publisher);
        clause.addColumn("publish_date", publish_date);
        return clause;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
