package br.com.tqi.bootcamp.bookstore.repository;

import br.com.tqi.bootcamp.bookstore.model.SaleEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends PagingAndSortingRepository<SaleEntity, Long> {
}
