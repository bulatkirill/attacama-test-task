package com.ataccama.test.testtask.service.impl;

import com.ataccama.test.testtask.model.DBConnection;
import com.ataccama.test.testtask.model.exception.BusinessException;
import com.ataccama.test.testtask.model.exception.UnsupportedProviderException;
import com.ataccama.test.testtask.model.metadata.DBColumn;
import com.ataccama.test.testtask.model.metadata.DBSchema;
import com.ataccama.test.testtask.model.metadata.DBTable;
import com.ataccama.test.testtask.repository.DBMetadataRepository;
import com.ataccama.test.testtask.service.DbMetadataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DbMetadataServiceImpl implements DbMetadataService {

    private static final Logger logger = LoggerFactory.getLogger(DbMetadataServiceImpl.class);

    private List<DBMetadataRepository> dbMetadataRepositories;

    private static final Map<String, DBMetadataRepository> repositoryMap = new HashMap<>();

    @PostConstruct
    public void postConstruct() {
        for (DBMetadataRepository repository : dbMetadataRepositories) {
            repositoryMap.put(repository.getProvider(), repository);
        }
    }

    @Override
    public List<DBSchema> findAllSchemas(DBConnection dbConnection) throws UnsupportedProviderException {
        try {
            return getRepository(dbConnection.getProvider()).findAllSchemas(dbConnection);
        } catch (SQLException e) {
            logger.error("Process of selecting all schemas failed for database connection with id = {}", dbConnection.getId());
            throw new BusinessException("Operation failed. Try again later.");
        }
    }

    @Override
    public List<DBTable> findAllTables(DBConnection dbConnection, String schemaName) throws UnsupportedProviderException {
        try {
            return getRepository(dbConnection.getProvider()).findAllTables(dbConnection, schemaName);
        } catch (SQLException e) {
            logger.error("Process of selecting all tables failed for database connection with id = {} and schema = {}",
                    dbConnection.getId(), schemaName);
            throw new BusinessException("Operation failed. Try again later.");
        }
    }

    @Override
    public List<DBColumn> findAllColumns(DBConnection dbConnection, String schemaName, String tableName) throws UnsupportedProviderException {
        try {
            return getRepository(dbConnection.getProvider()).findAllColumns(dbConnection, schemaName, tableName);
        } catch (SQLException e) {
            logger.error("Process of selecting all columns failed for database connection with id = {}, schema = {} and table = {}",
                    dbConnection.getId(), schemaName, tableName);
            throw new BusinessException("Operation failed. Try again later.");
        }
    }

    @Override
    public List<Map<String, Object>> findAllData(DBConnection dbConnection, String schemaName, String tableName) throws UnsupportedProviderException {
        try {
            return getRepository(dbConnection.getProvider()).findAllData(dbConnection, schemaName, tableName);
        } catch (SQLException e) {
            logger.error("Process of selecting data preview failed for database connection with id = {}, schema = {} and table = {}",
                    dbConnection.getId(), schemaName, tableName);
            throw new BusinessException("Operation failed. Try again later.");
        }
    }

    private DBMetadataRepository getRepository(String dbProvider) throws UnsupportedProviderException {
        DBMetadataRepository repository = repositoryMap.get(dbProvider);
        if (repository == null) {
            UnsupportedProviderException e = new UnsupportedProviderException
                    ("Specified database provider is not supported by this version of application.");
            logger.error(e.getMessage(), e);
            throw e;
        }
        return repository;
    }

    @Autowired
    public void setDbMetadataRepositories(List<DBMetadataRepository> dbMetadataRepositories) {
        this.dbMetadataRepositories = dbMetadataRepositories;
    }
}
