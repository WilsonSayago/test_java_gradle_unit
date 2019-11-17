package com.base.app.client.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.base.app.data.entity.Phone;
import com.base.app.data.entity.User;
import com.github.javafaker.Faker;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@FixMethodOrder(MethodSorters.NAME_ASCENDING) //Ejecuta de mayor a menor
public class UserControllerTest {
	
private TestRestTemplate testRestTemplate = new TestRestTemplate();
	
	private HttpHeaders httpHeaders = new HttpHeaders(); 
	
	@LocalServerPort
	private Integer port;
	
	private static final String URL = "http://localhost:";
	
	@SuppressWarnings("rawtypes")
	private ResponseEntity<Map> response = null;
	private static User userValid = new User();
	private static User userInvalid = new User();

	@BeforeClass
	public static void initial() {
		Faker faker = new Faker();
		userValid.setEmail("wilson@everis.cl");
		userValid.setName("wilson");
		userValid.setPassword("Qa12asd");
		List<Phone> phones = new ArrayList<>();
		Phone phone = new Phone();
		phone.setCitycode(faker.number().digits(1));
		phone.setContrycode(faker.number().digits(2));
		phone.setNumber(faker.phoneNumber().phoneNumber());
		phones.add(phone);
		userValid.setPhones(phones);
		userInvalid.setPassword("AA123");
	}
	
	@SuppressWarnings({"unchecked"})
	@Test
	public final void AtestGetAll() throws Exception {
		// Status params correct: actives - desactives
		response = responseEntity("/users/actives", HttpMethod.GET, null);
		Map<String, Object> map = response.getBody();
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
		assertTrue("GetAll no errors",map.get("users")!=null);
		response = responseEntity("/users/desactives", HttpMethod.GET, null);
		map = response.getBody();
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
		assertTrue("GetAll no errors",map.get("users")!=null);
		// Status params error
		response = responseEntity("/users/error", HttpMethod.GET, null);
		map = response.getBody();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
		assertTrue("GetAll bad request",map.get("mensaje")!=null);
	}

	@SuppressWarnings("unchecked")
	@Test
	public final void BtestSaveFailed() throws Exception {
		response = responseEntity("/users", HttpMethod.POST, userInvalid);
		Map<String, Object> map = response.getBody();
		User userSaved = (User) map.get("user");
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
		assertTrue("Save user", userSaved == null);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public final void CtestSave() throws Exception {
		response = responseEntity("/users", HttpMethod.POST, userValid);
		Map<String, Object> map = response.getBody();
		Map userSaved = (Map) map.get("user");
		assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
		assertTrue("Save user", userSaved != null);
		assertEquals(userValid.getEmail(), userSaved.get("email"));
		userValid.setId(UUID.fromString((String)userSaved.get("id")));
	}
	
	

	@SuppressWarnings("unchecked")
	@Test
	public final void DtestDesactivateUser() throws Exception {
		// Desactivate status Ok.
		response = responseEntity("/users/desactivate/" + userValid.getId(), HttpMethod.PUT, null);
		Map<String, Object> map = response.getBody();
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
		assertTrue("Desactivate user",map.get("user")!=null);
		// Desactivate status Failed
		response = responseEntity("/users/desactivate/" + userValid.getId(), HttpMethod.PUT, null);
		map = response.getBody();
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
	}

	@SuppressWarnings("unchecked")
	@Test
	public final void EtestActivateUser() throws Exception {
		// Activate status Ok.
		response = responseEntity("/users/activate/" + userValid.getId(), HttpMethod.PUT, null);
		Map<String, Object> map = response.getBody();
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
		assertTrue("Activate user",map.get("user")!=null);
		// Activate status Failed
		response = responseEntity("/users/activate/" + userValid.getId(), HttpMethod.PUT, null);
		map = response.getBody();
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
	}

	
	
	@SuppressWarnings("rawtypes")
	private ResponseEntity<Map> responseEntity(String uri, HttpMethod method, User user) {
        HttpEntity<User> entity = new HttpEntity<User>(user, httpHeaders);
        ResponseEntity<Map> response = testRestTemplate.exchange(
        	getUrl() + uri,
        	method, entity, Map.class);
        return response;
    }

	private String getUrl() {
		return URL + port;
	}
}
