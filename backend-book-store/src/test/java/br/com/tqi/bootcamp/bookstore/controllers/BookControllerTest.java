package br.com.tqi.bootcamp.bookstore.controllers;

import br.com.tqi.bootcamp.bookstore.Utils;
import br.com.tqi.bootcamp.bookstore.api.BookController;
import br.com.tqi.bootcamp.bookstore.api.request.BookEntryRequest;
import br.com.tqi.bootcamp.bookstore.api.request.BookRequest;
import br.com.tqi.bootcamp.bookstore.api.response.BookResponse;
import br.com.tqi.bootcamp.bookstore.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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
public class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private static final String URL_BOOKS_ENDPOINT = "/books";
    private static final String URL_BOOKS_ENDPOINT_ID = "/books/38AF7FE5-7CA1-4FCD-A0EB-602406FB7FE2";

    private static final String URL_BOOKS_ENDPOINT_ALL = "/books/all";

    private static final String URL_BOOKS_ENDPOINT_ENTRY = "/books/38AF7FE5-7CA1-4FCD-A0EB-602406FB7FE2/entry";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void shouldReturnCreatedWhenMethodPostIsCalledWithRequestIsValid() throws Exception {
        BookRequest request = Utils.createBookSuccessRequest();

        BookResponse bookResponse = Utils.createBookSuccessResponse();

        when(bookService.createBook(any())).thenReturn(bookResponse);

        mockMvc.perform(requestBuilderFactory(URL_BOOKS_ENDPOINT, HttpMethod.POST).file("file", Utils.createMockedFile().getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("title", request.getTitle())
                        .param("authorCode", request.getAuthorCode())
                        .param("publisher", request.getPublisher())
                        .param("yearPublication", request.getYearPublication())
                        .param("saleValue", request.getSaleValue()))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code", is(notNullValue())))
                .andExpect(jsonPath("$.title", is(bookResponse.getTitle())))
                .andExpect(jsonPath("$.saleValue", is(bookResponse.getSaleValue())))
                .andExpect(jsonPath("$.image", is(notNullValue())))
                .andExpect(jsonPath("$.author", is(notNullValue())));

        verify(bookService, times(1)).createBook(any());
    }

    @Test
    void shouldReturnBookResponseWhenMethodGetIsCalledWithBookId() throws Exception {
        BookResponse bookResponse = Utils.createBookSuccessResponse();

        when(bookService.findBook(any())).thenReturn(bookResponse);

        mockMvc.perform(get(URL_BOOKS_ENDPOINT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(notNullValue())))
                .andExpect(jsonPath("$.title", is(bookResponse.getTitle())))
                .andExpect(jsonPath("$.saleValue", is(bookResponse.getSaleValue())))
                .andExpect(jsonPath("$.image", is(notNullValue())))
                .andExpect(jsonPath("$.author", is(notNullValue())));

        verify(bookService, times(1)).findBook(any());
    }

    @Test
    void shouldReturnBookListResponse() throws Exception {
        var bookPage = Utils.createBookResponsePageable();
        when(bookService.getAllBooks(any())).thenReturn(bookPage);

        mockMvc.perform(get(URL_BOOKS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.page", is(1)))
                .andExpect(jsonPath("$.count", is(2)));

        verify(bookService, times(1)).getAllBooks(any());
    }

    @Test
    void shouldReturnBookListAllResponse() throws Exception {
        List<BookResponse> bookResponse = Utils.createBookResponseList();

        when(bookService.getAllBooks()).thenReturn(bookResponse);

        mockMvc.perform(get(URL_BOOKS_ENDPOINT_ALL)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)));

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void shouldReturnOkWhenPutIsCalledWithRequestIsValid() throws Exception {
        BookRequest request = Utils.createBookSuccessRequest();

        BookResponse bookResponse = BookResponse.builder()
                .code("38AF7FE5-7CA1-4FCD-A0EB-602406FB7FE2")
                .title(request.getTitle())
                .saleValue(Integer.valueOf(request.getSaleValue()))
                .author(Utils.createAuthorSuccessResponse())
                .image("urlImage").build();

        when(bookService.updateBook(any(), any())).thenReturn(bookResponse);

        mockMvc.perform(requestBuilderFactory(URL_BOOKS_ENDPOINT_ID, HttpMethod.PUT).file("file", Utils.createMockedFile().getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("title", request.getTitle())
                        .param("authorCode", request.getAuthorCode())
                        .param("publisher", request.getPublisher())
                        .param("yearPublication", request.getYearPublication())
                        .param("saleValue", request.getSaleValue()))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(notNullValue())))
                .andExpect(jsonPath("$.title", is(bookResponse.getTitle())))
                .andExpect(jsonPath("$.saleValue", is(bookResponse.getSaleValue())))
                .andExpect(jsonPath("$.image", is(notNullValue())))
                .andExpect(jsonPath("$.author", is(notNullValue())));

        verify(bookService, times(1)).updateBook(any(), any());
    }

    @Test
    void shouldReturnNoContentWhenBookIsDeleted() throws Exception {
        doNothing().when(bookService).deleteBook(any());

        mockMvc.perform(delete(URL_BOOKS_ENDPOINT_ID)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());

        verify(bookService, times(1)).deleteBook(any());
    }

    @Test
    void shouldReturnNoContentWhenEntry() throws Exception {
        BookEntryRequest bookEntryRequest = Utils.createBookEntrySuccessRequest();

        doNothing().when(bookService).bookEntry(any(), any());

        mockMvc.perform(post(URL_BOOKS_ENDPOINT_ENTRY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(bookEntryRequest))
        ).andExpect(status().isNoContent());

        verify(bookService, times(1)).bookEntry(any(), any());
    }


    private MockMultipartHttpServletRequestBuilder requestBuilderFactory(String endpoint, HttpMethod httpMethod) {
        return (MockMultipartHttpServletRequestBuilder) MockMvcRequestBuilders.multipart(endpoint).with(request -> {
            request.setMethod(httpMethod.name());
            return request;
        });
    }

}
