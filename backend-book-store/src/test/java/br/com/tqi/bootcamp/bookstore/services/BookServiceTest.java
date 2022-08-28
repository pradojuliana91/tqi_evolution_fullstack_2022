package br.com.tqi.bootcamp.bookstore.services;

import br.com.tqi.bootcamp.bookstore.Utils;
import br.com.tqi.bootcamp.bookstore.api.request.BookEntryRequest;
import br.com.tqi.bootcamp.bookstore.api.request.BookRequest;
import br.com.tqi.bootcamp.bookstore.api.response.BookResponse;
import br.com.tqi.bootcamp.bookstore.api.response.BookResponsePageable;
import br.com.tqi.bootcamp.bookstore.exception.APIBusinessException;
import br.com.tqi.bootcamp.bookstore.exception.NotFoundException;
import br.com.tqi.bootcamp.bookstore.model.AuthorEntity;
import br.com.tqi.bootcamp.bookstore.model.BookEntity;
import br.com.tqi.bootcamp.bookstore.model.BookEntryEntity;
import br.com.tqi.bootcamp.bookstore.repository.BookEntryRepository;
import br.com.tqi.bootcamp.bookstore.repository.BookRepository;
import br.com.tqi.bootcamp.bookstore.service.AuthorService;
import br.com.tqi.bootcamp.bookstore.service.BookService;
import br.com.tqi.bootcamp.bookstore.service.FileService;
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
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorService authorService;

    @Mock
    private FileService fileService;

    @Mock
    private BookEntryRepository bookEntryRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void shouldCreateBookWhenBookRequestIsValid() {
        BookRequest request = Utils.createBookRequest();
        AuthorEntity authorEntity = Utils.createAuthorEntity(request.getAuthorCode());
        BookEntity expectedBookEntity = new BookEntity(request, authorEntity, "urlMockName");

        when(authorService.findAuthorByCode(request.getAuthorCode())).thenReturn(authorEntity);
        when(bookRepository.save(any())).thenReturn(expectedBookEntity);
        when(fileService.persist(any(), any())).thenReturn("fileName");

        BookResponse response = bookService.createBook(request);

        verify(authorService, times(1)).findAuthorByCode(request.getAuthorCode());
        verify(bookRepository, times(1)).save(any());
        verify(fileService, times(1)).persist(any(), any());

        assertEquals(response.getTitle(), request.getTitle());
        assertEquals(response.getPublisher(), request.getPublisher());
        assertEquals(response.getYearPublication().toString(), request.getYearPublication());
        assertEquals(response.getCostValue(), 0);
        assertEquals(response.getQuantity(), 0);
        assertEquals(response.getAuthor().getCode(), request.getAuthorCode());
        assertEquals(response.getSaleValue().toString(), request.getSaleValue());

        assertNotNull(response.getCode());
        assertNotNull(response.getImage());
    }

    @Test
    void shouldNotCreateBookWhenNotExistsAuthor() {
        BookRequest request = Utils.createBookRequest();

        when(authorService.findAuthorByCode(request.getAuthorCode())).thenThrow(new NotFoundException("not found author"));

        assertThrows(NotFoundException.class, () -> bookService.createBook(request));

        verify(authorService, times(1)).findAuthorByCode(request.getAuthorCode());
        verify(bookRepository, never()).save(any());
        verify(fileService, never()).persist(any(), any());
    }

    @Test
    void shouldThrowExceptionWhenPersistenceFileFailed() {
        BookRequest request = Utils.createBookRequest();
        AuthorEntity authorEntity = Utils.createAuthorEntity(request.getAuthorCode());

        when(authorService.findAuthorByCode(request.getAuthorCode())).thenReturn(authorEntity);
        when(fileService.persist(any(), any())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> bookService.createBook(request));

        verify(authorService, times(1)).findAuthorByCode(request.getAuthorCode());
        verify(bookRepository, times(0)).save(any());
        verify(fileService, times(1)).persist(any(), any());
    }

    @Test
    void shouldFindBookExists() {
        BookEntity bookEntity = Utils.createBookEntity();

        when(bookRepository.findByCode(bookEntity.getCode())).thenReturn(Optional.of(bookEntity));

        BookResponse response = bookService.findBook(bookEntity.getCode());

        assertNotNull(bookEntity.getId());
        assertEquals(response.getCode(), bookEntity.getCode());
        assertEquals(response.getTitle(), bookEntity.getTitle());
        assertEquals(response.getPublisher(), bookEntity.getPublisher());
        assertEquals(response.getImage(), bookEntity.getImage());
        assertEquals(response.getYearPublication(), bookEntity.getYearPublication());
        assertEquals(response.getCostValue(), bookEntity.getCostValue());
        assertEquals(response.getSaleValue(), bookEntity.getSaleValue());
        assertEquals(response.getQuantity(), bookEntity.getQuantity());
        assertEquals(response.getAuthor().getCode(), bookEntity.getAuthor().getCode());
        assertEquals(response.getAuthor().getName(), bookEntity.getAuthor().getName());
        assertEquals(response.getAuthor().getDescription(), bookEntity.getAuthor().getDescription());
    }

    @Test
    void shouldThrowFindBookNotExists() {
        String code = UUID.randomUUID().toString();

        when(bookRepository.findByCode(code)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.findBook(code));
    }

    @Test
    void shouldReturnBookListPageResponse() {
        Page<BookEntity> page = Utils.createPageBookEntity();
        Pageable paging = PageRequest.of(1, 1);

        when(bookRepository.findAll(paging)).thenReturn(page);

        BookResponsePageable response = bookService.getAllBooks(paging);

        verify(bookRepository, times(1)).findAll(paging);
        assertEquals(2, response.getCount());
        assertEquals(0, response.getPage());
    }

    @Test
    void shouldReturnBookListResponse() {
        List<BookEntity> books = Utils.createBookEntityList();

        when(bookRepository.findAll()).thenReturn(books);

        List<BookResponse> response = bookService.getAllBooks();

        assertEquals(response.size(), books.size());
    }

    @Test
    void shouldThrowReturnBookListResponseNotExists() {
        List<BookEntity> books = new ArrayList<>();

        when(bookRepository.findAll()).thenReturn(books);

        assertThrows(NotFoundException.class, () -> bookService.getAllBooks());
    }

    @Test
    void shouldDeleteBook() {
        BookEntity bookEntity = Utils.createBookEntity();

        when(bookRepository.findByCode(any())).thenReturn(Optional.of(bookEntity));

        bookService.deleteBook(any());

        verify(bookRepository, times(1)).delete(any());
        verify(fileService, times(1)).delete(any());
    }

    @Test
    void shouldThrowBookNotFoundExceptionWhenDeleteBookIsCalledWithBookEntityNonExistent() {
        when(bookRepository.findByCode(any())).thenThrow(new NotFoundException("Book not found"));

        assertThrows(NotFoundException.class, () -> bookService.deleteBook(any()));

        verify(bookRepository, times(1)).findByCode(any());
        verify(fileService, times(0)).delete(any());
    }

    @Test
    void shouldReturnBookResponseWhenBookIsUpdated() {
        BookRequest request = Utils.createBookRequest();
        AuthorEntity authorEntity = Utils.createAuthorEntity(UUID.randomUUID().toString());
        BookEntity bookEntity = Utils.createBookEntity();

        when(authorService.findAuthorByCode(any())).thenReturn(authorEntity);
        when(bookRepository.findByCode(any())).thenReturn(Optional.of(bookEntity));
        when(bookRepository.save(any())).thenReturn(bookEntity);
        when(fileService.persist(any(), any())).thenReturn("fileName");

        BookResponse response = bookService.updateBook(any(), request);

        verify(authorService, times(1)).findAuthorByCode(request.getAuthorCode());
        verify(bookRepository, times(1)).findByCode(any());
        verify(bookRepository, times(1)).save(any());
        verify(fileService, times(1)).persist(any(), any());
        verify(fileService, times(1)).delete(any());

        assertEquals(bookEntity.getTitle(), response.getTitle());
        assertEquals(bookEntity.getSaleValue(), response.getSaleValue());
        assertEquals(bookEntity.getAuthor().getName(), response.getAuthor().getName());
        assertNotNull(response.getCode());
        assertNotNull(response.getImage());
    }

    @Test
    void shouldThrowAuthorNotFoundWhenUpdateBookIsCalledWithNonExistentBook() {
        BookRequest request = Utils.createBookRequest();

        when(bookRepository.findByCode(any())).thenThrow(new NotFoundException("Book not found"));

        NotFoundException exception = assertThrows(NotFoundException.class, () -> bookService.updateBook(any(), request));

        verify(authorService, times(0)).findAuthorByCode(request.getAuthorCode());
        verify(bookRepository, times(1)).findByCode(any());
        verify(bookRepository, times(0)).save(any());
        verify(fileService, times(0)).persist(any(), any());
        verify(fileService, times(0)).delete(any());
        assertEquals("Book not found", exception.getMessage());
    }

    @Test
    void shouldThrowAuthorNotFoundWhenAuthorEntityDoesNotExists() {
        BookRequest request = Utils.createBookRequest();
        BookEntity bookEntity = Utils.createBookEntity();

        when(bookRepository.findByCode(any())).thenReturn(Optional.of(bookEntity));
        when(authorService.findAuthorByCode(any())).thenThrow(new NotFoundException("Author not found"));

        NotFoundException exception = assertThrows(NotFoundException.class, () -> bookService.updateBook(any(), request));

        verify(authorService, times(1)).findAuthorByCode(request.getAuthorCode());
        verify(bookRepository, times(1)).findByCode(any());
        verify(bookRepository, times(0)).save(any());
        verify(fileService, times(0)).persist(any(), any());
        verify(fileService, times(0)).delete(any());
        assertEquals("Author not found", exception.getMessage());
    }

    @Test
    void shouldRemoveStock() {
        BookEntity bookEntity = Utils.createBookEntity();

        var amount_remove_stock = 4;
        var amount_has_in_stock = 5;

        bookEntity.setQuantity(amount_has_in_stock);

        when(bookRepository.findByCode(any())).thenReturn(Optional.of(bookEntity));
        when(bookRepository.save(any())).thenReturn(bookEntity);

        BookEntity response = bookService.removeSotck(bookEntity.getCode(), amount_remove_stock);


        assertEquals(response.getQuantity(), amount_has_in_stock - amount_remove_stock);
    }

    @Test
    void shouldThrowRemoveStockButNotHasStock() {
        BookEntity bookEntity = Utils.createBookEntity();

        var amount_remove_stock = 6;
        var amount_has_in_stock = 5;

        bookEntity.setQuantity(amount_has_in_stock);

        when(bookRepository.findByCode(any())).thenReturn(Optional.of(bookEntity));

        assertThrows(APIBusinessException.class, () -> bookService.removeSotck(bookEntity.getCode(), amount_remove_stock));

        verify(bookRepository, never()).save(any());
    }

    @Test
    void shouldThrowRemoveStockNotFoundBook() {
        when(bookRepository.findByCode(any())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.removeSotck(UUID.randomUUID().toString(), 0));

        verify(bookRepository, never()).save(any());
    }

    @Test
    void shouldBookEntry() {
        BookEntryRequest request = Utils.createBookEntryRequest();
        BookEntity bookEntity = Utils.createBookEntity();
        BookEntryEntity bookEntryEntity = Utils.createBookEntryEntity(bookEntity);
        var bookCode = UUID.randomUUID().toString();

        when(bookRepository.findByCode(bookCode)).thenReturn(Optional.of(bookEntity));
        when(bookRepository.save(any())).thenReturn(bookEntity);
        when(bookEntryRepository.save(any())).thenReturn(bookEntryEntity);


        bookService.bookEntry(bookCode, request);

        verify(bookRepository, times(1)).findByCode(bookCode);
        verify(bookRepository, times(1)).save(any());
        verify(bookEntryRepository, times(1)).save(any());
    }

    @Test
    void shouldTrhowBookEntryNotHasBook() {
        BookEntryRequest request = Utils.createBookEntryRequest();

        var bookCode = UUID.randomUUID().toString();

        when(bookRepository.findByCode(bookCode)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.bookEntry(bookCode, request));

        verify(bookRepository, times(1)).findByCode(bookCode);
        verify(bookRepository, never()).save(any());
        verify(bookEntryRepository, never()).save(any());
    }
}
