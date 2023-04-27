package com.sql.ide.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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

    @NotBlank
    @NotNull
    private String connectionName;
}