package com.sql.ide.services;

import com.sql.ide.domain.DataSourceRequest;
import com.sql.ide.domain.QueryRequest;
import org.springframework.stereotype.Service;

/**
 * Query relate service
 *
 * @author Nishant Bhardwaj
 */
@Service
public interface QueryService {
    DataSourceRequest validateQueryRequest(QueryRequest queryRequest, String username) throws Exception;

    Object executeQuery(QueryRequest queryRequest, DataSourceRequest dataSource) throws Exception;
    
    DataSourceRequest validateShowRequest(QueryRequest queryRequest, String username, String fetchFlag) throws Exception;

	Object executeShowQuery(QueryRequest queryRequest, DataSourceRequest dataSourceRequest)
			throws Exception;
}
