package br.com.tqi.bootcamp.bookstore.controllers;

import br.com.tqi.bootcamp.bookstore.Utils;
import br.com.tqi.bootcamp.bookstore.api.ClientController;
import br.com.tqi.bootcamp.bookstore.api.request.ClientRequest;
import br.com.tqi.bootcamp.bookstore.api.response.ClientResponse;
import br.com.tqi.bootcamp.bookstore.service.ClientService;
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
public class ClientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    private static final String URL_CLIENTS_ENDPOINT = "/clients";
    private static final String URL_CLIENTS_ENDPOINT_ID = "/clients/38AF7FE5-7CA1-4FCD-A0EB-602406FB7FE2";

    private static final String URL_CLIENTS_ENDPOINT_ALL = "/clients/all";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    void shouldReturnCreatedWhenMethodPostIsCalledWithRequestIsValid() throws Exception {
        ClientRequest request = Utils.createClientSuccessRequest();

        ClientResponse response = Utils.createClientSuccessResponse();

        when(clientService.createClient(any())).thenReturn(response);

        mockMvc.perform(post(URL_CLIENTS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code", is(notNullValue())))
                .andExpect(jsonPath("$.name", is(response.getName())))
                .andExpect(jsonPath("$.address", is(response.getAddress())))
                .andExpect(jsonPath("$.cpf", is(response.getCpf())));

        verify(clientService, times(1)).createClient(any());
    }

    @Test
    void shouldReturnClientResponseWhenMethodGetIsCalledWithClientId() throws Exception {
        ClientResponse response = Utils.createClientSuccessResponse();

        when(clientService.findClient(any())).thenReturn(response);

        mockMvc.perform(get(URL_CLIENTS_ENDPOINT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(notNullValue())))
                .andExpect(jsonPath("$.name", is(response.getName())))
                .andExpect(jsonPath("$.address", is(response.getAddress())))
                .andExpect(jsonPath("$.cpf", is(response.getCpf())));

        verify(clientService, times(1)).findClient(any());
    }

    @Test
    void shouldReturnClientListResponse() throws Exception {
        var clientPage = Utils.createClientResponsePageable();
        when(clientService.getAllClientsByPage(any())).thenReturn(clientPage);

        mockMvc.perform(get(URL_CLIENTS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.page", is(1)))
                .andExpect(jsonPath("$.count", is(2)));

        verify(clientService, times(1)).getAllClientsByPage(any());
    }

    @Test
    void shouldReturnClientListAllResponse() throws Exception {
        List<ClientResponse> clientResponse = Utils.createClientResponseList();

        when(clientService.getAllClients()).thenReturn(clientResponse);

        mockMvc.perform(get(URL_CLIENTS_ENDPOINT_ALL)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)));

        verify(clientService, times(1)).getAllClients();
    }

    @Test
    void shouldReturnOkWhenPutIsCalledWithRequestIsValid() throws Exception {

        ClientRequest request = Utils.createClientSuccessRequest();

        ClientResponse response = Utils.createClientSuccessResponse();

        when(clientService.updateClient(any(), any())).thenReturn(response);

        mockMvc.perform(put(URL_CLIENTS_ENDPOINT_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(notNullValue())))
                .andExpect(jsonPath("$.name", is(response.getName())))
                .andExpect(jsonPath("$.address", is(response.getAddress())))
                .andExpect(jsonPath("$.cpf", is(response.getCpf())));

        verify(clientService, times(1)).updateClient(any(), any());
    }

    @Test
    void shouldReturnNoContentWhenClientIsDeleted() throws Exception {
        doNothing().when(clientService).deleteClient(any());

        mockMvc.perform(delete(URL_CLIENTS_ENDPOINT_ID)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());

        verify(clientService, times(1)).deleteClient(any());
    }
}
