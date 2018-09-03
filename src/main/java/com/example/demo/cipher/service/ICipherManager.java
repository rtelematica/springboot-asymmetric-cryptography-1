package com.example.demo.cipher.service;

public interface ICipherManager {
	
	/**
	 * Genera una llave pública.
	 *
	 * @param  appId Identificador del aplicativo o cliente consumidor.
	 * @return      la llave pública.
	 */
	String generatePublicKey(String appId);

	/**
	 * Descifra un texto cifrado mediante el identificador del aplicativo o cliente consumidor.
	 *
	 * @param  appId Identificador del aplicativo o cliente consumidor.
	 * @param  chiperText Texto cifrado.
	 * @return      el texto descifrado.
	 */
	String decrypt(String appId, String chiperText);

	/**
	 * Cifra un texto plano mediante el identificador del aplicativo o cliente consumidor.
	 *
	 * @param  appId Identificador del aplicativo o cliente consumidor.
	 * @param  data Texto a cifrar.
	 * @return      el texto cifrado.
	 */
	String encrypt(String appId, String data);
}
