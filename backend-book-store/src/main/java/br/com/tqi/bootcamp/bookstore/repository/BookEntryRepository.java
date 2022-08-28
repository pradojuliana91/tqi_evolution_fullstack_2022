package br.com.tqi.bootcamp.bookstore.repository;

import br.com.tqi.bootcamp.bookstore.model.BookEntryEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookEntryRepository extends PagingAndSortingRepository<BookEntryEntity, Long> {
}
