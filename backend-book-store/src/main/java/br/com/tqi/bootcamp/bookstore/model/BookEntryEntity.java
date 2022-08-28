package br.com.tqi.bootcamp.bookstore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "book_entry")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookEntryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private BookEntity book;
    private Integer costValue;
    private Integer quantity;
    private LocalDateTime createdAt;
}
