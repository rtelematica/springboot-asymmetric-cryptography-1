package com.example.demo.cipher.service.impl;

import java.security.KeyPair;
import java.security.PrivateKey;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo._domain.KeyPairPEM;
import com.example.demo.cipher.keypair.repository.provider.IKeyPairRepositoryProvider;
import com.example.demo.cipher.keypair.service.IKeyPairGeneratorProvider;
import com.example.demo.cipher.service.ICipherManager;

import lombok.SneakyThrows;

@Component
public class CipherManagerImpl implements ICipherManager {

	@Autowired
	private IKeyPairGeneratorProvider keyPairGeneratorProvider;

	@Autowired
	private IKeyPairRepositoryProvider keyPairRepositoryProvider;

	@Override
	public String generatePublicKey(String appId) {

		KeyPair keyPair = keyPairGeneratorProvider.generateKeyPair();

		KeyPairPEM keyPairPEM = keyPairGeneratorProvider.generateKeyPairPEM(keyPair.getPublic(), keyPair.getPrivate());

		storeKeysSomewhere(appId, keyPairPEM.getPublicKeyPEM(), keyPairPEM.getPrivateKeyPEM());

		return keyPairPEM.getPublicKeyPEM();
	}

	@Override
	@SneakyThrows
	public String decrypt(String appId, String cipherText) {

		String originalText = null;

		PrivateKey privateKey = retrievePrivateKeyFromSomewhere(appId);

		Cipher cipher = Cipher.getInstance("RSA");

		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		byte[] encodedCipherText = DatatypeConverter.parseBase64Binary(cipherText);

		originalText = new String(cipher.doFinal(encodedCipherText), "UTF-8");

		return originalText;
	}

	@Override
	public String encrypt(String appId, String data) {
		// TODO: PrivateKey privateKey = retrievePrivateKeyFromSomewhere(appId);
		// continue here ...

		throw new UnsupportedOperationException("Not implemented yet.");
	}

	private void storeKeysSomewhere(String appId, String publicKeyPEM, String privateKeyPEM) {
		keyPairRepositoryProvider.store(appId, publicKeyPEM, privateKeyPEM);
	}

	private PrivateKey retrievePrivateKeyFromSomewhere(String appId) {

		String privateKeyPEM = keyPairRepositoryProvider.retrievePrivateKey(appId);

		return buildPrivateKey(privateKeyPEM);
	}

	private PrivateKey buildPrivateKey(String privateKeyPEM) {
		return keyPairGeneratorProvider.buildPrivateKey(privateKeyPEM);
	}

}
