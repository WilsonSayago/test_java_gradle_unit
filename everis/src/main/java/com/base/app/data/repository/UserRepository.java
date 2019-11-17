package com.base.app.data.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.base.app.data.entity.User;

public interface UserRepository extends CrudRepository<User, String>{
	
	List<User> findAllByIsactive(boolean status);
	User findByEmail(String email);
	User findByIdAndIsactive(UUID id, boolean status);
}
