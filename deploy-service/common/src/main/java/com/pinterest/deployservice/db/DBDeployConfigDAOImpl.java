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
package com.pinterest.deployservice.db;

import com.pinterest.deployservice.bean.*;
import com.pinterest.deployservice.common.StateMachines;
import com.pinterest.deployservice.dao.DeployConfigDAO;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang.StringUtils;
import org.joda.time.Interval;

import java.sql.Connection;
import java.util.List;

public class DBDeployConfigDAOImpl implements DeployConfigDAO {

//    private static final Logger LOG = LoggerFactory.getLogger(DBDeployDAOImpl.class);

    private static final String TABLE_NAME = "deploy_configs";

    private static final String INSERT_DEPLOYMENT_TEMPLATE =
        "INSERT INTO %s SET".format(TABLE_NAME) + "%s";

    private static final String GET_DEPLOYMENT_BY_ID =
            "SELECT * FROM %s WHERE deploy_id=?".format(TABLE_NAME);

    private static final String UPDATE_DEPLOYMENT_TEMPLATE =
        "UPDATE deploys SET %s WHERE deploy_id=?";

    private static final String UPDATE_DEPLOY_SAFELY_TEMPLATE =
        "UPDATE deploys SET %s WHERE deploy_id=? AND state=?";

    private static final String DELETE_DEPLOYMENT =
        "DELETE FROM %s WHERE deploy_id=?".format(TABLE_NAME);

    private BasicDataSource dataSource;

    public DBDeployConfigDAOImpl(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void insert(DeployConfigBean deployConfigBean) throws Exception {
        SetClause setClause = deployConfigBean.genSetClause();
        String clause = String.format(INSERT_DEPLOYMENT_TEMPLATE, setClause.getClause());
        new QueryRunner(dataSource).update(clause, setClause.getValueArray());
    }

    @Override
    public DeployConfigBean getById(String deploymentId) throws Exception {
        ResultSetHandler<DeployConfigBean> h = new BeanHandler<>(DeployConfigBean.class);
        return new QueryRunner(dataSource).query(GET_DEPLOYMENT_BY_ID, h, deploymentId);
    }


    @Override
    public void delete(String deployId) throws Exception {
        new QueryRunner(dataSource).update(DELETE_DEPLOYMENT, deployId);
    }

    @Override
    public void update(String deployId, DeployConfigBean deployConfigBean) throws Exception {
        SetClause setClause = deployConfigBean.genSetClause();
        String clause = String.format(UPDATE_DEPLOYMENT_TEMPLATE, setClause.getClause());
        setClause.addValue(deployId);
        new QueryRunner(dataSource).update(clause, setClause.getValueArray());
    }

//    @Override
//    public UpdateStatement genUpdateStatement(String deployId, DeployConfigBean deployConfigBean) {
//        SetClause setClause = deployConfigBean.genSetClause();
//        String clause = String.format(UPDATE_DEPLOYMENT_TEMPLATE, setClause.getClause());
//        setClause.addValue(deployId);
//        return new UpdateStatement(clause, setClause.getValueArray());
//    }
//
//    @Override
//    public UpdateStatement genInsertStatement(DeployConfigBean deployConfigBean) {
//        SetClause setClause = deployConfigBean.genSetClause();
//        String clause = String.format(INSERT_DEPLOYMENT_TEMPLATE, setClause.getClause());
//        return new UpdateStatement(clause, setClause.getValueArray());
//    }

}
