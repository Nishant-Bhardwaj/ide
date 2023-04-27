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
public class QueryResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Object response;
}
