package br.com.tqi.bootcamp.bookstore.services;

import br.com.tqi.bootcamp.bookstore.Utils;
import br.com.tqi.bootcamp.bookstore.api.request.BookEntryRequest;
import br.com.tqi.bootcamp.bookstore.api.request.BookRequest;
import br.com.tqi.bootcamp.bookstore.api.request.SaleRequest;
import br.com.tqi.bootcamp.bookstore.api.response.BookResponse;
import br.com.tqi.bootcamp.bookstore.api.response.BookResponsePageable;
import br.com.tqi.bootcamp.bookstore.exception.APIBusinessException;
import br.com.tqi.bootcamp.bookstore.exception.NotFoundException;
import br.com.tqi.bootcamp.bookstore.model.*;
import br.com.tqi.bootcamp.bookstore.repository.SaleDetailRepository;
import br.com.tqi.bootcamp.bookstore.repository.SaleRepository;
import br.com.tqi.bootcamp.bookstore.service.BookService;
import br.com.tqi.bootcamp.bookstore.service.ClientService;
import br.com.tqi.bootcamp.bookstore.service.SaleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaleServiceTest {

    @Mock
    private BookService bookService;

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private SaleDetailRepository saleDetailRepository;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private SaleService saleService;

    @Test
    void shouldSale() {
        SaleRequest saleRequest = Utils.createSaleRequest();
        ClientEntity clientEntity = Utils.createClientEntity();

        when(clientService.findClientByCode(saleRequest.getClientCode())).thenReturn(clientEntity);
        for (var saleDetailRequest : saleRequest.getDetails()) {
            when(bookService.removeSotck(saleDetailRequest.getBookCode(),
                    Integer.parseInt(saleDetailRequest.getQuantity()))).thenReturn(Utils.createBookEntity(saleDetailRequest.getBookCode()));
        }

        when(saleRepository.save(any())).thenReturn(SaleEntity.builder().build());
        when(saleDetailRepository.saveAll(any())).thenReturn(new ArrayList<>());

        var response = saleService.sale(saleRequest);

        verify(clientService, times(1)).findClientByCode(any());
        verify(bookService, times(2)).removeSotck(any(), any());
        verify(saleRepository, times(1)).save(any());
        verify(saleDetailRepository, times(1)).saveAll(any());

        assertNotNull(response.getCode());
        assertEquals(response.getClient().getCode(), clientEntity.getCode());
        assertEquals(response.getDetails().size(), saleRequest.getDetails().size());
    }

    @Test
    void shouldThrowSaleEmptyAndNullDetail() {
        SaleRequest saleRequestNull = Utils.createSaleWithDatailnullRequest();
        SaleRequest saleRequestEmpty = Utils.createSaleWithDatailEmptyRequest();


        APIBusinessException exceptionNull = assertThrows(APIBusinessException.class, () -> saleService.sale(saleRequestNull));
        APIBusinessException execptionEmpty = assertThrows(APIBusinessException.class, () -> saleService.sale(saleRequestEmpty));

        assertEquals("Não existe livro(s) na venda!", exceptionNull.getMessage());
        assertEquals("Não existe livro(s) na venda!", execptionEmpty.getMessage());
    }

    @Test
    void shouldThrowSaleDuplicateBook() {
        SaleRequest saleRequest = Utils.createSaleDuplicateDetailBookRequest();

        APIBusinessException exceptionNull = assertThrows(APIBusinessException.class, () -> saleService.sale(saleRequest));

        assertEquals("Existe livro(s) duplicado(s) na venda!", exceptionNull.getMessage());
    }

    @Test
    void shouldThrowSaleWithNotFoundClient() {
        SaleRequest saleRequest = Utils.createSaleRequest();

        when(clientService.findClientByCode(saleRequest.getClientCode())).thenThrow(new NotFoundException("Cliente Not Found"));

        NotFoundException exception = assertThrows(NotFoundException.class, () -> saleService.sale(saleRequest));

        verify(clientService, times(1)).findClientByCode(any());
        verify(bookService, never()).removeSotck(any(), any());
        verify(saleRepository, never()).save(any());
        verify(saleDetailRepository, never()).saveAll(any());

        assertEquals("Cliente Not Found", exception.getMessage());
    }

    @Test
    void shouldThrowSaleWithNotFoundBook() {
        SaleRequest saleRequest = Utils.createSaleRequest();
        ClientEntity clientEntity = Utils.createClientEntity();

        when(clientService.findClientByCode(saleRequest.getClientCode())).thenReturn(clientEntity);
        when(bookService.removeSotck(any(),any())).thenThrow(new NotFoundException("Book not found"));

        NotFoundException exception = assertThrows(NotFoundException.class, () -> saleService.sale(saleRequest));

        verify(clientService, times(1)).findClientByCode(any());
        verify(bookService, times(1)).removeSotck(any(), any());
        verify(saleRepository, never()).save(any());
        verify(saleDetailRepository, never()).saveAll(any());

        assertEquals("Book not found", exception.getMessage());
    }
}
