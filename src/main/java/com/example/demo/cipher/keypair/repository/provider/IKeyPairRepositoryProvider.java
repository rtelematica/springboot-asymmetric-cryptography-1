package com.example.demo.cipher.keypair.repository.provider;

public interface IKeyPairRepositoryProvider {

	void store(String appId, String publicKeyPEM, String privateKeyPEM);

	String retrievePrivateKey(String appId);

}
