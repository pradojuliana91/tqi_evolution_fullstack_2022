package br.com.tqi.bootcamp.bookstore.service;


import br.com.tqi.bootcamp.bookstore.api.request.SaleDetailRequest;
import br.com.tqi.bootcamp.bookstore.api.request.SaleRequest;
import br.com.tqi.bootcamp.bookstore.api.response.SaleResponse;
import br.com.tqi.bootcamp.bookstore.exception.APIBusinessException;
import br.com.tqi.bootcamp.bookstore.model.BookEntity;
import br.com.tqi.bootcamp.bookstore.model.SaleDetailEntity;
import br.com.tqi.bootcamp.bookstore.model.SaleEntity;
import br.com.tqi.bootcamp.bookstore.repository.SaleDetailRepository;
import br.com.tqi.bootcamp.bookstore.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final SaleDetailRepository saleDetailRepository;
    private final BookService bookService;
    private final ClientService clientService;


    @Transactional(isolation = Isolation.READ_COMMITTED)
    public SaleResponse sale(SaleRequest request) {

        if (CollectionUtils.isEmpty(request.getDetails()))
            throw new APIBusinessException("Não existe livro(s) na venda!");

        if (request.getDetails().stream().map(SaleDetailRequest::getBookCode).distinct().toList().size() <
                request.getDetails().size()) {
            throw new APIBusinessException("Existe livro(s) duplicado(s) na venda!");
        }

        SaleEntity saleEntity = getSaleEntity(request);

        List<SaleDetailEntity> saleDetailsEntity = new ArrayList<>();

        for (SaleDetailRequest saleDetail : request.getDetails()) {
            BookEntity book = bookService.removeSotck(saleDetail.getBookCode(), Integer.parseInt(saleDetail.getQuantity()));
            saleDetailsEntity.add(getSaleDetailEntity(saleEntity, Integer.valueOf(saleDetail.getQuantity()), book));
        }

        saleEntity.setTotalSaleValue(saleDetailsEntity.stream().mapToInt(SaleDetailEntity::getTotalSaleValue).sum());

        saleRepository.save(saleEntity);
        saleDetailRepository.saveAll(saleDetailsEntity);

        return SaleResponse.entityToResponse(saleEntity, saleDetailsEntity);
    }

    private SaleEntity getSaleEntity(SaleRequest request) {
        SaleEntity saleEntity = new SaleEntity();
        saleEntity.setCreatedAt(LocalDateTime.now());
        saleEntity.setCode(UUID.randomUUID().toString().toUpperCase());
        saleEntity.setClient(clientService.findClientByCode(request.getClientCode()));
        return saleEntity;
    }

    private SaleDetailEntity getSaleDetailEntity(SaleEntity saleEntity, Integer quantity, BookEntity book) {

        SaleDetailEntity saleDetailEntity = new SaleDetailEntity();
        saleDetailEntity.setCode(UUID.randomUUID().toString().toUpperCase());
        saleDetailEntity.setSale(saleEntity);
        saleDetailEntity.setSaleValue(book.getSaleValue());
        saleDetailEntity.setBook(book);
        saleDetailEntity.setQuantity(quantity);
        saleDetailEntity.setTotalSaleValue(quantity * book.getSaleValue());

        return saleDetailEntity;
    }
}
