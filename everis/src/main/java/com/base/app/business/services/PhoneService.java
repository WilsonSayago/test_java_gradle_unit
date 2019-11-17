package com.base.app.business.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.app.data.entity.Phone;
import com.base.app.data.entity.User;
import com.base.app.data.repository.PhoneRepository;

@Service
public class PhoneService {

	@Autowired
	private PhoneRepository phoneRepository;
	
	public List<Phone> updatePhonesUser(UUID user_id, List<Phone> phones) {
		User user = new User();
		user.setId(user_id);
		for(Phone p : phones) {
			p.setUser(user);
		}
		return (List<Phone>) phoneRepository.saveAll(phones);
	}
	
}
