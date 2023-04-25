package com.sql.ide.services;

import com.sql.ide.domain.DataSourceRequest;
import org.springframework.stereotype.Service;

@Service
public interface ConnectionService {

    String createConnection(DataSourceRequest dataSourceRequest) throws Exception;
}
