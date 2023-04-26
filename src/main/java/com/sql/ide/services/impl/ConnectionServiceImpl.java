package com.sql.ide.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sql.ide.domain.DataSourceRequest;
import com.sql.ide.services.ConnectionService;
import com.sql.ide.services.CryptoService;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.io.BufferedWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

@Service
public class ConnectionServiceImpl implements ConnectionService {

    private Logger logger = LogManager.getLogger(ConnectionServiceImpl.class);

    @Autowired
    CryptoService cryptoService;

    @Override
    public String createConnection(DataSourceRequest dataSourceRequest) throws Exception {
        logger.info("Create Connection Service :: Start");

        // Create DataSource:
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dataSourceRequest.getDriver());
        dataSource.setUrl(dataSourceRequest.getUrl());
        dataSource.setUsername(dataSourceRequest.getUsername());
        dataSource.setPassword(cryptoService.decrypt(dataSourceRequest.getPassword()));

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        try{
            jdbcTemplate.execute("show tables");
            // txt file(userId.txt) exist for user in "sql_resource" folder ?
            // create one
            // create Json
            //json fetch -> decrypt -> check already present
            // -> (if not)append/add new connection -> encrypt -> save in txt
            // -> TODO: Connection already exist Exception
            //create txt file for user and save jdbcTemplate after encrypt:
            
            
			String path = "C:\\sql_resource/".concat(dataSourceRequest.getUsername()).concat(".txt");
			File file = new File(path);
			if (file.exists()) {
				FileReader reader = new FileReader(path);
				byte[] jsonData = Files.readAllBytes(Paths.get(path));

				ObjectMapper objectMapper = new ObjectMapper();
				List<DataSourceRequest> allUserDetails = objectMapper.readValue(jsonData,
						new TypeReference<List<DataSourceRequest>>() {
						});

				logger.info(allUserDetails);
				int count = 0;
				for (DataSourceRequest userData : allUserDetails) {
					if (userData.getPassword().equalsIgnoreCase(dataSourceRequest.getPassword())
							&& userData.getDriver().equalsIgnoreCase(dataSourceRequest.getDriver())
							&& userData.getUrl().equalsIgnoreCase(dataSourceRequest.getUrl())
							&& userData.getUsername().equalsIgnoreCase(dataSourceRequest.getUsername())) {
						count++;
					}

				}
				if (count == 0) {
					allUserDetails.add(dataSourceRequest);
					ObjectMapper mapper = new ObjectMapper();
					String allJson = mapper.writeValueAsString(allUserDetails);
					FileWriter fw = new FileWriter(path);
					fw.write(allJson);
					fw.close();
				}
			} else {
				file.createNewFile();
				logger.info("File Created" + file.getName());
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				ObjectMapper mapper = new ObjectMapper();
				ArrayList<DataSourceRequest> dataSourceRequestList = new ArrayList<DataSourceRequest>();
				dataSourceRequestList.add(dataSourceRequest);
				String allJson = mapper.writeValueAsString(dataSourceRequestList);
				fw.write(allJson);
				fw.close();
			}

		} catch (Exception e) {
			// invalid details, return error
		}



        return null;
    }
    
    
}
