package com.fnozoy.myWallet.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnozoy.myWallet.api.dto.UserDTO;
import com.fnozoy.myWallet.exceptions.AuthenticationErrorException;
import com.fnozoy.myWallet.exceptions.BusinessRuleException;
import com.fnozoy.myWallet.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.security.web.savedrequest.RequestCacheAwareFilter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ActiveProfiles("test")
@WebMvcTest (controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    static final String API = "/api/user";

    @Autowired
    MockMvc mvc;

    @MockBean
    UserService userService;

    @Test
    public void validateUserAuthentication() throws Exception {

        UserDTO userDTO = makeUserDTO();
        Mockito.when(userService.authenticate(Mockito.any(UserDTO.class))).thenReturn(userDTO);
        String json = new ObjectMapper().writeValueAsString(userDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat("/v1/authenticate"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("email").value(userDTO.getEmail()))
                .andReturn();
    }

    @Test
    public void validateWrongUserAuthentication() throws Exception {

        UserDTO userDTO = makeUserDTO();
        Mockito.when(userService.authenticate(Mockito.any(UserDTO.class))).thenThrow(AuthenticationErrorException.class);
        String json = new ObjectMapper().writeValueAsString(userDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat("/v1/authenticate"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    @Test
    public void validateUserSignup() throws Exception {

        UserDTO userDTO = makeUserDTO();
        Mockito.when(userService.signupUser(Mockito.any(UserDTO.class))).thenReturn(userDTO);
        String json = new ObjectMapper().writeValueAsString(userDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat("/v1/signup"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("email").value(userDTO.getEmail()))
                .andReturn();
    }

    @Test
    public void validateWrongUserSignup() throws Exception {

        UserDTO userDTO = makeUserDTO();
        Mockito.when(userService.signupUser(Mockito.any(UserDTO.class))).thenThrow(BusinessRuleException.class);
        String json = new ObjectMapper().writeValueAsString(userDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat("/v1/signup"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    public UserDTO makeUserDTO(){
        return UserDTO.builder()
                .email("george@jungle.com")
                .name("George of the Jungle")
                .pswd("123456")
                .id(1L)
                .build();
    }

}
