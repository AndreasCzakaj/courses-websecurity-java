package com.websecurity.app01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.assertj.core.api.Assertions.*;

class EncryptionServiceTest {
    EncryptionService service;

    @BeforeEach
    void beforeEach() {
        // Generate a proper 32-byte (256-bit) key for AES-256
        String secretKey = "mySecretKey_Exactly_32_Bytes!!#+"; // Exactly 32 bytes
        final var bytes = secretKey.getBytes();
        assertThat(bytes.length).isEqualTo(32);
        final var base64Key = Base64.getEncoder().encodeToString(bytes);
        service = new EncryptionService(base64Key);
    }

    @Test
    void cycle() throws Exception {
        // given
        String value = "my content";

        // when
        String encrypted = service.encrypt(value);

        // then
        assertThat(encrypted).isNotEqualTo(value);

        String decrypted = service.decrypt(encrypted);
        assertThat(decrypted).isEqualTo(value);
    }

    @Test
    void sameInputDifferentResult() throws Exception {
        // given
        String value = "my content";

        // when
        String encrypted1 = service.encrypt(value);
        String encrypted2 = service.encrypt(value);

        // then
        assertThat(encrypted1).isNotEqualTo(encrypted2);

        String decrypted1 = service.decrypt(encrypted1);
        String decrypted2 = service.decrypt(encrypted2);
        assertThat(decrypted1).isEqualTo(value);
        assertThat(decrypted2).isEqualTo(value);
    }
}
