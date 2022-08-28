package br.com.tqi.bootcamp.bookstore.service;

import br.com.tqi.bootcamp.bookstore.api.request.BookEntryRequest;
import br.com.tqi.bootcamp.bookstore.api.request.BookRequest;
import br.com.tqi.bootcamp.bookstore.api.response.BookResponse;
import br.com.tqi.bootcamp.bookstore.api.response.BookResponsePageable;
import br.com.tqi.bootcamp.bookstore.api.response.ClientResponse;
import br.com.tqi.bootcamp.bookstore.exception.APIBusinessException;
import br.com.tqi.bootcamp.bookstore.exception.NotFoundException;
import br.com.tqi.bootcamp.bookstore.model.AuthorEntity;
import br.com.tqi.bootcamp.bookstore.model.BookEntity;
import br.com.tqi.bootcamp.bookstore.model.BookEntryEntity;
import br.com.tqi.bootcamp.bookstore.model.ClientEntity;
import br.com.tqi.bootcamp.bookstore.repository.BookEntryRepository;
import br.com.tqi.bootcamp.bookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private final BookEntryRepository bookEntryRepository;
    private final AuthorService authorService;
    private final FileService fileService;

    @Transactional
    public BookResponse createBook(BookRequest request) {
        AuthorEntity authorEntity = authorService.findAuthorByCode(request.getAuthorCode());

        String fileName = generateFileName();
        String urlImage = fileService.persist(request.getFile(), fileName);

        BookEntity bookEntity = new BookEntity(request, authorEntity, urlImage);
        return BookResponse.entityToResponse(bookRepository.save(bookEntity));
    }

    public BookResponse findBook(final String code) {
        return BookResponse.entityToResponse(findBookByCode(code));
    }

    public BookResponsePageable getAllBooks(final Pageable pageable) {
        return BookResponsePageable.toResponse(bookRepository.findAll(pageable));
    }

    public List<BookResponse> getAllBooks() {
        Iterable<BookEntity> books = bookRepository.findAll();
        if (IterableUtils.isEmpty(books)) {
            throw new NotFoundException("book(s) not found");
        }
        return BookResponse.entityToResponse(books);
    }

    @Transactional
    public void deleteBook(final String code) {
        BookEntity entity = findBookByCode(code);
        bookRepository.delete(entity);
        fileService.delete(entity.getImage());
    }

    @Transactional
    public BookResponse updateBook(final String code, final BookRequest request) {
        BookEntity bookEntity = findBookByCode(code);
        AuthorEntity authorEntity = authorService.findAuthorByCode(request.getAuthorCode());
        String oldImage = bookEntity.getImage();
        String urlImage = fileService.persist(request.getFile(), generateFileName());
        replaceBookEntity(bookEntity, authorEntity, request, urlImage);

        bookRepository.save(bookEntity);

        fileService.delete(oldImage);

        return BookResponse.entityToResponse(bookEntity);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public BookEntity removeSotck(final String code, final Integer quantity) {
        BookEntity book = findBookByCode(code);
        if (book.getQuantity() < quantity) {
            throw new APIBusinessException("Livro " + book.getTitle() + " só contém " + book.getQuantity() + " em estoque!");
        }
        book.setQuantity(book.getQuantity() - quantity);
        return bookRepository.save(book);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void bookEntry(final String code, BookEntryRequest request) {
        BookEntity bookEntity = findBookByCode(code);
        bookEntity.setCostValue(Integer.valueOf(request.getCostValue()));
        bookEntity.setQuantity(Integer.parseInt(request.getQuantity()) + bookEntity.getQuantity());

        bookRepository.save(bookEntity);

        BookEntryEntity bookEntryEntity = BookEntryEntity.builder()
                .book(bookEntity)
                .costValue(Integer.valueOf(request.getCostValue()))
                .quantity(Integer.valueOf(request.getQuantity()))
                .createdAt(LocalDateTime.now())
                .build();

        bookEntryRepository.save(bookEntryEntity);
    }

    private BookEntity findBookByCode(final String code) {
        return bookRepository.findByCode(code).orElseThrow(() -> new NotFoundException("book not found"));
    }

    private void replaceBookEntity(BookEntity entity, final AuthorEntity author, final BookRequest request, final String urlImage) {
        entity.setTitle(request.getTitle());
        entity.setAuthor(author);
        entity.setPublisher(request.getPublisher());
        entity.setYearPublication(Integer.valueOf(request.getYearPublication()));
        entity.setSaleValue(Integer.valueOf(request.getSaleValue()));
        entity.setImage(urlImage);
    }

    private String generateFileName() {
        return UUID.randomUUID().toString().replace("-", "_");
    }
}
