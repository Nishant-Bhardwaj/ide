package com.sql.ide.controller;

import com.sql.ide.domain.DataSourceRequest;
import com.sql.ide.domain.QueryRequest;
import com.sql.ide.services.QueryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/query")
public class QueryController {

    private Logger logger = LogManager.getLogger(QueryController.class);

    @Autowired
    QueryService queryService;

    @PostMapping(path="/execute")
    public ResponseEntity<Object> executeQuery(@RequestBody QueryRequest queryRequest) throws Exception {
        logger.info("Execute Query endpoint: START");

        // TODO: from header take: username, token
        String username = null;

        DataSourceRequest dataSource = queryService.validateQueryRequest(queryRequest, username);

        if(null == dataSource){
            return new ResponseEntity<>("Please select correct Database!", HttpStatus.OK);
        }

        return new ResponseEntity<>(queryService.executeQuery(queryRequest, dataSource), HttpStatus.OK);
    }
}
