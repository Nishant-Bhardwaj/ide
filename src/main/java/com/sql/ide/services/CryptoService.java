package com.sql.ide.services;

import org.springframework.stereotype.Service;

/**
 * Service interface for crypto service
 *
 * @author Nishant Bhardwaj
 */
@Service
public interface CryptoService {

    public String encrypt(String data) throws Exception;

    public String decrypt(String encryptedData) throws Exception;
}
