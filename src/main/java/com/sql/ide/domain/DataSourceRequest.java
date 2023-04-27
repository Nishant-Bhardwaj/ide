package com.sql.ide.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private String username;

    private String password;

    private String url;

    private String driver;
}