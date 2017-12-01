package com.calmeter.core.tests.account.service;

import com.calmeter.core.ApplicationConfig;
import com.calmeter.core.Main;
import com.calmeter.core.account.model.User;
import com.calmeter.core.account.service.IUserService;
import com.calmeter.core.custom.TestValueLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
@ContextConfiguration(classes = {Main.class, ApplicationConfig.class})
public class UserServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private IUserService userService;

    @Autowired
    TestValueLoader testValueLoader;

    @BeforeClass
    public void setUp() {
        this.testValueLoader.loadValues();
    }

    @Test
    public void testSaveDoesNotModifyPassword() throws Exception {

        Optional<User> userWrapper = userService.findByUsername("john.doe");
        if(!userWrapper.isPresent())
            throw new Exception("Could not find user");

        String passwordBefore = userWrapper.get().getPassword();
        User updatedUser = this.userService.save(userWrapper.get());
        Assert.assertEquals(passwordBefore, updatedUser.getPassword());
    }

    private static Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

}
