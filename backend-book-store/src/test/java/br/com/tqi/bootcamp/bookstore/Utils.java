package br.com.tqi.bootcamp.bookstore;

import br.com.tqi.bootcamp.bookstore.api.request.*;
import br.com.tqi.bootcamp.bookstore.api.response.*;
import br.com.tqi.bootcamp.bookstore.model.AuthorEntity;
import br.com.tqi.bootcamp.bookstore.model.BookEntity;
import br.com.tqi.bootcamp.bookstore.model.BookEntryEntity;
import br.com.tqi.bootcamp.bookstore.model.ClientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Utils {

    public static AuthorRequest createAuthorRequest() {
        return AuthorRequest.builder()
                .name("teste")
                .description("best author roamance")
                .build();
    }

    public static AuthorRequest createAuthorSuccessRequest() {
        return AuthorRequest.builder()
                .name("teste")
                .description("best author roamance")
                .build();
    }

    public static AuthorEntity findAuthorEntity() {
        return AuthorEntity.builder()
                .id(23L)
                .code(UUID.randomUUID().toString().toUpperCase())
                .name("teste find")
                .description("best author terror find")
                .build();
    }

    public static List<AuthorEntity> createAuthorEntityList() {
        List<AuthorEntity> authors = new ArrayList<>();
        authors.add(AuthorEntity.builder()
                .id(1L)
                .code(UUID.randomUUID().toString())
                .name("author 1")
                .description("description 1")
                .build());

        authors.add(AuthorEntity.builder()
                .id(2L)
                .code(UUID.randomUUID().toString())
                .name("author 2")
                .description("description 2")
                .build());

        return authors;
    }

    public static List<AuthorResponse> createAuthorResponseList() {
        return createAuthorEntityList().stream().map(AuthorResponse::entityToResponse).collect(Collectors.toList());
    }

    public static Page<AuthorEntity> createPageAuthorEntity() {
        List<AuthorEntity> authors = createAuthorEntityList();
        return new PageImpl<>(authors, Pageable.ofSize(1), authors.size());
    }

    public static AuthorResponsePageable createAuthorResponsePageable() {
        return AuthorResponsePageable.builder()
                .page(1)
                .count(2L)
                .authors(createPageAuthorEntity().get().map(AuthorResponse::entityToResponse).collect(Collectors.toList()))
                .build();
    }

    public static AuthorEntity createAuthorEntity(String code) {
        return AuthorEntity.builder()
                .id(3L)
                .code(code)
                .name("author 3")
                .description("description 3")
                .build();
    }

    public static AuthorResponse createAuthorResponse() {
        return AuthorResponse.builder()
                .code(UUID.randomUUID().toString())
                .name("author 3")
                .description("description 3")
                .build();
    }

    public static ClientRequest createClientRequest() {
        return ClientRequest.builder()
                .name("teste")
                .address("rua 1")
                .birthDate(LocalDate.now())
                .cpf("11111111111")
                .build();
    }

    public static ClientResponse createClientSuccessResponse() {
        return ClientResponse.builder()
                .code(UUID.randomUUID().toString())
                .name("teste 2")
                .address("rua 2")
                .birthDate(LocalDate.now())
                .cpf("22222222222")
                .build();
    }


    public static ClientRequest createClientSuccessRequest() {
        return ClientRequest.builder()
                .name("teste")
                .address("rua 1")
                .birthDate(LocalDate.now())
                .cpf("11111111111")
                .build();
    }

    public static ClientEntity findClientEntity() {
        return ClientEntity.builder()
                .id(1L)
                .code(UUID.randomUUID().toString().toUpperCase())
                .name("teste")
                .address("rua 1")
                .birthDate(LocalDate.now())
                .cpf("11111111111")
                .build();
    }

    public static List<ClientEntity> createClientEntityList() {
        List<ClientEntity> clients = new ArrayList<>();
        clients.add(ClientEntity.builder()
                .id(2L)
                .code(UUID.randomUUID().toString())
                .name("teste 2")
                .address("rua 2")
                .birthDate(LocalDate.now())
                .cpf("2222222222")
                .build());

        clients.add(ClientEntity.builder()
                .id(3L)
                .code(UUID.randomUUID().toString())
                .name("teste 3")
                .address("rua 3")
                .birthDate(LocalDate.now())
                .cpf("33333333333")
                .build());

        return clients;
    }

    public static List<ClientResponse> createClientResponseList() {
        return createClientEntityList().stream().map(ClientResponse::entityToResponse).collect(Collectors.toList());
    }

    public static Page<ClientEntity> createPageClientEntity() {
        List<ClientEntity> clients = createClientEntityList();
        return new PageImpl<>(clients, Pageable.ofSize(1), clients.size());
    }

    public static ClientResponsePageable createClientResponsePageable() {
        return ClientResponsePageable.builder()
                .page(1)
                .count(2L)
                .clients(createPageClientEntity().get().map(ClientResponse::entityToResponse).collect(Collectors.toList()))
                .build();
    }

    public static ClientEntity createClientEntity() {
        return ClientEntity.builder()
                .id(4L)
                .code(UUID.randomUUID().toString())
                .name("teste 4")
                .address("rua 4")
                .birthDate(LocalDate.now())
                .cpf("44444444444")
                .build();
    }

    public static MockMultipartFile createMockedFile() {
        return new MockMultipartFile("fileName", "filename.png", "image/png", "content".getBytes());
    }

    public static BookRequest createBookRequest() {
        return BookRequest.builder()
                .title("Book Name")
                .authorCode("2E126BBC-FC6E-4382-B816-C3EC730FDBE1")
                .publisher("editora abril")
                .yearPublication("1988")
                .saleValue("100")
                .file(createMockedFile())
                .build();
    }

    public static BookRequest createBookSuccessRequest() {
        return BookRequest.builder()
                .title("Book Name")
                .authorCode("2E126BBC-FC6E-4382-B816-C3EC730FDBE1")
                .publisher("editora abril")
                .yearPublication("1988")
                .saleValue("100")
                .file(createMockedFile())
                .build();
    }

    public static BookResponse createBookSuccessResponse() {
        return BookResponse.builder()
                .title("Book Name")
                .author(createAuthorSuccessResponse())
                .publisher("editora abril")
                .yearPublication(2232)
                .saleValue(100)
                .code(UUID.randomUUID().toString())
                .costValue(0)
                .image("/images.jpg")
                .quantity(4)
                .build();
    }

    public static AuthorResponse createAuthorSuccessResponse() {
        return AuthorResponse.builder()
                .code(UUID.randomUUID().toString())
                .name("author 1")
                .description("mitologia nordica")
                .build();
    }

    public static BookEntity createBookEntity() {
        return BookEntity.builder()
                .id(5L)
                .code("B3158BDC-CC7E-4286-C816-D3EE730GDB51")
                .title("Book name 2")
                .author(createAuthorEntity(UUID.randomUUID().toString()))
                .publisher("editora")
                .yearPublication(2022)
                .image("img")
                .costValue(1050)
                .saleValue(3050)
                .quantity(40)
                .build();
    }

    public static BookEntity createBookEntity(String code) {
        return BookEntity.builder()
                .id(5L)
                .code(code)
                .title("Book name 2")
                .author(createAuthorEntity(UUID.randomUUID().toString()))
                .publisher("editora")
                .yearPublication(2022)
                .image("img")
                .costValue(1050)
                .saleValue(3050)
                .quantity(40)
                .build();
    }

    public static List<BookEntity> createBookEntityList() {
        List<BookEntity> books = new ArrayList<>();
        books.add(BookEntity.builder()
                .id(1L)
                .code("A3126BBC-FC6E-4382-B816-C3EC730FDBE1")
                .title("Book name 1")
                .author(createAuthorEntity(UUID.randomUUID().toString()))
                .publisher("editora")
                .yearPublication(2022)
                .image("img")
                .costValue(1050)
                .saleValue(3050)
                .quantity(90)
                .build());

        books.add(BookEntity.builder()
                .id(2L)
                .code("B3158BDC-CC7E-4286-C816-D3EE730GDB51")
                .title("Book name 2")
                .author(createAuthorEntity(UUID.randomUUID().toString()))
                .publisher("editora 2")
                .yearPublication(2012)
                .image("img333")
                .costValue(5050)
                .saleValue(22050)
                .quantity(4)
                .build());


        return books;
    }

    public static List<BookResponse> createBookResponseList() {
        return createBookEntityList().stream().map(BookResponse::entityToResponse).collect(Collectors.toList());
    }

    public static Page<BookEntity> createPageBookEntity() {
        List<BookEntity> books = createBookEntityList();
        return new PageImpl<>(books, Pageable.ofSize(1), books.size());
    }

    public static BookResponsePageable createBookResponsePageable() {
        return BookResponsePageable.builder()
                .page(1)
                .count(2L)
                .books(createPageBookEntity().get().map(BookResponse::entityToResponse).collect(Collectors.toList()))
                .build();
    }

    public static BookEntryRequest createBookEntryRequest() {
        return BookEntryRequest.builder()
                .costValue("5050")
                .quantity("4")
                .build();
    }

    public static BookEntryRequest createBookEntrySuccessRequest() {
        return BookEntryRequest.builder()
                .costValue("6050")
                .quantity("2")
                .build();
    }

    public static BookEntryEntity createBookEntryEntity(BookEntity bookEntity) {
        return BookEntryEntity.builder()
                .book(bookEntity)
                .costValue(142004)
                .quantity(542)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static SaleRequest createSaleRequest() {
        return SaleRequest.builder()
                .clientCode(UUID.randomUUID().toString())
                .details(createSaleDetailRequestList())
                .build();
    }

    public static SaleRequest createSaleSuccessRequest() {
        return SaleRequest.builder()
                .clientCode(UUID.randomUUID().toString())
                .details(createSaleDetailRequestList())
                .build();
    }

    public static SaleRequest createSaleWithDatailnullRequest() {
        return SaleRequest.builder()
                .clientCode(UUID.randomUUID().toString())
                .details(null)
                .build();
    }

    public static SaleRequest createSaleWithDatailEmptyRequest() {
        return SaleRequest.builder()
                .clientCode(UUID.randomUUID().toString())
                .details(new ArrayList<>())
                .build();
    }

    public static List<SaleDetailRequest> createSaleDetailRequestList() {
        List<SaleDetailRequest> saleDetailsRequest = new ArrayList<>();
        saleDetailsRequest.add(SaleDetailRequest.builder()
                .bookCode(UUID.randomUUID().toString())
                .quantity("5")
                .build());

        saleDetailsRequest.add(SaleDetailRequest.builder()
                .bookCode(UUID.randomUUID().toString())
                .quantity("2")
                .build());

        return saleDetailsRequest;
    }

    public static SaleRequest createSaleDuplicateDetailBookRequest() {
        return SaleRequest.builder()
                .clientCode(UUID.randomUUID().toString())
                .details(createSaleDetailRequestDuplicateBookList())
                .build();
    }

    public static List<SaleDetailRequest> createSaleDetailRequestDuplicateBookList() {
        List<SaleDetailRequest> saleDetailsRequest = new ArrayList<>();
        var code = UUID.randomUUID().toString();
        saleDetailsRequest.add(SaleDetailRequest.builder()
                .bookCode(code)
                .quantity("5")
                .build());

        saleDetailsRequest.add(SaleDetailRequest.builder()
                .bookCode(code)
                .quantity("2")
                .build());

        return saleDetailsRequest;
    }

    public static SaleResponse createSaleSuccessResponse() {
        return SaleResponse.builder()
                .code(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .totalSaleValue(2340)
                .client(createClientSuccessResponse())
                .details(createSaleDatailSuccessResponse())
                .build();
    }

    public static List<SaleDetailResponse> createSaleDatailSuccessResponse() {
        List<SaleDetailResponse> saleDetailsResponse = new ArrayList<>();
        var code = UUID.randomUUID().toString();
        saleDetailsResponse.add(SaleDetailResponse.builder()
                .code(UUID.randomUUID().toString())
                .book(createBookSuccessResponse())
                .quantity(1)
                .saleValue(1040)
                .build());

        saleDetailsResponse.add(SaleDetailResponse.builder()
                .code(UUID.randomUUID().toString())
                .book(createBookSuccessResponse())
                .quantity(1)
                .saleValue(1300)
                .build());

        return saleDetailsResponse;
    }

}
