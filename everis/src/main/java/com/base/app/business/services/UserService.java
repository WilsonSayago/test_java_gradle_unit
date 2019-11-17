package com.base.app.business.services;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.app.data.entity.User;
import com.base.app.data.repository.UserRepository;


@Service
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	private static final boolean STATE_ACTIVE = true;
	private static final boolean STATE_DESACTIVE = false;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PhoneService phoneService;
	
	@Transactional
	public User save(User user) {
//		user.setPassword(GlobalFunctions.encryptText(user.getPassword()));
		User userSave =  userRepository.save(user);
		userSave.setPhones(phoneService.updatePhonesUser(userSave.getId(), userSave.getPhones()));
		return userSave;
	}
	
	@Transactional(readOnly = true)
	public List<User> getAllByStatus(boolean status){
		if (status) {
			return userRepository.findAllByIsactive(STATE_ACTIVE);
		} else {
			return userRepository.findAllByIsactive(STATE_DESACTIVE);
		}
	}
	
	@Transactional(readOnly = true)
	public User getByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Transactional
	public User updateStatus(User user, boolean status) {
		user.setIsactive(status);
		return userRepository.save(user);
	}
	
	@Transactional(readOnly = true)
	public User getByIdStatus(UUID id, boolean status) {
		try {
			return userRepository.findByIdAndIsactive(id, status);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
}
