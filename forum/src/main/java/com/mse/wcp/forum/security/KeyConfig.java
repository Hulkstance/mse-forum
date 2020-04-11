package com.mse.wcp.forum.security;

import org.apache.commons.codec.binary.Base64;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Generating a keystore file: keytool -genkey -alias key_alias -keystore forum.keystore -keyalg rsa -storepass keystore_password -keypass keystore_password
 */
public class KeyConfig {

    private static final String KEY_STORE_FILE = "forum.keystore";
    private static final String KEY_STORE_PASSWORD = "keystore_password";
    private static final String KEY_ALIAS = "key_alias";
    private static KeyStoreKeyFactory KEY_STORE_KEY_FACTORY = new KeyStoreKeyFactory(
            new ClassPathResource(KEY_STORE_FILE), KEY_STORE_PASSWORD.toCharArray());
    public static final String VERIFIER_KEY_ID = new String(Base64.encodeBase64(KeyGenerators.secureRandom(32).generateKey()));

    public static RSAPublicKey getVerifierKey() {
        return (RSAPublicKey) getKeyPair().getPublic();
    }

    public static RSAPrivateKey getSignerKey() {
        return (RSAPrivateKey) getKeyPair().getPrivate();
    }

    private static KeyPair getKeyPair() {
        return KEY_STORE_KEY_FACTORY.getKeyPair(KEY_ALIAS);
    }

}
