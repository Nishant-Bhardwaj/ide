package com.sql.ide.services;

import com.sql.ide.domain.DataSourceRequest;
import com.sql.ide.domain.DataSourceResponse;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Services related to database connection
 *
 * @author Nishant Bhardwaj
 */
@Service
public interface ConnectionService {

    DataSourceResponse createConnection(DataSourceRequest dataSourceRequest) throws Exception;
    
    DataSourceRequest selectConnection(DataSourceRequest dataSourceRequest) throws Exception;

    DataSourceRequest getActiveConnection();

    DataSourceRequest getConnectionOfUser(String username, String datasourceUrl) throws Exception;
}
