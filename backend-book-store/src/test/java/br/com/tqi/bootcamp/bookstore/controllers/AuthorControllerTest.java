package br.com.tqi.bootcamp.bookstore.controllers;

import br.com.tqi.bootcamp.bookstore.Utils;
import br.com.tqi.bootcamp.bookstore.api.AuthorController;
import br.com.tqi.bootcamp.bookstore.api.request.AuthorRequest;
import br.com.tqi.bootcamp.bookstore.api.response.AuthorResponse;
import br.com.tqi.bootcamp.bookstore.service.AuthorService;
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
public class AuthorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    private static final String URL_AUTHORS_ENDPOINT = "/authors";
    private static final String URL_AUTHORS_ENDPOINT_ID = "/authors/38AF7FE5-7CA1-4FCD-A0EB-602406FB7FE2";

    private static final String URL_AUTHORS_ENDPOINT_ALL = "/authors/all";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
    }

    @Test
    void shouldReturnCreatedWhenMethodPostIsCalledWithRequestIsValid() throws Exception {
        AuthorRequest request = Utils.createAuthorSuccessRequest();

        AuthorResponse response = Utils.createAuthorSuccessResponse();

        when(authorService.createAuthor(any())).thenReturn(response);

        mockMvc.perform(post(URL_AUTHORS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code", is(notNullValue())))
                .andExpect(jsonPath("$.name", is(response.getName())))
                .andExpect(jsonPath("$.description", is(response.getDescription())));

        verify(authorService, times(1)).createAuthor(any());
    }

    @Test
    void shouldReturnAuthorResponseWhenMethodGetIsCalledWithAuthorId() throws Exception {
        AuthorResponse response = Utils.createAuthorSuccessResponse();

        when(authorService.findAuthor(any())).thenReturn(response);

        mockMvc.perform(get(URL_AUTHORS_ENDPOINT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(notNullValue())))
                .andExpect(jsonPath("$.name", is(response.getName())))
                .andExpect(jsonPath("$.description", is(response.getDescription())));

        verify(authorService, times(1)).findAuthor(any());
    }

    @Test
    void shouldReturnAuthorListResponse() throws Exception {
        var authorPage = Utils.createAuthorResponsePageable();
        when(authorService.getAllAuthorsByPage(any())).thenReturn(authorPage);

        mockMvc.perform(get(URL_AUTHORS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.page", is(1)))
                .andExpect(jsonPath("$.count", is(2)));

        verify(authorService, times(1)).getAllAuthorsByPage(any());
    }

    @Test
    void shouldReturnAuthorListAllResponse() throws Exception {
        List<AuthorResponse> authorResponse = Utils.createAuthorResponseList();

        when(authorService.getAllAuthors()).thenReturn(authorResponse);

        mockMvc.perform(get(URL_AUTHORS_ENDPOINT_ALL)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)));

        verify(authorService, times(1)).getAllAuthors();
    }

    @Test
    void shouldReturnOkWhenPutIsCalledWithRequestIsValid() throws Exception {

        AuthorRequest request = Utils.createAuthorSuccessRequest();

        AuthorResponse response = Utils.createAuthorSuccessResponse();

        when(authorService.updateAuthor(any(), any())).thenReturn(response);

        mockMvc.perform(put(URL_AUTHORS_ENDPOINT_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(notNullValue())))
                .andExpect(jsonPath("$.name", is(response.getName())))
                .andExpect(jsonPath("$.description", is(response.getDescription())));

        verify(authorService, times(1)).updateAuthor(any(), any());
    }

    @Test
    void shouldReturnNoContentWhenAuthorIsDeleted() throws Exception {
        doNothing().when(authorService).deleteAuthor(any());

        mockMvc.perform(delete(URL_AUTHORS_ENDPOINT_ID)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());

        verify(authorService, times(1)).deleteAuthor(any());
    }
}
