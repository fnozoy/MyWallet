package com.fnozoy.mywallet.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnozoy.myWallet.api.controller.UserController;
import com.fnozoy.myWallet.api.dto.UserDTO;
import com.fnozoy.myWallet.exceptions.AuthenticationErrorException;
import com.fnozoy.myWallet.exceptions.BusinessRuleException;
import com.fnozoy.myWallet.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ActiveProfiles("test")
@WebMvcTest (controllers = UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    static final String API = "/api/v1/user";

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
                .post(API.concat("/authenticate"))
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
                .post(API.concat("/authenticate"))
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
                .post(API.concat("/signup"))
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
                .post(API.concat("/signup"))
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
