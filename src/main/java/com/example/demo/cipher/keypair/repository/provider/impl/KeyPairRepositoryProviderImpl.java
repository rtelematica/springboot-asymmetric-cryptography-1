package com.example.demo.cipher.keypair.repository.provider.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.cipher.keypair.repository.provider.IKeyPairRepositoryProvider;
import com.example.demo.h2.repository.IUserAppKeyPairRepository;
import com.example.demo.h2.repository.entity.UserAppKeyPair;

@Component
public class KeyPairRepositoryProviderImpl implements IKeyPairRepositoryProvider {

	@Autowired
	private IUserAppKeyPairRepository userAppKeyPairRepository;

	@Override
	public void store(String appId, String publicKeyPEM, String privateKeyPEM) {

		UserAppKeyPair userAppKeyPair = UserAppKeyPair.builder().appId(appId).publicKey(publicKeyPEM)
				.privateKey(privateKeyPEM).build();

		userAppKeyPairRepository.save(userAppKeyPair);
	}

	@Override
	public String retrievePrivateKey(String appId) {
		return userAppKeyPairRepository.findByAppId(appId).getPrivateKey();
	}

}
