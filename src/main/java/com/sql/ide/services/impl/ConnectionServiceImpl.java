package com.sql.ide.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sql.ide.domain.DataSourceRequest;
import com.sql.ide.domain.DataSourceResponse;
import com.sql.ide.services.ConnectionService;
import com.sql.ide.services.CryptoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Services implementations related to database connection
 *
 * @author Nishant Bhardwaj
 */
@Service
public class ConnectionServiceImpl implements ConnectionService {

	private Logger logger = LogManager.getLogger(ConnectionServiceImpl.class);

	@Autowired
	CryptoService cryptoService;

	private DataSourceRequest activeDataSource;

	@Override
	public DataSourceResponse createConnection(DataSourceRequest dataSourceRequest) throws Exception {
		logger.info("Create Connection Service :: Start");

		// Create DataSource:
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(dataSourceRequest.getDriver());
		dataSource.setUrl(dataSourceRequest.getUrl());
		dataSource.setUsername(dataSourceRequest.getUsername());
		dataSource.setPassword(cryptoService.decrypt(dataSourceRequest.getPassword()));

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String connections;

		try {
			// txt file(userId.txt) exist for user in "sql_resource" folder ?
			// create one
			// create Json
			// json fetch -> decrypt -> check already present
			// -> (if not)append/add new connection -> encrypt -> save in txt
			// -> Connection already exist Exception
			// create txt file for user and save jdbcTemplate after encrypt:

			jdbcTemplate.execute("show databases");

			// select database/ open connection:
			activeDataSource = dataSourceRequest;

			Path filepath = (Path) Paths.get("sql_resource", dataSourceRequest.getUsername() + ".txt").toAbsolutePath();

			String path = String.valueOf(filepath);

			File file = new File(path);

			if (file.exists()) {

				ObjectMapper objectMapper = new ObjectMapper();
				String jsonString = cryptoService.decrypt(new String(Files.readAllBytes(Paths.get(path))));

				List<DataSourceRequest> allUserDetails = objectMapper.readValue(jsonString,
						new TypeReference<List<DataSourceRequest>>() {
						});

				logger.info(allUserDetails);
				int count = 0;

				for (DataSourceRequest userData : allUserDetails) {
					if (userData.getPassword().equalsIgnoreCase(dataSourceRequest.getPassword())
							&& userData.getDriver().equalsIgnoreCase(dataSourceRequest.getDriver())
							&& userData.getUrl().equalsIgnoreCase(dataSourceRequest.getUrl())
							&& userData.getConnectionName().equals(dataSourceRequest.getConnectionName())
							&& userData.getUsername().equalsIgnoreCase(dataSourceRequest.getUsername())) {
						count = 1;
						break;
					}

				}
				if (count == 0) {
					allUserDetails.add(dataSourceRequest);
					ObjectMapper mapper = new ObjectMapper();
					String allJson = mapper.writeValueAsString(allUserDetails);

					// Encrypt json:
					String encryptedJson = cryptoService.encrypt(allJson);

					FileWriter fw = new FileWriter(path);
					fw.write(encryptedJson);
					fw.close();
				} else {
					String msg = "Connection already exists!";
					return DataSourceResponse.builder().message(msg).build();
				}
			} else {
				Path dirpath = (Path) Paths.get("sql_resource");

				File dir = new File(dirpath.toString());

				if (!dir.exists())
					dir.mkdirs();

				file.createNewFile();
				logger.info("File Created: " + file.getName());
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				ObjectMapper mapper = new ObjectMapper();
				ArrayList<DataSourceRequest> dataSourceRequestList = new ArrayList<DataSourceRequest>();
				dataSourceRequestList.add(dataSourceRequest);
				String allJson = mapper.writeValueAsString(dataSourceRequestList);

				fw.write(cryptoService.encrypt(allJson));
				fw.close();
			}
			connections = readFileAsString(path);
			connections = cryptoService.decrypt(connections);

		} catch (Exception e) {
			// invalid details, return error
			String errMsg = "Invalid credentials!" + e.getMessage();
			logger.error(errMsg);
			return DataSourceResponse.builder().message(errMsg).build();
		}

		return DataSourceResponse.builder().connectionArray(connections).message("Connection Successful").build();

	}

