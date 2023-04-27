package com.sql.ide.services;

import com.sql.ide.domain.DataSourceRequest;
import com.sql.ide.domain.QueryRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {


	List<DataSourceRequest> fetchUserConnections(String username) throws Exception;

  
}
