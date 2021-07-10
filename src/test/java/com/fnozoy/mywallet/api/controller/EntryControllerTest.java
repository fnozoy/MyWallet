package com.fnozoy.mywallet.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnozoy.myWallet.api.controller.EntryController;
import com.fnozoy.myWallet.api.dto.EntryDTO;
import com.fnozoy.myWallet.exceptions.BusinessRuleException;
import com.fnozoy.myWallet.model.enums.EntryCodeEnum;
import com.fnozoy.myWallet.model.enums.EntryStatusEnum;
import com.fnozoy.myWallet.service.EntryService;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@WebMvcTest(controllers = EntryController.class)
@AutoConfigureMockMvc
public class EntryControllerTest {

    static final String API = "/api/entry";

    @Autowired
    MockMvc mvc;

    @MockBean
    EntryService entryService;

    @Test
    public void validateCreateEntryWithSuccess() throws Exception {
        EntryDTO entryDTO = makeEntryDTO();
        Mockito.when(entryService.create(Mockito.any(EntryDTO.class))).thenReturn(entryDTO);
        String json = new ObjectMapper().writeValueAsString(entryDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat("/v1/create"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("value").value(entryDTO.getValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("description").value(entryDTO.getDescription()))
                .andReturn();
    }

    @Test
    public void validateCreateEntryWithFailure() throws Exception {
        EntryDTO entryDTO = makeEntryDTO();
        Mockito.when(entryService.create(Mockito.any(EntryDTO.class))).thenThrow(BusinessRuleException.class);
        String json = new ObjectMapper().writeValueAsString(entryDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat("/v1/create"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    @Test
    public void validateUpdateEntryWithSuccess() throws Exception {
        EntryDTO entryDTO = makeEntryDTO();
        Mockito.when(entryService.update(Mockito.any(EntryDTO.class))).thenReturn(entryDTO);
        String json = new ObjectMapper().writeValueAsString(entryDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(API.concat("/v1/update"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("value").value(entryDTO.getValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("description").value(entryDTO.getDescription()))
                .andReturn();
    }

    @Test
    public void validateUpdateEntryWithFailure() throws Exception {
        EntryDTO entryDTO = makeEntryDTO();
        Mockito.when(entryService.update(Mockito.any(EntryDTO.class))).thenThrow(BusinessRuleException.class);
        String json = new ObjectMapper().writeValueAsString(entryDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(API.concat("/v1/update"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    @Test
    public void validateUpdateStatusEntryWithSuccess() throws Exception {
        EntryDTO entryDTO = makeEntryDTO();
        Mockito.doNothing().when(entryService).updateStatus(Mockito.any(EntryDTO.class));
        String json = new ObjectMapper().writeValueAsString(entryDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(API.concat("/v1/updatestatus"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void validateUpdateStatusEntryWithFailure() throws Exception {
        EntryDTO entryDTO = makeEntryDTO();
        Mockito.doThrow(BusinessRuleException.class).when(entryService).updateStatus(Mockito.any(EntryDTO.class));
        String json = new ObjectMapper().writeValueAsString(entryDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(API.concat("/v1/updatestatus"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    @Test
    public void validateDeleteEntryWithSuccess() throws Exception {
        EntryDTO entryDTO = makeEntryDTO();
        Mockito.doNothing().when(entryService).delete(1L);
        String json = new ObjectMapper().writeValueAsString(entryDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(API.concat("/v1/delete/1"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void validateDeleteEntryWithFailure() throws Exception {
        EntryDTO entryDTO = makeEntryDTO();
        Mockito.doThrow(BusinessRuleException.class).when(entryService).delete(1L);
        String json = new ObjectMapper().writeValueAsString(entryDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(API.concat("/v1/delete/1"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    @Test
    public void validateSearchEntryWithSuccess() throws Exception {
        EntryDTO entryDTO = makeEntryDTO();
        List<EntryDTO> list = new ArrayList<>();
        list.add(entryDTO);
        Mockito.when(entryService.search(Mockito.any(EntryDTO.class))).thenReturn(list);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat("/v1/search?year=2021&userId=1"));
        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void validateSearchEntryWithFailure() throws Exception {
        Mockito.when(entryService.search(Mockito.any(EntryDTO.class))).thenThrow(BusinessRuleException.class);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat("/v1/search?year=2021&userId=1"));
        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    @Test
    public void validateGetBalanceWithSuccess() throws Exception {
        BigDecimal balance = new BigDecimal("1000");
        Mockito.when(entryService.getBalanceByUserId(1L)).thenReturn(balance);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API.concat("/v1/getbalance/1"));
        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void validateGetBalanceWithFailure() throws Exception {
        Mockito.when(entryService.getBalanceByUserId(1L)).thenThrow(BusinessRuleException.class);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API.concat("/v1/getbalance/1"));
        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    public EntryDTO makeEntryDTO(){
        return EntryDTO.builder()
                .id(777L)
                .month(6)
                .year(2021)
                .entryCode(EntryCodeEnum.INCOME)
                .entryStatus(EntryStatusEnum.APPROVED)
                .description("SALARY")
                .value(BigDecimal.valueOf(10000))
                .userId(1L)
                .build();
    }
}
