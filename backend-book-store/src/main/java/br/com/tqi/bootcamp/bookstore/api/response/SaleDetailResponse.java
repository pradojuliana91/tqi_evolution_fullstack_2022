package br.com.tqi.bootcamp.bookstore.api.response;

import br.com.tqi.bootcamp.bookstore.model.SaleDetailEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaleDetailResponse {

    private String code;
    private BookResponse book;
    private Integer quantity;
    private Integer saleValue;
    private Integer totalSaleValue;

    public static SaleDetailResponse entityToResponse(SaleDetailEntity entity) {
        return SaleDetailResponse.builder()
                .code(entity.getCode())
                .book(BookResponse.entityToResponse(entity.getBook()))
                .totalSaleValue(entity.getTotalSaleValue())
                .saleValue(entity.getSaleValue())
                .quantity(entity.getQuantity())
                .build();
    }
}