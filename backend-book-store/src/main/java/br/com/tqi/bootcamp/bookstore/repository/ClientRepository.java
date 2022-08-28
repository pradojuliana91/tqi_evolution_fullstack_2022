package br.com.tqi.bootcamp.bookstore.repository;

import br.com.tqi.bootcamp.bookstore.model.ClientEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends PagingAndSortingRepository<ClientEntity, Long> {

    Optional<ClientEntity> findByCode(String code);
}