	public String readFileAsString(String filePath) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));

		String line;
		while ((line = bufferedReader.readLine()) != null) {
			stringBuilder.append(line);
		}

		bufferedReader.close();
		return stringBuilder.toString();
	}

	/**
	 * Return the active database connection
	 *
	 * @author Nishant Bhardwaj
	 */
	@Override
	public DataSourceRequest getActiveConnection() {
		return this.activeDataSource;
	}

	/**
	 * Return connection/ datasource if it is present for user
	 *
	 * @param username
	 * @param connectionName
	 * @return datasource
	 * @author Nishant Bhardwaj
	 */
	@Override
	public DataSourceRequest getConnectionOfUser(String username, String connectionName) throws Exception {

		Path filepath = (Path) Paths.get("sql_resource", username + ".txt").toAbsolutePath();

		String path = String.valueOf(filepath);

		File file = new File(path);

		if (file.exists()) {

			ObjectMapper objectMapper = new ObjectMapper();
			String jsonString = cryptoService.decrypt(new String(Files.readAllBytes(Paths.get(path))));

			List<DataSourceRequest> allConnectionDetails = objectMapper.readValue(jsonString,
					new TypeReference<List<DataSourceRequest>>() {
					});

			if (allConnectionDetails.size() > 0) {
				 Optional<DataSourceRequest> dataSource = allConnectionDetails.stream()
						.filter(x->x.getConnectionName().equals(connectionName))
						.findFirst();

				 if(dataSource.isPresent())
					 return dataSource.get();
				 else
					 throw new Exception("Invalid connection request!!");

			} else {
				throw new Exception("Invalid connection request!!");
			}

		} else {
			throw new Exception("Not a valid user!!");
		}
	}

	@Override
	public DataSourceRequest selectConnection(DataSourceRequest dataSourceRequest) throws Exception {

		logger.info("Select Connection Service :: Start");

		// Create DataSource:
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(dataSourceRequest.getDriver());
		dataSource.setUrl(dataSourceRequest.getUrl());
		dataSource.setUsername(dataSourceRequest.getUsername());
		dataSource.setPassword(cryptoService.decrypt(dataSourceRequest.getPassword()));

		Path filepath = (Path) Paths.get("sql_resource", dataSourceRequest.getUsername() + ".txt").toAbsolutePath();

		String path = String.valueOf(filepath);

		File file = new File(path);

		if (file.exists()) {

			ObjectMapper objectMapper = new ObjectMapper();
			String jsonString = cryptoService.decrypt(new String(Files.readAllBytes(Paths.get(path))));

			List<DataSourceRequest> allUserDetails = objectMapper.readValue(jsonString,
					new TypeReference<List<DataSourceRequest>>() {
					});

			logger.info(allUserDetails);
			int count = 0;

			for (DataSourceRequest userData : allUserDetails) {
				if (userData.getPassword().equalsIgnoreCase(dataSourceRequest.getPassword())
						&& userData.getDriver().equalsIgnoreCase(dataSourceRequest.getDriver())
						&& userData.getUrl().equalsIgnoreCase(dataSourceRequest.getUrl())
						&& userData.getUsername().equalsIgnoreCase(dataSourceRequest.getUsername())) {
					count = 1;
					break;
				}

			}
			if (count == 0) {

				throw new Exception("Database not found");
			} else {
				// select database/ open connection:
				activeDataSource = dataSourceRequest;

				return activeDataSource;
			}
		} else {
			throw new Exception("User not found");
		}

	}
	
	@Override
	public DataSourceResponse deleteConnection(String userName, String connectionName) throws Exception {
		Path filepath = (Path) Paths.get("sql_resource", userName + ".txt").toAbsolutePath();

		String path = String.valueOf(filepath);

		File file = new File(path);

		if (file.exists()) {

			ObjectMapper objectMapper = new ObjectMapper();
			String jsonString = cryptoService.decrypt(new String(Files.readAllBytes(Paths.get(path))));

			List<DataSourceRequest> allConnectionDetails = objectMapper.readValue(jsonString,
					new TypeReference<List<DataSourceRequest>>() {
					});

			if (allConnectionDetails.size() > 0) {
				Optional<DataSourceRequest> dataSource = allConnectionDetails.stream()
						.filter(x -> x.getConnectionName().equals(connectionName))
						.filter(y -> y.getUsername().equals(userName)).findFirst();

				if (dataSource.isPresent()) {
					allConnectionDetails.remove(dataSource.get());
					updateFile(file, allConnectionDetails);
					String msg = "Connection deleted successfully!";
					return DataSourceResponse.builder().message(msg).build();
				}

				else
					throw new Exception("Invalid connection request. Connection does not exist");

			} else {
				throw new Exception("Invalid connection request. Connection does not exist");
			}

		} else {
			throw new Exception("Not a valid user!!");
		}
	}
	
	@Override
	public DataSourceResponse updateConnection(DataSourceRequest dataSourceRequest) throws Exception {
		Path filepath = (Path) Paths.get("sql_resource", dataSourceRequest.getUsername() + ".txt").toAbsolutePath();

		String path = String.valueOf(filepath);

		File file = new File(path);

		if (file.exists()) {

			ObjectMapper objectMapper = new ObjectMapper();
			String jsonString = cryptoService.decrypt(new String(Files.readAllBytes(Paths.get(path))));

			List<DataSourceRequest> allConnectionDetails = objectMapper.readValue(jsonString,
					new TypeReference<List<DataSourceRequest>>() {
					});

			if (allConnectionDetails.size() > 0) {
				Optional<DataSourceRequest> dataSource = allConnectionDetails.stream()
						.filter(x -> x.getConnectionName().equals(dataSourceRequest.getConnectionName()))
						.filter(y -> y.getUsername().equals(dataSourceRequest.getUsername())).findFirst();

				if (dataSource.isPresent()) {

					// Create DataSource:
					DriverManagerDataSource dataSourceCon = new DriverManagerDataSource();
					dataSourceCon.setDriverClassName(dataSourceRequest.getDriver());
					dataSourceCon.setUrl(dataSourceRequest.getUrl());
					dataSourceCon.setUsername(dataSourceRequest.getUsername());
					dataSourceCon.setPassword(cryptoService.decrypt(dataSourceRequest.getPassword()));

					JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceCon);
					String connections;

					try {
						jdbcTemplate.execute("show databases");

						allConnectionDetails.remove(dataSource.get());
						allConnectionDetails.add(dataSourceRequest);
						updateFile(file, allConnectionDetails);
						String msg = "Connection updated successfully!";
						return DataSourceResponse.builder().message(msg).build();

					}catch (Exception e){
						throw new Exception("Invalid connection: " + e.getMessage());
					}
				}

				else
					throw new Exception("Invalid connection request. Connection does not exist");

			} else {
				throw new Exception("Invalid connection request. Connection does not exist");
			}

		} else {
			throw new Exception("Not a valid user!!");
		}

	}
	
	private void updateFile(File file, List<DataSourceRequest> allConnectionDetails) throws Exception {
		FileWriter fw=new FileWriter(file.getAbsoluteFile());
		ObjectMapper mapper = new ObjectMapper();
		String allJson = mapper.writeValueAsString(allConnectionDetails);
		fw.write(cryptoService.encrypt(allJson));
		fw.close();
	}

}
