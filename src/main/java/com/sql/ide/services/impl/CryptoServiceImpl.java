package com.sql.ide.services.impl;

import com.sql.ide.constant.AppConstants;
import com.sql.ide.services.CryptoService;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Service implementation for crypto service
 *
 * @author Nishant Bhardwaj
 */
@Service
public class CryptoServiceImpl implements CryptoService {

    public String encrypt(String data) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(AppConstants.aesKey.getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(AppConstants.aesIv.getBytes(StandardCharsets.UTF_8));
        Cipher cipher = Cipher.getInstance(AppConstants.AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        byte[] iv = cipher.getIV();
        byte[] combined = new byte[AppConstants.IV_SIZE + encryptedData.length];
        System.arraycopy(iv, 0, combined, 0, AppConstants.IV_SIZE);
        System.arraycopy(encryptedData, 0, combined, AppConstants.IV_SIZE, encryptedData.length);
        return Base64.getEncoder().encodeToString(combined);
    }

    public String decrypt(String encryptedData) throws Exception {
        byte[] combined = Base64.getDecoder().decode(encryptedData);
        byte[] iv = new byte[AppConstants.IV_SIZE];
        byte[] data = new byte[combined.length - AppConstants.IV_SIZE];
        System.arraycopy(combined, 0, iv, 0, AppConstants.IV_SIZE);
        System.arraycopy(combined, AppConstants.IV_SIZE, data, 0, combined.length - AppConstants.IV_SIZE);
        SecretKeySpec secretKeySpec = new SecretKeySpec(AppConstants.aesKey.getBytes(StandardCharsets.UTF_8), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance(AppConstants.AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] decryptedData = cipher.doFinal(data);
        return new String(decryptedData);
    }
}
