package com.calmeter.core.tests.jpa;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

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
import com.calmeter.core.account.repository.IUserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryIntegrationTests {

	@Autowired
	EntityManager entityManager;

	@Autowired
	IUserRepository userRepository;

	@Before
	@Transactional
	public void runBeforeTestMethod () {
		User user = new User ();
		user.setEmail (Constants.EMAIL);
		user.setPassword (Constants.PASSWORD);
		user.setUsername (Constants.USERNAME);

		entityManager.persist (user);
		entityManager.flush ();
	}

	@Test
	@Transactional
	public void findByUserNameTest ()
			throws Exception {

		Optional<User> userWrapper = userRepository.findByUsername (Constants.USERNAME);
		if (!userWrapper.isPresent ()) {
			throw new Exception ("Could not find user");
		}
		User user = userWrapper.get ();

		if (user == null)
			throw new Exception ();

		assertEquals (Constants.EMAIL, user.getEmail ());
		assertEquals (Constants.USERNAME, user.getUsername ());
		assertEquals (Constants.PASSWORD, user.getPassword ());

	}

	@Test
	@Transactional
	public void findaAllTest ()
			throws Exception {

		User user1 = new User ();
		user1.setEmail ("user1" + Constants.EMAIL);
		user1.setPassword (Constants.PASSWORD);
		user1.setUsername ("user1" + Constants.USERNAME);
		entityManager.persist (user1);

		User user2 = new User ();
		user2.setEmail ("user2" + Constants.EMAIL);
		user2.setPassword (Constants.PASSWORD);
		user2.setUsername ("user2" + Constants.USERNAME);
		entityManager.persist (user2);

		List<User> users = userRepository.findAll ();

		assertEquals (3, users.size ());

	}

	@Test
	@Transactional
	public void deleteTest ()
			throws Exception {

		assertEquals (1, userRepository.findAll ().size ());

		Optional<User> userWrapper = userRepository.findByUsername (Constants.USERNAME);
		if (!userWrapper.isPresent ()) {
			throw new Exception ("Could not find user");
		}
		User user = userWrapper.get ();
		userRepository.delete (user);

		assertEquals (0, userRepository.findAll ().size ());

	}

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger (UserRepositoryIntegrationTests.class);

}
