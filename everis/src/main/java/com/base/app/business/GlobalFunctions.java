package com.base.app.business;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class GlobalFunctions {

//	@Autowired
//	private static BCryptPasswordEncoder bCryptPasswordEncoder;
//	
//	public static String encryptText(String value) {
//		return bCryptPasswordEncoder.encode(value);
//	}
	
	public static boolean validatePassword(String password) {
		if (!password.matches(".*[a-z]+.*")) {
			return false;
		} else if (!password.matches(".*[A-Z]+.*")) {
			return false;
		} else if (!password.matches(".*[0-9]+.*")) {
			return false;
		} else if (password.matches("^(?=(.*[A-Z]){2})\\S*$") || password.matches("^(?=(.*[0-9]){3})\\S*$")) {
			return false;
		}
		return true;
	}
}
