package com.caiodorn.codingtests.gamesys.user.rest;

import com.caiodorn.codingtests.gamesys.user.persistence.BlackListedUserEntity;
import com.caiodorn.codingtests.gamesys.user.persistence.BlackListedUserRepository;
import com.caiodorn.codingtests.gamesys.user.persistence.UserEntity;
import com.caiodorn.codingtests.gamesys.user.persistence.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ActiveProfiles("it")
public class UserControllerIT {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlackListedUserRepository blackListedUserRepository;

    private ObjectMapper mapper = new ObjectMapper();

    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
        blackListedUserRepository.deleteAll();

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void givenValidUserOfNonBlackListedPerson_whenRegister_thenShouldPersistIt() throws Exception {
        User user = new User("ValidUserName", "vAl1d", "2000-12-31", "000-11-4444");
        UserEntity previousUserFromSamePerson = new UserEntity(null,
                "johndoe", user.getPassword(), user.getSsn(), LocalDate.parse(user.getDob()));
        userRepository.save(previousUserFromSamePerson);

        mockMvc.perform(post("/users")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(mapper.writeValueAsString(user))
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void givenInvalidUserName_withSpaces_whenRegister_thenShouldReturnBadRequest() throws Exception {
        User user = new User("Invalid UserName", "vAl1d", "2000-12-31", "000-11-4444");

        mockMvc.perform(post("/users")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(mapper.writeValueAsString(user))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void givenInvalidUserName_empty_whenRegister_thenShouldReturnBadRequest() throws Exception {
        User user = new User("", "vAl1d", "2000-12-31", "000-11-4444");

        mockMvc.perform(post("/users")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(mapper.writeValueAsString(user))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void givenInvalidUserName_allSpaces_whenRegister_thenShouldReturnBadRequest() throws Exception {
        User user = new User("  ", "vAl1d", "2000-12-31", "000-11-4444");

        mockMvc.perform(post("/users")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(mapper.writeValueAsString(user))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void givenInvalidUserName_invalidChars_whenRegister_thenShouldReturnBadRequest() throws Exception {
        User user = new User("inv@lid", "vAl1d", "2000-12-31", "000-11-4444");

        mockMvc.perform(post("/users")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(mapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenUserNameInUse_whenRegister_thenShouldReturnConflict() throws Exception {
        User user = new User("someUserName", "vAl1d", "2000-12-31", "000-11-4444");
        UserEntity previousUserFromSamePerson = new UserEntity(null,
                user.getUserName(), user.getPassword(), user.getSsn(), LocalDate.parse(user.getDob()));
        userRepository.save(previousUserFromSamePerson);

        mockMvc.perform(post("/users")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(mapper.writeValueAsString(user))
        ).andExpect(status().isConflict());
    }

    @Test
    public void givenBlackListedUser_whenRegister_thenShouldReturnBadRequest() throws Exception {
        User user = new User("someUserName", "vAl1d", "2000-12-31", "000-11-4444");
        BlackListedUserEntity blackListedUser = new BlackListedUserEntity(null,
                user.getSsn(), LocalDate.parse(user.getDob()), "cheating", null);
        blackListedUserRepository.save(blackListedUser);

        mockMvc.perform(post("/users")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(mapper.writeValueAsString(user))
        ).andExpect(status().isBadRequest());
    }

}
