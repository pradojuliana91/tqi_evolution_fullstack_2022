package br.com.tqi.bootcamp.bookstore.controllers;

import br.com.tqi.bootcamp.bookstore.Utils;
import br.com.tqi.bootcamp.bookstore.api.BookController;
import br.com.tqi.bootcamp.bookstore.api.request.BookEntryRequest;
import br.com.tqi.bootcamp.bookstore.api.request.BookRequest;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BookControllerValidationTest {

    private MockMvc mockMvc;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .build();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "Shor", "The name of this book contains more than one hundred characters xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"})
    void shouldReturnBadRequestWhenBookNameHasInvalidValue(String name) throws Exception {
        BookRequest request = successBookRequest();
        request.setTitle(name);

        mockMvc.perform(multipart("/books").file("file", Utils.createMockedFile().getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("title", request.getTitle())
                        .param("authorCode", request.getAuthorCode())
                        .param("publisher", request.getPublisher())
                        .param("yearPublication", request.getYearPublication())
                        .param("saleValue", request.getSaleValue())
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenBookNameIsNull() throws Exception {
        BookRequest request = successBookRequest();
        request.setTitle(null);

        mockMvc.perform(multipart("/books").file("file", Utils.createMockedFile().getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("title", request.getTitle())
                        .param("authorCode", request.getAuthorCode())
                        .param("publisher", request.getPublisher())
                        .param("yearPublication", request.getYearPublication())
                        .param("saleValue", request.getSaleValue())
                )
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "@", "100."})
    void shouldReturnBadRequestWhenBookPriceHasInvalidValue(String price) throws Exception {
        BookRequest request = successBookRequest();
        request.setSaleValue(price);


        mockMvc.perform(multipart("/books").file("file", Utils.createMockedFile().getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("title", request.getTitle())
                        .param("authorCode", request.getAuthorCode())
                        .param("publisher", request.getPublisher())
                        .param("yearPublication", request.getYearPublication())
                        .param("saleValue", request.getSaleValue())
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenBookPriceIsNull() throws Exception {
        BookRequest request = successBookRequest();
        request.setSaleValue(null);

        mockMvc.perform(multipart("/books").file("file", Utils.createMockedFile().getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("title", request.getTitle())
                        .param("authorCode", request.getAuthorCode())
                        .param("publisher", request.getPublisher())
                        .param("yearPublication", request.getYearPublication())
                        .param("saleValue", request.getSaleValue())
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenBookPriceIsLessThan100Cents() throws Exception {
        BookRequest request = successBookRequest();
        request.setSaleValue("99");

        mockMvc.perform(multipart("/books").file("file", Utils.createMockedFile().getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("title", request.getTitle())
                        .param("authorCode", request.getAuthorCode())
                        .param("publisher", request.getPublisher())
                        .param("yearPublication", request.getYearPublication())
                        .param("saleValue", request.getSaleValue())
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenBookPriceIsGreaterThan10000000Cents() throws Exception {
        BookRequest request = successBookRequest();
        request.setSaleValue("10000001");

        mockMvc.perform(multipart("/books").file("file", Utils.createMockedFile().getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("title", request.getTitle())
                        .param("authorCode", request.getAuthorCode())
                        .param("publisher", request.getPublisher())
                        .param("yearPublication", request.getYearPublication())
                        .param("saleValue", request.getSaleValue())
                )
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void shouldReturnBadRequestWhenBookAuthorCodeHasInvalidValue(String authorCode) throws Exception {
        BookRequest request = successBookRequest();
        request.setAuthorCode(authorCode);


        mockMvc.perform(multipart("/books").file("file", Utils.createMockedFile().getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("title", request.getTitle())
                        .param("authorCode", request.getAuthorCode())
                        .param("publisher", request.getPublisher())
                        .param("yearPublication", request.getYearPublication())
                        .param("saleValue", request.getSaleValue())
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenBookAuthorCodeIsNull() throws Exception {
        BookRequest request = successBookRequest();
        request.setAuthorCode(null);

        mockMvc.perform(multipart("/books").file("file", Utils.createMockedFile().getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("title", request.getTitle())
                        .param("authorCode", request.getAuthorCode())
                        .param("publisher", request.getPublisher())
                        .param("yearPublication", request.getYearPublication())
                        .param("saleValue", request.getSaleValue())
                )
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "         ", "aaaa", "asdfasdfsdfasdfadfafsdfasdfadfadfasdfasdfadfasdfasdfasdfasdafasdfadfasdfasdfasdfasdfasdasfasdfasdfaaa"})
    void shouldReturnBadRequestWhenBookPublisherHasInvalidValue(String publisher) throws Exception {
        BookRequest request = successBookRequest();
        request.setPublisher(publisher);

        mockMvc.perform(multipart("/books").file("file", Utils.createMockedFile().getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("title", request.getTitle())
                        .param("authorCode", request.getAuthorCode())
                        .param("publisher", request.getPublisher())
                        .param("yearPublication", request.getYearPublication())
                        .param("saleValue", request.getSaleValue())
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenPublisherIsNull() throws Exception {
        BookRequest request = successBookRequest();
        request.setPublisher(null);

        mockMvc.perform(multipart("/books").file("file", Utils.createMockedFile().getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("title", request.getTitle())
                        .param("authorCode", request.getAuthorCode())
                        .param("publisher", request.getPublisher())
                        .param("yearPublication", request.getYearPublication())
                        .param("saleValue", request.getSaleValue())
                )
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "         ", "a", "aaaa", "111", "33333"})
    void shouldReturnBadRequestWhenBookYearPublicationHasInvalidValue(String yearPublication) throws Exception {
        BookRequest request = successBookRequest();
        request.setYearPublication(yearPublication);

        mockMvc.perform(multipart("/books").file("file", Utils.createMockedFile().getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("title", request.getTitle())
                        .param("authorCode", request.getAuthorCode())
                        .param("publisher", request.getPublisher())
                        .param("yearPublication", request.getYearPublication())
                        .param("saleValue", request.getSaleValue())
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenYearPublicationIsNull() throws Exception {
        BookRequest request = successBookRequest();
        request.setYearPublication(null);

        mockMvc.perform(multipart("/books").file("file", Utils.createMockedFile().getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("title", request.getTitle())
                        .param("authorCode", request.getAuthorCode())
                        .param("publisher", request.getPublisher())
                        .param("yearPublication", request.getYearPublication())
                        .param("saleValue", request.getSaleValue())
                )
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "         ", "0", "-100","10000001"})
    void shouldReturnBadRequestWhenQUantityHasInvalidValue(String quantity) throws Exception {
        BookEntryRequest bookEntryRequest = Utils.createBookEntrySuccessRequest();
        bookEntryRequest.setQuantity(quantity);

        mockMvc.perform(post("/books/38AF7FE5-7CA1-4FCD-A0EB-602406FB7FE2/entry")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookEntryRequest))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenQuantityIsNull() throws Exception {
        BookEntryRequest bookEntryRequest = Utils.createBookEntrySuccessRequest();
        bookEntryRequest.setQuantity(null);


        mockMvc.perform(post("/books/38AF7FE5-7CA1-4FCD-A0EB-602406FB7FE2/entry")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookEntryRequest))
                )
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "         ", "0", "-100","10000001"})
    void shouldReturnBadRequestWhenCostValueHasInvalidValue(String costValue) throws Exception {
        BookEntryRequest bookEntryRequest = Utils.createBookEntrySuccessRequest();
        bookEntryRequest.setCostValue(costValue);

        mockMvc.perform(post("/books/38AF7FE5-7CA1-4FCD-A0EB-602406FB7FE2/entry")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookEntryRequest))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenCostValueIsNull() throws Exception {
        BookEntryRequest bookEntryRequest = Utils.createBookEntrySuccessRequest();
        bookEntryRequest.setCostValue(null);


        mockMvc.perform(post("/books/38AF7FE5-7CA1-4FCD-A0EB-602406FB7FE2/entry")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookEntryRequest))
                )
                .andExpect(status().isBadRequest());
    }

    private BookRequest successBookRequest() {
        return BookRequest.builder()
                .title("Book Name")
                .authorCode("2E126BBC-FC6E-4382-B816-C3EC730FDBE1")
                .publisher("editora abril")
                .yearPublication("1988")
                .saleValue("100")
                .file(Utils.createMockedFile())
                .build();
    }
}
