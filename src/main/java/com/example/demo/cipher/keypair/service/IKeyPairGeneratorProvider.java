package com.example.demo.cipher.keypair.service;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import com.example.demo._domain.KeyPairPEM;

public interface IKeyPairGeneratorProvider {

	KeyPair generateKeyPair();

	KeyPairPEM generateKeyPairPEM(PublicKey publicKey, PrivateKey privateKey);

	PrivateKey buildPrivateKey(String privateKeyPEM);
}
