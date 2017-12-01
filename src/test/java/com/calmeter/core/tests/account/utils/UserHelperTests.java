package com.calmeter.core.tests.account.utils;

import com.calmeter.core.ApplicationConfig;
import com.calmeter.core.Main;
import com.calmeter.core.account.model.User;
import com.calmeter.core.account.model.WeightLogEntry;
import com.calmeter.core.account.service.IUserService;
import com.calmeter.core.account.utils.UserHelper;
import com.calmeter.core.custom.TestValueLoader;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
@ContextConfiguration(classes = {Main.class, ApplicationConfig.class})
public class UserHelperTests extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private IUserService userService;

    @Autowired
    private TestValueLoader testValueLoader;

    private User user;

    @BeforeClass
    public void setUp() throws Exception {
        this.testValueLoader.loadValues();

        Optional<User> userWrapper = userService.findByUsername("john.doe");
        if (!userWrapper.isPresent())
            throw new Exception("Unable to find user");

        this.user = userWrapper.get();
    }

    @Test
    public void getIsUserAdmin() {
        Assert.assertEquals(true, userHelper.isAdmin(this.user));
    }

    @Test
    public void getCurrentWeightTest() throws Exception {
        Optional<WeightLogEntry> weightLogEntryWrapper = userHelper.getCurrentWeight(this.user);
        if (!weightLogEntryWrapper.isPresent())
            throw new Exception("Failed to get a WeightLogEntry");

        Assert.assertEquals(85.0, weightLogEntryWrapper.get().getWeightInKgs(), 0);
    }

}
