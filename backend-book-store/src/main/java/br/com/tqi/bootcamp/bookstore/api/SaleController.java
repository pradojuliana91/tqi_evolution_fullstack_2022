package br.com.tqi.bootcamp.bookstore.api;

import br.com.tqi.bootcamp.bookstore.api.request.SaleRequest;
import br.com.tqi.bootcamp.bookstore.api.response.SaleResponse;
import br.com.tqi.bootcamp.bookstore.service.SaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
@Slf4j
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    public ResponseEntity<SaleResponse> sale(@Valid @RequestBody SaleRequest request) {
        log.info("Create new sale | request={}", request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saleService.sale(request));
    }
}
