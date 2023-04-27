package com.sql.ide.services.impl;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

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
	public String fetchUserConnections(String username) {
		logger.info("Fetching connections for User: "+ username);
		String connections = null;
		Path filepath = (Path) Paths.get("sql_resource", username + ".txt").toAbsolutePath();

		String path = String.valueOf(filepath);

		File file = new File(path);

		if (file.exists()&& file.length()!=0) {

			
			try {
				connections = connectionService.readFileAsString(path);
				connections = cryptoService.decrypt(connections);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return connections;
		} 
		return "User doesn't exist";
	}
}
