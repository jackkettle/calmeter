package com.calmeter.core.tests.jpa;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.calmeter.core.account.model.User;
import com.calmeter.core.account.repository.UserRepository;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryIntegrationTests {
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	UserRepository userRepository;
	
	private static String EMAIL = "info@example.com";
	
	private static String USERNAME = "I.AM.A.TEST";

	private static String PASSWORD = "password";

    @Before
    @Transactional
    public void runBeforeTestMethod() {
       
    	User user = new User();
    	user.setEmail(EMAIL);
    	user.setPassword(PASSWORD);
    	user.setUsername(USERNAME);
    	entityManager.persist(user);    	
    }
	
	@Test
	@Transactional
	public void findByUserNameTest() throws Exception {

		User user = userRepository.findByUsername(USERNAME);

		if(user == null)
			throw new Exception();
		
		assertEquals(EMAIL, user.getEmail());
		assertEquals(USERNAME, user.getUsername());
		assertEquals(PASSWORD, user.getPassword());

	}
	
	@Test
	@Transactional
	public void findaAllTest() throws Exception {

		User user1 = new User();
		user1.setEmail("user1" + EMAIL);
		user1.setPassword(PASSWORD);
		user1.setUsername("user1" + USERNAME);
    	entityManager.persist(user1);    	
    	
    	User user2 = new User();
    	user2.setEmail("user2" + EMAIL);
    	user2.setPassword(PASSWORD);
    	user2.setUsername("user2" + USERNAME);
    	entityManager.persist(user2);  
    	
    	List<User> users = userRepository.findAll();
    	
    	assertEquals(3, users.size());

	}
	
	@Test
	@Transactional
	public void deleteTest() throws Exception {
		
		assertEquals(1, userRepository.findAll().size());
		
		User user = userRepository.findByUsername(USERNAME);
		userRepository.delete(user);
    	
		assertEquals(0, userRepository.findAll().size());

	}

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(UserRepositoryIntegrationTests.class);
	
}
