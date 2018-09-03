package com.example.demo._domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeyPairPEM {
	private String privateKeyPEM;
	private String publicKeyPEM;
}
