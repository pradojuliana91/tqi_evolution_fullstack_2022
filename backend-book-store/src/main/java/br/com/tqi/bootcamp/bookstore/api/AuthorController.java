package br.com.tqi.bootcamp.bookstore.api;

import br.com.tqi.bootcamp.bookstore.api.request.AuthorRequest;
import br.com.tqi.bootcamp.bookstore.api.response.AuthorResponse;
import br.com.tqi.bootcamp.bookstore.api.response.AuthorResponsePageable;
import br.com.tqi.bootcamp.bookstore.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
@Slf4j
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(@Valid @RequestBody AuthorRequest request) {
        log.info("Create new author | request={}", request);
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.createAuthor(request));
    }

    @GetMapping("/{code}")
    public ResponseEntity<AuthorResponse> retrieveAuthor(@PathVariable String code) {
        log.info("Retrieve a author | code={}", code);
        return ResponseEntity.ok(authorService.findAuthor(code));
    }

    @GetMapping
    public ResponseEntity<AuthorResponsePageable> retrieveAllAuthorsByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable paging = PageRequest.of(page, size);
        log.info("Retrieve author list | page={} size={}", page, size);
        return ResponseEntity.ok(authorService.getAllAuthorsByPage(paging));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<AuthorResponse>> retrieveAllAuthors() {
        log.info("Retrieve author list");
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @PutMapping(value = "/{code}")
    public ResponseEntity<AuthorResponse> updateAuthor(@PathVariable String code, @Valid @RequestBody AuthorRequest request) {
        log.info("Update author | code={} | request={}", code, request);
        return ResponseEntity.ok(authorService.updateAuthor(code, request));
    }

    @DeleteMapping(value = "/{code}")
    public ResponseEntity deleteAuthor(@PathVariable String code) {
        log.info("Delete author | code={}", code);
        authorService.deleteAuthor(code);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
