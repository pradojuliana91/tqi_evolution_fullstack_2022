package br.com.tqi.bootcamp.bookstore.services;

import br.com.tqi.bootcamp.bookstore.Utils;
import br.com.tqi.bootcamp.bookstore.api.request.AuthorRequest;
import br.com.tqi.bootcamp.bookstore.api.response.AuthorResponse;
import br.com.tqi.bootcamp.bookstore.api.response.AuthorResponsePageable;
import br.com.tqi.bootcamp.bookstore.exception.NotFoundException;
import br.com.tqi.bootcamp.bookstore.model.AuthorEntity;
import br.com.tqi.bootcamp.bookstore.repository.AuthorRepository;
import br.com.tqi.bootcamp.bookstore.service.AuthorService;
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
public class AuthorServiceTest {

    @InjectMocks
    private AuthorService authorService;

    @Mock
    private AuthorRepository authorRepository;

    @Test
    void shouldCreateAuthor() {
        AuthorRequest request = Utils.createAuthorRequest();

        when(authorRepository.save(any())).thenReturn(request.toEntity());

        AuthorResponse response = authorService.createAuthor(request);

        verify(authorRepository, times(1)).save(any());

        assertEquals(request.getName(), response.getName());
        assertEquals(request.getDescription(), response.getDescription());
        assertNotNull(response.getCode());
    }

    @Test
    void shouldFindAuthorExists() {
        AuthorEntity authorEntity = Utils.findAuthorEntity();

        when(authorRepository.findByCode(authorEntity.getCode())).thenReturn(Optional.of(authorEntity));

        AuthorResponse response = authorService.findAuthor(authorEntity.getCode());

        assertNotNull(authorEntity.getId());
        assertEquals(response.getCode(), authorEntity.getCode());
        assertEquals(response.getName(), authorEntity.getName());
        assertEquals(response.getDescription(), authorEntity.getDescription());
    }

    @Test
    void shouldThrowFindAuthorNotExists() {
        String code = UUID.randomUUID().toString();

        when(authorRepository.findByCode(code)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> authorService.findAuthor(code));
    }

    @Test
    void shouldReturnAuthorListPageResponse() {
        Page<AuthorEntity> page = Utils.createPageAuthorEntity();
        Pageable paging = PageRequest.of(1, 1);

        when(authorRepository.findAll(paging)).thenReturn(page);

        AuthorResponsePageable response = authorService.getAllAuthorsByPage(paging);

        verify(authorRepository, times(1)).findAll(paging);
        assertEquals(2, response.getCount());
        assertEquals(0, response.getPage());
    }

    @Test
    void shouldReturnAuthorListResponse() {
        List<AuthorEntity> authors = Utils.createAuthorEntityList();

        when(authorRepository.findAll()).thenReturn(authors);

        List<AuthorResponse> response = authorService.getAllAuthors();

        assertEquals(response.size(), authors.size());
    }

    @Test
    void shouldThrowReturnAuthorListResponseNotExists() {
        List<AuthorEntity> authors = new ArrayList<>();

        when(authorRepository.findAll()).thenReturn(authors);

        assertThrows(NotFoundException.class, () -> authorService.getAllAuthors());
    }

    @Test
    void shouldDeleteAuthor() {
        AuthorEntity authorEntity = Utils.createAuthorEntity(UUID.randomUUID().toString());

        when(authorRepository.findByCode(authorEntity.getCode())).thenReturn(Optional.of(authorEntity));

        authorService.deleteAuthor(authorEntity.getCode());

        verify(authorRepository, times(1)).delete(authorEntity);
    }

    @Test
    void shouldThrowDeleteAuthorNotExists() {
        String code = UUID.randomUUID().toString();

        when(authorRepository.findByCode(code)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> authorService.deleteAuthor(code));

        verify(authorRepository, never()).delete(any());
    }

    @Test
    void shouldReturnAuthorResponseWhenAuthorIsUpdated() {
        AuthorRequest request = Utils.createAuthorRequest();
        AuthorEntity authorEntity = Utils.createAuthorEntity(UUID.randomUUID().toString());

        when(authorRepository.findByCode(authorEntity.getCode())).thenReturn(Optional.of(authorEntity));

        when(authorRepository.save(any())).thenReturn(authorEntity);

        AuthorResponse response = authorService.updateAuthor(authorEntity.getCode(), request);

        verify(authorRepository, times(1)).findByCode(authorEntity.getCode());
        verify(authorRepository, times(1)).save(any());

        assertEquals(authorEntity.getCode(), response.getCode());
        assertEquals(authorEntity.getName(), response.getName());
        assertEquals(authorEntity.getDescription(), response.getDescription());
    }

    @Test
    void shouldThrowReturnAuthorResponseWhenAuthorIsUpdatedNotExists() {
        AuthorRequest request = Utils.createAuthorRequest();
        String code = UUID.randomUUID().toString();

        when(authorRepository.findByCode(code)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> authorService.updateAuthor(code, request));

        verify(authorRepository, times(1)).findByCode(code);
        verify(authorRepository, never()).save(any());
    }
}
