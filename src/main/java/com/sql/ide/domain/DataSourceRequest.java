package com.sql.ide.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceRequest {

    private String username;

    private String password;

    private String url;

    private String driver;
}