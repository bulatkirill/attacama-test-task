package com.ataccama.test.testtask.repository.impl;

import com.ataccama.test.testtask.model.DBConnection;
import com.ataccama.test.testtask.model.metadata.DBColumn;
import com.ataccama.test.testtask.model.metadata.DBSchema;
import com.ataccama.test.testtask.model.metadata.DBTable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;

/**
 * Concrete implementation of the MySql database provider for providing different SQL metadata operations
 * Methods are not implemented and class exists only to show a way to extend application in future versions
 */
//@Repository
//@Qualifier("MySqlDbMetadataRepositoryImpl")
public class MySqlDbMetadataRepositoryImpl extends DBMetadataRepositoryImpl {

    @Override
    protected String findAllSchemaQueryString(DBConnection dbConnection) {
        return null;
    }

    @Override
    protected DBSchema extractDbSchema(ResultSet resultSet) {
        return null;
    }

    @Override
    protected String findAllTablesQueryString(DBConnection dbConnection, String schemaName) {
        return null;
    }

    @Override
    protected DBTable extractDbTable(ResultSet resultSet) {
        return null;
    }

    @Override
    protected String findAllColumnsQueryString(DBConnection dbConnection, String schemaName, String tableName) {
        return null;
    }

    @Override
    protected DBColumn extractDbColumn(ResultSet resultSet) {
        return null;
    }

    @Override
    protected String getDataQueryString(DBConnection dbConnection, String schemaName, String tableName) {
        return null;
    }

    @Override
    public String getProvider() {
        return null;
    }
}
