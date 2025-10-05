package com.websecurity.app01;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class EncryptionService {
    private final SecretKey secretKey;
    private final SecureRandom secureRandom;

    public EncryptionService(@Value("${encryption.key}") String base64Key) {
        // Load key from environment variable (NEVER hardcode!)
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        this.secretKey = new SecretKeySpec(keyBytes, "AES");
        this.secureRandom = new SecureRandom();

        // Validate key strength
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("AES key must be at least 256 bits");
        }
    }
    /**
     * Encrypt data using AES-256-GCM (AEAD - Authenticated Encryption)
     * GCM provides both confidentiality AND integrity
     */
    public String encrypt(String plaintext) throws Exception {

        // 1. Generate random IV (MUST be unique for each encryption!)
        byte[] iv = new byte[12]; // 96 bits for GCM
        secureRandom.nextBytes(iv);

        // 2. Initialize cipher in GCM mode
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv); // 128-bit auth tag
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmSpec);

        // 3. Encrypt
        byte[] ciphertext = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

        // 4. Combine IV + Ciphertext (IV needs to be stored/transmitted)
        byte[] combined = new byte[iv.length + ciphertext.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(ciphertext, 0, combined, iv.length, ciphertext.length);

        // 5. Encode as Base64 for storage/transmission
        return Base64.getEncoder().encodeToString(combined);

        // GCM benefits:
        // - Authenticated encryption (prevents tampering)
        // - Parallel processing (fast)
        // - No padding oracle vulnerabilities
        // - NIST approved
    }

    /**
     * Decrypt AES-GCM encrypted data
     */
    public String decrypt(String encryptedData) throws Exception {

        // 1. Decode Base64
        byte[] combined = Base64.getDecoder().decode(encryptedData);

        // 2. Extract IV and ciphertext
        byte[] iv = new byte[12];
        byte[] ciphertext = new byte[combined.length - 12];
        System.arraycopy(combined, 0, iv, 0, 12);
        System.arraycopy(combined, 12, ciphertext, 0, ciphertext.length);

        // 3. Initialize cipher
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmSpec);

        // 4. Decrypt (automatically verifies authentication tag)
        byte[] plaintext = cipher.doFinal(ciphertext);

        return new String(plaintext, StandardCharsets.UTF_8);

        // If data was tampered with, doFinal() throws AEADBadTagException
    }
}
