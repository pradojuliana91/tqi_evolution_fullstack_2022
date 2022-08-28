package br.com.tqi.bootcamp.bookstore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "sale")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SaleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    @ManyToOne(optional = false)
    private ClientEntity client;
    private Integer totalSaleValue;
    private LocalDateTime createdAt;
}
