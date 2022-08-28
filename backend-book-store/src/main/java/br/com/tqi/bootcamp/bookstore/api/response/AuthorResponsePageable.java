package br.com.tqi.bootcamp.bookstore.api.response;

import br.com.tqi.bootcamp.bookstore.model.AuthorEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class AuthorResponsePageable {

    private Integer page;
    private Long count;
    private List<AuthorResponse> authors;

    public static AuthorResponsePageable toResponse(Page<AuthorEntity> page) {
        return AuthorResponsePageable.builder()
                .authors(page.get().map(AuthorResponse::entityToResponse).collect(Collectors.toList()))
                .page(page.getPageable().getPageNumber())
                .count(page.getTotalElements())
                .build();
    }
}
