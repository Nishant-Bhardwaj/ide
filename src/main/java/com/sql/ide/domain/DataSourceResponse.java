package com.sql.ide.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;

    private String connectionArray;
}
