package com.sql.ide.services;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

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

	    DataSourceRequest getConnectionOfUser(String username, String connectionName) throws Exception;
    
	    String  readFileAsString(String filePath) throws IOException;
	    
	    DataSourceResponse deleteConnection(String username, String connectionName) throws Exception;

		DataSourceResponse updateConnection(@Valid DataSourceRequest dataSourceRequest) throws Exception;

}
