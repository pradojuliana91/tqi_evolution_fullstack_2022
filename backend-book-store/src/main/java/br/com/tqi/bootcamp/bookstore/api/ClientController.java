package br.com.tqi.bootcamp.bookstore.api;

import br.com.tqi.bootcamp.bookstore.api.request.ClientRequest;
import br.com.tqi.bootcamp.bookstore.api.response.ClientResponse;
import br.com.tqi.bootcamp.bookstore.api.response.ClientResponsePageable;
import br.com.tqi.bootcamp.bookstore.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientResponse> createClient(@Valid @RequestBody ClientRequest request) {
        log.info("Create new client | request={}", request);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.createClient(request));
    }

    @GetMapping("/{code}")
    public ResponseEntity<ClientResponse> retrieveClient(@PathVariable String code) {
        log.info("Retrieve a client | code={}", code);
        return ResponseEntity.ok(clientService.findClient(code));
    }

    @GetMapping
    public ResponseEntity<ClientResponsePageable> retrieveAllClients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable paging = PageRequest.of(page, size);
        log.info("Retrieve client list | page={} size={}", page, size);
        return ResponseEntity.ok(clientService.getAllClientsByPage(paging));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<ClientResponse>> retrieveAllClients() {
        log.info("Retrieve client list");
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @PutMapping(value = "/{code}")
    public ResponseEntity<ClientResponse> updateClient(@PathVariable String code, @Valid @RequestBody ClientRequest request) {
        log.info("Update client | code={} | request={}", code, request);
        return ResponseEntity.ok(clientService.updateClient(code, request));
    }

    @DeleteMapping(value = "/{code}")
    public ResponseEntity deleteClient(@PathVariable String code) {
        log.info("Delete client | code={}", code);
        clientService.deleteClient(code);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
