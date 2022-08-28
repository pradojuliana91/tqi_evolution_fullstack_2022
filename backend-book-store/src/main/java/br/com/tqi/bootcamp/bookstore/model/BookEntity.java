package br.com.tqi.bootcamp.bookstore.model;

import br.com.tqi.bootcamp.bookstore.api.request.BookRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "book")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String title;
    @ManyToOne(optional = false)
    private AuthorEntity author;
    private String publisher;
    @Column(name = "image_url")
    private String image;
    private Integer yearPublication;
    private Integer costValue;
    private Integer saleValue;
    private Integer quantity;

    public BookEntity(final BookRequest request, final AuthorEntity author, final String urlImage) {
        this.code = UUID.randomUUID().toString().toUpperCase();
        this.title = request.getTitle();
        this.author = author;
        this.publisher = request.getPublisher();
        this.image = urlImage;
        this.yearPublication = Integer.valueOf(request.getYearPublication());
        this.costValue = 0;
        this.saleValue = Integer.valueOf(request.getSaleValue());
        this.quantity = 0;
    }
}
