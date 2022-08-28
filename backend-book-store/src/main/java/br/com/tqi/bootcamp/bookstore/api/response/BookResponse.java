package br.com.tqi.bootcamp.bookstore.api.response;

import br.com.tqi.bootcamp.bookstore.model.BookEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.StreamSupport;

@Data
@Builder
public class BookResponse {

    private String code;
    private String title;
    private AuthorResponse author;
    private String publisher;
    private String image;
    private Integer yearPublication;
    private Integer costValue;
    private Integer saleValue;
    private Integer quantity;

    public static BookResponse entityToResponse(BookEntity entity) {
        return BookResponse.builder()
                .code(entity.getCode())
                .title(entity.getTitle())
                .author(AuthorResponse.entityToResponse(entity.getAuthor()))
                .publisher(entity.getPublisher())
                .image(entity.getImage())
                .yearPublication(entity.getYearPublication())
                .costValue(entity.getCostValue())
                .saleValue(entity.getSaleValue())
                .quantity(entity.getQuantity())
                .build();
    }

    public static List<BookResponse> entityToResponse(Iterable<BookEntity> entities) {
        return StreamSupport.stream(entities.spliterator(), false).map(BookResponse::entityToResponse).toList();
    }
}
