package com.sql.ide.domain;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotBlank
    private String username;

    private String password;

    private String url;

    private String driver;

    @NotBlank
    @NotNull
    private String connectionName;
}