package br.com.tqi.bootcamp.bookstore.service;

import br.com.tqi.bootcamp.bookstore.api.request.ClientRequest;
import br.com.tqi.bootcamp.bookstore.api.response.ClientResponse;
import br.com.tqi.bootcamp.bookstore.api.response.ClientResponsePageable;
import br.com.tqi.bootcamp.bookstore.exception.NotFoundException;
import br.com.tqi.bootcamp.bookstore.model.ClientEntity;
import br.com.tqi.bootcamp.bookstore.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;


    public ClientResponse createClient(ClientRequest request) {
        return ClientResponse.entityToResponse(clientRepository.save(request.toEntity()));
    }

    public ClientResponse findClient(final String code) {
        return ClientResponse.entityToResponse(findClientByCode(code));
    }

    public ClientResponsePageable getAllClientsByPage(final Pageable pageable) {
        return ClientResponsePageable.toResponse(clientRepository.findAll(pageable));
    }

    public List<ClientResponse> getAllClients() {
        Iterable<ClientEntity> clients = clientRepository.findAll();
        if (IterableUtils.isEmpty(clients)) {
            throw new NotFoundException("client(s) not found");
        }
        return ClientResponse.entityToResponse(clients);
    }

    public void deleteClient(final String code) {
        ClientEntity entity = findClientByCode(code);
        clientRepository.delete(entity);
    }

    public ClientResponse updateClient(final String code, final ClientRequest request) {
        ClientEntity clientEntity = findClientByCode(code);
        replaceClientEntity(clientEntity, request);

        clientRepository.save(clientEntity);

        return ClientResponse.entityToResponse(clientEntity);
    }

    public ClientEntity findClientByCode(final String code) {
        return clientRepository.findByCode(code).orElseThrow(() -> new NotFoundException("client not found"));
    }

    private void replaceClientEntity(ClientEntity entity, final ClientRequest request) {
        entity.setName(request.getName());
        entity.setCpf(request.getCpf());
        entity.setAddress(request.getAddress());
        entity.setBirthDate(request.getBirthDate());
    }
}
