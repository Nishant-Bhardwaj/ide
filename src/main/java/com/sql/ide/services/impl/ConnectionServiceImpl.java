package com.sql.ide.services.impl;

import com.sql.ide.domain.DataSourceRequest;
import com.sql.ide.services.ConnectionService;
import com.sql.ide.services.CryptoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

@Service
public class ConnectionServiceImpl implements ConnectionService {

    private Logger logger = LogManager.getLogger(ConnectionServiceImpl.class);

    @Autowired
    CryptoService cryptoService;

    @Override
    public String createConnection(DataSourceRequest dataSourceRequest) throws Exception {
        logger.info("Create Connection Service :: Start");

        // Create DataSource:
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dataSourceRequest.getDriver());
        dataSource.setUrl(dataSourceRequest.getUrl());
        dataSource.setUsername(dataSourceRequest.getUsername());
        dataSource.setPassword(cryptoService.decrypt(dataSourceRequest.getPassword()));

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        try{
            jdbcTemplate.execute("show tables");

            // txt file(userId.txt) exist for user in "sql_resource" folder ?
                // create one
                // create Json

                //json fetch -> decrypt -> check already present
                // -> (if not)append/add new connection -> encrypt -> save in txt
                // -> TODO: Connection already exist Exception

            //create txt file for user and save jdbcTemplate after encrypt:

        }catch (Exception e){
            // invalid details, return error
        }



        return null;
    }
}
