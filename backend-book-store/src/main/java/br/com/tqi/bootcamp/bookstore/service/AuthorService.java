package br.com.tqi.bootcamp.bookstore.service;

import br.com.tqi.bootcamp.bookstore.api.request.AuthorRequest;
import br.com.tqi.bootcamp.bookstore.api.response.AuthorResponse;
import br.com.tqi.bootcamp.bookstore.api.response.AuthorResponsePageable;
import br.com.tqi.bootcamp.bookstore.exception.NotFoundException;
import br.com.tqi.bootcamp.bookstore.model.AuthorEntity;
import br.com.tqi.bootcamp.bookstore.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;


    public AuthorResponse createAuthor(AuthorRequest request) {
        return AuthorResponse.entityToResponse(authorRepository.save(request.toEntity()));
    }

    public AuthorResponse findAuthor(final String code) {
        return AuthorResponse.entityToResponse(findAuthorByCode(code));
    }

    public AuthorResponsePageable getAllAuthorsByPage(final Pageable pageable) {
        return AuthorResponsePageable.toResponse(authorRepository.findAll(pageable));
    }

    public List<AuthorResponse> getAllAuthors() {
        Iterable<AuthorEntity> authors = authorRepository.findAll();
        if (IterableUtils.isEmpty(authors)) {
            throw new NotFoundException("author(s) not found");
        }
        return AuthorResponse.entityToResponse(authors);
    }

    public void deleteAuthor(final String code) {
        AuthorEntity entity = findAuthorByCode(code);
        authorRepository.delete(entity);
    }

    public AuthorResponse updateAuthor(final String code, final AuthorRequest request) {
        AuthorEntity authorEntity = findAuthorByCode(code);
        replaceAuthorEntity(authorEntity, request);

        authorRepository.save(authorEntity);

        return AuthorResponse.entityToResponse(authorEntity);
    }

    public AuthorEntity findAuthorByCode(final String code) {
        return authorRepository.findByCode(code).orElseThrow(() -> new NotFoundException("author not found"));
    }

    private void replaceAuthorEntity(AuthorEntity entity, final AuthorRequest request) {
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
    }
}
