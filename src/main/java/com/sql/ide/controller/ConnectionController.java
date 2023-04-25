package com.sql.ide.controller;

import com.sql.ide.domain.DataSourceRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/connection")
public class ConnectionController {

    @PostMapping("/create")
    public ResponseEntity<String> createConnection(@RequestBody DataSourceRequest dataSourceRequest){
        // TODO: Service..
        return new ResponseEntity<>("connection success!", HttpStatus.OK);
    }
}
