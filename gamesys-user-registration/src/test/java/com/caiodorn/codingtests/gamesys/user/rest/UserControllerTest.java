package com.caiodorn.codingtests.gamesys.user.rest;

import com.caiodorn.codingtests.gamesys.user.business.BlackListedUserException;
import com.caiodorn.codingtests.gamesys.user.business.UserNameAlreadyInUseException;
import com.caiodorn.codingtests.gamesys.user.business.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ActiveProfiles("test")
public class UserControllerTest extends DocumentedTest {

    @MockBean
    private UserService userServiceMock;

    private ObjectMapper mapper = new ObjectMapper();

    /**
     * This test is the one being used to generate documentation - this is the reason why it is not following a BDD
     * pattern. 'register' corresponds to the method on the controller instead.
     *
     * @throws Exception
     */
    @Test
    public void register() throws Exception {
        User user = new User("ValidUserName", "vAl1d", "2000-12-31", "000-11-4444");

        mockMvc.perform(post("/users")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(mapper.writeValueAsString(user))
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(documentationHandler.document(requestFieldsSnippet));
    }

    @Test
    public void givenInvalidUserName_withSpaces_whenRegister_thenShouldReturnBadRequest() throws Exception {
        User user = new User("Invalid UserName", "vAl1d", "2000-12-31", "000-11-4444");

        mockMvc.perform(post("/users")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(mapper.writeValueAsString(user))
        ).andExpect(status().isBadRequest())
                .andDo(documentationHandler.document(requestFieldsSnippet));
    }

    @Test
    public void givenInvalidUserName_empty_whenRegister_thenShouldReturnBadRequest() throws Exception {
        User user = new User("", "vAl1d", "2000-12-31", "000-11-4444");

        mockMvc.perform(post("/users")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(mapper.writeValueAsString(user))
        ).andExpect(status().isBadRequest())
                .andDo(documentationHandler.document(requestFieldsSnippet));
    }

    @Test
    public void givenInvalidUserName_allSpaces_whenRegister_thenShouldReturnBadRequest() throws Exception {
        User user = new User("  ", "vAl1d", "2000-12-31", "000-11-4444");

        mockMvc.perform(post("/users")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(mapper.writeValueAsString(user))
        ).andExpect(status().isBadRequest())
                .andDo(documentationHandler.document(requestFieldsSnippet));
    }

    @Test
    public void givenInvalidUserName_invalidChars_whenRegister_thenShouldReturnBadRequest() throws Exception {
        User user = new User("inv@lid", "vAl1d", "2000-12-31", "000-11-4444");

        mockMvc.perform(post("/users")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(mapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andDo(documentationHandler.document(requestFieldsSnippet));
    }

    @Test
    public void givenUserNameInUse_whenRegister_thenShouldReturnConflict() throws Exception {
        User user = new User("someUserName", "vAl1d", "2000-12-31", "000-11-4444");

        doThrow(new UserNameAlreadyInUseException(user.getUserName())).when(userServiceMock).register(user);

        mockMvc.perform(post("/users")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(mapper.writeValueAsString(user))
        ).andExpect(status().isConflict())
                .andDo(documentationHandler.document(requestFieldsSnippet,customErrorSnippet));
    }

    @Test
    public void givenBlackListedUser_whenRegister_thenShouldReturnBadRequest() throws Exception {
        User user = new User("someUserName", "vAl1d", "2000-12-31", "000-11-4444");

        doThrow(new BlackListedUserException(user.getDob(), user.getSsn())).when(userServiceMock).register(user);

        mockMvc.perform(post("/users")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(mapper.writeValueAsString(user))
        ).andExpect(status().isBadRequest())
                .andDo(documentationHandler.document(requestFieldsSnippet, customErrorSnippet));
    }

}
