package com.example.demo.cipher.keypair.service.impl;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Component;

import com.example.demo._domain.KeyPairPEM;
import com.example.demo.cipher.keypair.service.IKeyPairGeneratorProvider;

import lombok.SneakyThrows;

@Component
public class KeyPairGeneratorProviderImpl implements IKeyPairGeneratorProvider {

	private static final String PUBLIC_KEY_PREFIX = "-----BEGIN PUBLIC KEY-----";
	private static final String PUBLIC_KEY_SUFFIX = "-----END PUBLIC KEY-----";
	private static final String PRIVATE_KEY_PREFIX = "-----BEGIN RSA PRIVATE KEY-----";
	private static final String PRIVATE_KEY_SUFFIX = "-----END RSA PRIVATE KEY-----";

	@Override
	@SneakyThrows
	public KeyPair generateKeyPair() {

		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(1024);

		KeyPair pair = keyGen.generateKeyPair();

		return pair;
	}

	@Override
	public KeyPairPEM generateKeyPairPEM(PublicKey publicKey, PrivateKey privateKey) {

		String publicKeyPEM = null;
		String privateKeyPEM = null;

		publicKeyPEM = PUBLIC_KEY_PREFIX + "\n"
				+ DatatypeConverter.printBase64Binary(publicKey.getEncoded()).replaceAll("(.{64})", "$1\n") + "\n"
				+ PUBLIC_KEY_SUFFIX;

		privateKeyPEM = PRIVATE_KEY_PREFIX + "\n"
				+ DatatypeConverter.printBase64Binary(privateKey.getEncoded()).replaceAll("(.{64})", "$1\n") + "\n"
				+ PRIVATE_KEY_SUFFIX;

		return KeyPairPEM.builder().publicKeyPEM(publicKeyPEM).privateKeyPEM(privateKeyPEM).build();
	}

	@Override
	@SneakyThrows
	public PrivateKey buildPrivateKey(String privateKeyPEM) {
		
		PrivateKey key = null;

		privateKeyPEM = privateKeyPEM.replace(PRIVATE_KEY_PREFIX + "\n", "").replace(PRIVATE_KEY_SUFFIX, "");

		byte[] keyBytes = DatatypeConverter.parseBase64Binary(privateKeyPEM);

		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		key = keyFactory.generatePrivate(spec);
		
		return key;
	}
}
