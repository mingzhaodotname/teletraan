/**
 * Copyright 2016 Pinterest, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pinterest.deployservice.db;

import com.pinterest.deployservice.bean.Pg2PackageBean;
import com.pinterest.deployservice.bean.SetClause;
import com.pinterest.deployservice.dao.Pg2PackagesDAO;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

public class DBPg2PackagesDAOImpl implements Pg2PackagesDAO {

    private static final String INSERT_PACKAGE_TEMPLATE = "INSERT INTO pg_and_packages SET %s";
    private static final String DELETE_PACKAGE = "DELETE FROM pg_and_packages WHERE package_id=?";
    private static final String GET_PACKAGE_BY_ID = "SELECT * FROM pg_and_packages WHERE package_id=?";

    private static final String GET_PACKAGES_BY_GROUP_ID =
        "SELECT * FROM pg_and_packages WHERE group_id=? ORDER BY publish_date ASC";


    private BasicDataSource dataSource;

    public DBPg2PackagesDAOImpl(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void insert(Pg2PackageBean Pg2PackageBean) throws Exception {
        SetClause setClause = Pg2PackageBean.genSetClause();
        String clause = String.format(INSERT_PACKAGE_TEMPLATE, setClause.getClause());
        new QueryRunner(dataSource).update(clause, setClause.getValueArray());
    }

    @Override
    public void delete(String packageId) throws Exception {
        new QueryRunner(dataSource).update(DELETE_PACKAGE, packageId);
    }

    @Override
    public Pg2PackageBean getById(String packageId) throws Exception {
        ResultSetHandler<Pg2PackageBean> h = new BeanHandler<>(Pg2PackageBean.class);
        return new QueryRunner(dataSource).query(GET_PACKAGE_BY_ID, h, packageId);
    }

    @Override
    public List<Pg2PackageBean> getByGroupId(String groupId)
            throws Exception {
        ResultSetHandler<List<Pg2PackageBean>> h = new BeanListHandler<>(Pg2PackageBean.class);
        return new QueryRunner(dataSource)
                .query(GET_PACKAGES_BY_GROUP_ID, h, groupId);
    }
}
