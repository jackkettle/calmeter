package com.calmeter.core.account.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.calmeter.core.account.model.*;
import org.assertj.core.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.calmeter.core.account.repository.IUserRoleRepository;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class UserDeserializer extends JsonDeserializer<User> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserRoleRepository roleRepository;

    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        User user = new User();

        JsonNode usernameNode = rootNode.get("username");
        if (usernameNode != null) {
            user.setUsername(usernameNode.asText());
        }

        user.setFirstname(rootNode.get("firstname").asText());
        user.setLastname(rootNode.get("lastname").asText());
        user.setEmail(rootNode.get("email").asText());

        UserProfile userProfile = new UserProfile();
        JsonNode dateOfBirthNode = rootNode.get("dateOfBirth");
        if (dateOfBirthNode != null) {

            String dobString = dateOfBirthNode.asText();
            if (!Strings.isNullOrEmpty(dobString)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                formatter = formatter.withLocale(Locale.UK);
                LocalDate date = LocalDate.parse(dateOfBirthNode.asText(), formatter);
                userProfile.setDateOfBirth(date);
            }
        }

        JsonNode sexNode = rootNode.get("sex");
        if (sexNode != null) {
            userProfile.setSex(Sex.valueOf(sexNode.asText().toUpperCase()));
        }

        JsonNode weightNode = rootNode.get("weight");
        if (weightNode != null) {

            JsonNode weightInKgsNode = weightNode.get("weightInKgs");
            Double weightInKgs = weightInKgsNode.asDouble();
            JsonNode dateTimeNode = weightNode.get("dateTime");
            WeightLogEntry weightLogEntry = new WeightLogEntry();
            weightLogEntry.setWeightInKgs(weightInKgs);
            weightLogEntry.setDateTime(LocalDateTime.now());
            userProfile.getWeightLog().add(weightLogEntry);
        }

        JsonNode heightNode = rootNode.get("height");
        if (heightNode != null) {
            userProfile.setHeight(heightNode.asDouble());
        }

        JsonNode passwordsNode = rootNode.get("passwords");
        if (passwordsNode != null) {
            String rawPassword = passwordsNode.get("password").asText();
            user.setPassword(passwordEncoder.encode(rawPassword));

        }

        JsonNode passwordNode = rootNode.get("password");
        if (passwordsNode != null) {
            String rawPassword = passwordNode.asText();
            user.setPassword(passwordEncoder.encode(rawPassword));

        }

        UserRole memberRole = roleRepository.findByRole(Role.MEMBER).get();
        user.getRoles().add(memberRole);

        user.setUserProfile(userProfile);
        user.setEnabled(true);
        return user;
    }

    public static final Logger logger = LoggerFactory.getLogger(UserDeserializer.class);

}
