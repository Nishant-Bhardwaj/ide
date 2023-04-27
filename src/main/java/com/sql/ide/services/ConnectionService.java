package com.sql.ide.services;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sql.ide.domain.DataSourceRequest;
import com.sql.ide.domain.DataSourceResponse;

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
    
    String  readFileAsString(String filePath) throws IOException;

}
