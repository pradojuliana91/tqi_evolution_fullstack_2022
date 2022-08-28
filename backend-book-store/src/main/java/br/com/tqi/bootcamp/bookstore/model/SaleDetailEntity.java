package br.com.tqi.bootcamp.bookstore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "sale_detail")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SaleDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    @ManyToOne(optional = false)
    private SaleEntity sale;
    @ManyToOne(optional = false)
    private BookEntity book;
    private Integer quantity;
    private Integer saleValue;
    private Integer totalSaleValue;
}
