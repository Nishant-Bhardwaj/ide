package com.sql.ide.services.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sql.ide.domain.DataSourceRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sql.ide.services.ConnectionService;
import com.sql.ide.services.CryptoService;
import com.sql.ide.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	private Logger logger = LogManager.getLogger(UserServiceImpl.class);


	@Autowired
	CryptoService cryptoService;
	
	@Autowired
	ConnectionService connectionService;



	@Override
	public List<DataSourceRequest> fetchUserConnections(String username) throws Exception {
		logger.info("Fetching connections for User: "+ username);

		Path filepath = (Path) Paths.get("sql_resource", username + ".txt").toAbsolutePath();

		String path = String.valueOf(filepath);

		File file = new File(path);

		if (file.exists()&& file.length()!=0) {

			
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				String jsonString = cryptoService.decrypt(new String(Files.readAllBytes(Paths.get(path))));

				List<DataSourceRequest> allConnectionDetails = objectMapper.readValue(jsonString,
						new TypeReference<List<DataSourceRequest>>() {
						});

				return allConnectionDetails;

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return null;
		} 
		throw new Exception("User doesn't exist");
	}
}
