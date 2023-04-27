package com.sql.ide.services;

import com.sql.ide.domain.DataSourceRequest;
import com.sql.ide.domain.QueryRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
   

	String fetchUserConnections(String username);

  
}
