package br.com.tqi.bootcamp.bookstore.api.response;

import br.com.tqi.bootcamp.bookstore.model.SaleDetailEntity;
import br.com.tqi.bootcamp.bookstore.model.SaleEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class SaleResponse {

    private String code;
    private ClientResponse client;
    private Integer totalSaleValue;
    private LocalDateTime createdAt;
    private List<SaleDetailResponse> details;

    public static SaleResponse entityToResponse(SaleEntity entity, List<SaleDetailEntity> saleDetailsEntity) {
        return SaleResponse.builder()
                .code(entity.getCode())
                .client(ClientResponse.entityToResponse(entity.getClient()))
                .totalSaleValue(entity.getTotalSaleValue())
                .createdAt(entity.getCreatedAt())
                .details(CollectionUtils.isEmpty(saleDetailsEntity) ? null :
                        saleDetailsEntity.stream().map(SaleDetailResponse::entityToResponse).toList())
                .build();
    }
}
