package com.example.demo.h2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.h2.repository.entity.UserAppKeyPair;

public interface IUserAppKeyPairRepository extends JpaRepository<UserAppKeyPair, Long> {

	UserAppKeyPair findByAppId(String appId);

}
