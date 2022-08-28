package br.com.tqi.bootcamp.bookstore.controllers;

import br.com.tqi.bootcamp.bookstore.Utils;
import br.com.tqi.bootcamp.bookstore.api.AuthorController;
import br.com.tqi.bootcamp.bookstore.api.request.AuthorRequest;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AuthorControllerValidationTest {

    private MockMvc mockMvc;

    @InjectMocks
    private AuthorController authorController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authorController)
                .build();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "          ", "aaaa", "asdfasdfasdfasdfasdfadfasdfadfasdfasdfasdfadfasdfaa"})
    void shouldReturnBadRequestWhenAuthorNameHasInvalidValue(String name) throws Exception {
        AuthorRequest request = successAuthorRequest();
        request.setName(name);

        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenAuthorNameIsNull() throws Exception {
        AuthorRequest request = successAuthorRequest();
        request.setName(null);

        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "          ", "aaaa", "asdfasdfsdfasdfadfafsdfasdfadfadfasdfasdfadfasdfasdfasdfasdafasdfadfasdfasdfasdfasdfasdasfasdfasdfaaa"})
    void shouldReturnBadRequestWhenAuthorDescriptionHasInvalidValue(String description) throws Exception {
        AuthorRequest request = successAuthorRequest();
        request.setDescription(description);

        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenAuthorDescriptionIsNull() throws Exception {
        AuthorRequest request = successAuthorRequest();
        request.setDescription(null);

        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    private AuthorRequest successAuthorRequest() {
        return AuthorRequest.builder()
                .name("Author Name")
                .description("best of the best of best")
                .build();
    }
}
