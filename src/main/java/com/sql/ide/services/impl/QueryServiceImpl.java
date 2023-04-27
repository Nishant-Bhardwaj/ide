package com.sql.ide.services.impl;

import com.sql.ide.domain.DataSourceRequest;
import com.sql.ide.domain.QueryRequest;
import com.sql.ide.services.ConnectionService;
import com.sql.ide.services.CryptoService;
import com.sql.ide.services.QueryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Query relate service implementations
 *
 * @author Nishant Bhardwaj
 */
@Service
public class QueryServiceImpl implements QueryService {

    private Logger logger = LogManager.getLogger(QueryServiceImpl.class);

    @Autowired
    ConnectionService connectionService;

    @Autowired
    CryptoService cryptoService;

    @Override
    public DataSourceRequest validateQueryRequest(QueryRequest queryRequest, String username){
        logger.info("Validating Query req: "+ queryRequest.getQuery());
        logger.info("Validating Database req: "+ queryRequest.getDatasourceUrl());

        DataSourceRequest activeDatasource = connectionService.getActiveConnection();

        if(activeDatasource.getUrl().equals(queryRequest.getDatasourceUrl())
            && activeDatasource.getUsername().equals(username)){
            return activeDatasource;
        }

        return null;
    }

    @Override
    public Object executeQuery(QueryRequest queryRequest, DataSourceRequest dataSourceRequest) throws Exception {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dataSourceRequest.getDriver());
        dataSource.setUrl(dataSourceRequest.getUrl());
        dataSource.setUsername(dataSourceRequest.getUsername());
        dataSource.setPassword(cryptoService.decrypt(dataSourceRequest.getPassword()));

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        try{
            List<Map<String, Object>> queryResults = jdbcTemplate.queryForList(queryRequest.getQuery());
            return queryResults;
        }catch (Exception e){
            jdbcTemplate.execute(queryRequest.getQuery());
        }

        return null;
    }
}
