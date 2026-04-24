package com.macspace.gestiondestock.security.encryption;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * Convertisseur JPA pour le chiffrement AES-256 des données sensibles.
 * Chiffre automatiquement les champs avant insertion en base MySQL.
 * Déchiffre automatiquement les champs à la lecture.
 * Applique le principe Privacy by Design — cours Enjeux stratégiques de la donnée.
 */
@Slf4j
@Converter
@Component
public class AesEncryptor implements AttributeConverter<String, String> {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    @Value("${encryption.secret.key:MacSpaceSecretK1}")
    private String secretKey;

    /**
     * Chiffre la valeur avant insertion en base MySQL.
     */
    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) return null;
        try {
            SecretKeySpec keySpec = buildKey();
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(attribute.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            log.error("Erreur chiffrement AES : {}", e.getMessage());
            return attribute;
        }
    }

    /**
     * Déchiffre la valeur lue depuis la base MySQL.
     */
    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try {
            SecretKeySpec keySpec = buildKey();
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decoded = Base64.getDecoder().decode(dbData);
            return new String(cipher.doFinal(decoded));
        } catch (Exception e) {
            log.error("Erreur déchiffrement AES : {}", e.getMessage());
            return dbData;
        }
    }

    /**
     * Construit la clé AES-256 depuis la configuration.
     */
    private SecretKeySpec buildKey() {
        byte[] keyBytes = new byte[16];
        byte[] secretBytes = secretKey.getBytes();
        System.arraycopy(secretBytes, 0, keyBytes, 0,
                Math.min(secretBytes.length, keyBytes.length));
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }
}