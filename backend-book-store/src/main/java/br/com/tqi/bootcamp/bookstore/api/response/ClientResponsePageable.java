package br.com.tqi.bootcamp.bookstore.api.response;

import br.com.tqi.bootcamp.bookstore.model.ClientEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ClientResponsePageable {

    private Integer page;
    private Long count;
    private List<ClientResponse> clients;

    public static ClientResponsePageable toResponse(Page<ClientEntity> page) {
        return ClientResponsePageable.builder()
                .clients(page.get().map(ClientResponse::entityToResponse).collect(Collectors.toList()))
                .page(page.getPageable().getPageNumber())
                .count(page.getTotalElements())
                .build();
    }
}
