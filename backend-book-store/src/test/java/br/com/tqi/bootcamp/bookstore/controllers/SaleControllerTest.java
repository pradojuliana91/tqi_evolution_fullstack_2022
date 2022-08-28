package br.com.tqi.bootcamp.bookstore.controllers;

import br.com.tqi.bootcamp.bookstore.Utils;
import br.com.tqi.bootcamp.bookstore.api.SaleController;
import br.com.tqi.bootcamp.bookstore.api.request.AuthorRequest;
import br.com.tqi.bootcamp.bookstore.api.request.SaleRequest;
import br.com.tqi.bootcamp.bookstore.api.response.AuthorResponse;
import br.com.tqi.bootcamp.bookstore.api.response.SaleResponse;
import br.com.tqi.bootcamp.bookstore.service.SaleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class SaleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SaleService saleService;

    @InjectMocks
    private SaleController saleController;

    private static final String URL_SALES_ENDPOINT = "/sales";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(saleController).build();
    }

    @Test
    void shouldSale() throws Exception {
        SaleRequest request = Utils.createSaleSuccessRequest();

        SaleResponse response = Utils.createSaleSuccessResponse();

        when(saleService.sale(any())).thenReturn(response);

        mockMvc.perform(post(URL_SALES_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code", is(notNullValue())))
                .andExpect(jsonPath("$.client.code", is(response.getClient().getCode())))
                .andExpect(jsonPath("$.totalSaleValue", is(response.getTotalSaleValue())))
                .andExpect(jsonPath("$.details.*", hasSize(2)));

        verify(saleService, times(1)).sale(any());
    }
}
