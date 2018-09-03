package com.example.demo.h2.repository.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "USER_APP_KEY")
public class UserAppKeyPair {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String appId;
	private String publicKey;
	private String privateKey;
}
