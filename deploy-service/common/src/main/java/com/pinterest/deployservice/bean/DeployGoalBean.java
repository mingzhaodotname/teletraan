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

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.util.Map;
import java.util.List;

public class DeployGoalBean {

    public class TargetState {
        public TargetState(String packages, String baseline, String builds) {
            this.packages = packages;
            this.baseline = baseline;
            this.builds = builds;
        }

//        List<String> packages;
        String packages;
        String baseline;
        String builds;

//        List<String> builds;
        public String getPackages() {
            return packages;
        }

        public String getBaseline() {
            return baseline;
        }

        public String getBuilds() {
            return builds;
        }

    }

    private String deployId;
    private DeployType deployType;
    private String envId;
    private String envName;
    private String stageName;
    private DeployStage deployStage;
    private BuildBean build;
    private String deployAlias;
    private Map<String, String> agentConfigs;
    private Map<String, String> scriptVariables;
    private Boolean firstDeploy;
    private Boolean isDocker;
    private TargetState targetState;

    public String getDeployId() {
        return deployId;
    }

    public void setDeployId(String deployId) {
        this.deployId = deployId;
    }

    public String getEnvId() {
        return envId;
    }

    public void setEnvId(String envId) {
        this.envId = envId;
    }

    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public DeployStage getDeployStage() {
        return deployStage;
    }

    public void setDeployStage(DeployStage deployStage) {
        this.deployStage = deployStage;
    }

    public BuildBean getBuild() {
        return build;
    }

    public void setBuild(BuildBean build) {
        this.build = build;
    }

    public String getDeployAlias() {
        return deployAlias;
    }

    public void setDeployAlias(String deployAlias) {
        this.deployAlias = deployAlias;
    }

    public Map<String, String> getAgentConfigs() {
        return agentConfigs;
    }

    public void setAgentConfigs(Map<String, String> agentConfigs) {
        this.agentConfigs = agentConfigs;
    }

    public Map<String, String> getScriptVariables() {
        return scriptVariables;
    }

    public void setScriptVariables(Map<String, String> scriptVariables) {
        this.scriptVariables = scriptVariables;
    }

    public Boolean getFirstDeploy() { return firstDeploy; }

    public void setFirstDeploy(Boolean firstDeploy) { this.firstDeploy = firstDeploy; }

    public DeployType getDeployType() {
        return deployType;
    }

    public void setDeployType(DeployType deployType) {
        this.deployType = deployType;
    }

    public Boolean getIsDocker() {
        return isDocker;
    }

    public void setIsDocker(Boolean isDocker) {
        this.isDocker = isDocker;
    }

    public void setTargetState(TargetState targetState) {
        this.targetState = targetState;
        if (this.targetState == null) {
            this.targetState = new TargetState(
                    "target pacakges", "test baseline", " test builds");
        }
    }

    public TargetState getTargetState() {return this.targetState; }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
