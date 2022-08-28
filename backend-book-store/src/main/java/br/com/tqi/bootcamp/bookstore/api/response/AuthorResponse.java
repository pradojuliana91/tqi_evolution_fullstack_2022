package br.com.tqi.bootcamp.bookstore.api.response;

import br.com.tqi.bootcamp.bookstore.model.AuthorEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.StreamSupport;

@Builder
@Data
public class AuthorResponse {

    private String code;
    private String name;
    private String description;

    public static AuthorResponse entityToResponse(AuthorEntity entity) {
        return AuthorResponse.builder()
                .code(entity.getCode())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    public static List<AuthorResponse> entityToResponse(Iterable<AuthorEntity> entities) {
        return StreamSupport.stream(entities.spliterator(), false).map(AuthorResponse::entityToResponse).toList();
    }
}
