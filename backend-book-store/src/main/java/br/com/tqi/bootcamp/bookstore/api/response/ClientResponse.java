package br.com.tqi.bootcamp.bookstore.api.response;

import br.com.tqi.bootcamp.bookstore.model.ClientEntity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.StreamSupport;

@Data
@Builder
public class ClientResponse {

    private String code;
    private String name;
    private String cpf;
    private String address;
    private LocalDate birthDate;

    public static ClientResponse entityToResponse(ClientEntity entity) {
        return ClientResponse.builder()
                .code(entity.getCode())
                .name(entity.getName())
                .cpf(entity.getCpf())
                .address(entity.getAddress())
                .birthDate(entity.getBirthDate())
                .build();
    }

    public static List<ClientResponse> entityToResponse(Iterable<ClientEntity> entities) {
        return StreamSupport.stream(entities.spliterator(), false).map(ClientResponse::entityToResponse).toList();
    }
}
