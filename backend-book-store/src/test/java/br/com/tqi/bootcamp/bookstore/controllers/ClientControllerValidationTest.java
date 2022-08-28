package br.com.tqi.bootcamp.bookstore.controllers;

import br.com.tqi.bootcamp.bookstore.Utils;
import br.com.tqi.bootcamp.bookstore.api.ClientController;
import br.com.tqi.bootcamp.bookstore.api.request.BookRequest;
import br.com.tqi.bootcamp.bookstore.api.request.ClientRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ClientControllerValidationTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientController)
                .build();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "          ", "aaaa", "asdfasdfasdfasdfasdfadfasdfadfasdfasdfasdfadfasdfaa"})
    void shouldReturnBadRequestWhenClientNameHasInvalidValue(String name) throws Exception {
        ClientRequest request = successClientRequest();
        request.setName(name);

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenClientNameIsNull() throws Exception {
        ClientRequest request = successClientRequest();
        request.setName(null);

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "          ", "aaaa", "asdfasdfsdfasdfadfafsdfasdfadfadfasdfasdfadfasdfasdfasdfasdafasdfadfasdfasdfasdfasdfasdasfasdfasdfaaa"})
    void shouldReturnBadRequestWhenClientAddressHasInvalidValue(String address) throws Exception {
        ClientRequest request = successClientRequest();
        request.setAddress(address);

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenClientDescriptionIsNull() throws Exception {
        ClientRequest request = successClientRequest();
        request.setAddress(null);

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "           ", "aaabbbcccdd", "1234567890", "089484424221"})
    void shouldReturnBadRequestWhenBookCPFHasInvalidValue(String cpf) throws Exception {
        ClientRequest request = successClientRequest();
        request.setCpf(cpf);

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenCPFIsNull() throws Exception {
        ClientRequest request = successClientRequest();
        request.setCpf(null);

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    private ClientRequest successClientRequest() {
        return ClientRequest.builder()
                .name("teste")
                .address("rua 1")
                .birthDate(LocalDate.now())
                .cpf("11111111111")
                .build();
    }
}
