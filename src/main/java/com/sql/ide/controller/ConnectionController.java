package com.sql.ide.controller;

import com.sql.ide.domain.DataSourceRequest;
import com.sql.ide.domain.DataSourceResponse;
import com.sql.ide.services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/connection")
public class ConnectionController {

    @Autowired
    ConnectionService conService;

    @PostMapping("/create")
    public ResponseEntity<DataSourceResponse> createConnection(@RequestBody DataSourceRequest dataSourceRequest) throws Exception {
        return new ResponseEntity<>(conService.createConnection(dataSourceRequest), HttpStatus.OK);
    }
}
