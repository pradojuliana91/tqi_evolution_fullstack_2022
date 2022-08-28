package br.com.tqi.bootcamp.bookstore.services;

import br.com.tqi.bootcamp.bookstore.Utils;
import br.com.tqi.bootcamp.bookstore.api.request.ClientRequest;
import br.com.tqi.bootcamp.bookstore.api.response.ClientResponse;
import br.com.tqi.bootcamp.bookstore.api.response.ClientResponsePageable;
import br.com.tqi.bootcamp.bookstore.exception.NotFoundException;
import br.com.tqi.bootcamp.bookstore.model.ClientEntity;
import br.com.tqi.bootcamp.bookstore.repository.ClientRepository;
import br.com.tqi.bootcamp.bookstore.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Test
    void shouldCreateClient() {
        ClientRequest request = Utils.createClientRequest();

        when(clientRepository.save(any())).thenReturn(request.toEntity());

        ClientResponse response = clientService.createClient(request);

        verify(clientRepository, times(1)).save(any());

        assertEquals(request.getName(), response.getName());
        assertEquals(request.getAddress(), response.getAddress());
        assertEquals(request.getCpf(), response.getCpf());
        assertEquals(request.getBirthDate(), response.getBirthDate());
        assertNotNull(response.getCode());
    }

    @Test
    void shouldFindClientExists() {
        ClientEntity clientEntity = Utils.findClientEntity();

        when(clientRepository.findByCode(clientEntity.getCode())).thenReturn(Optional.of(clientEntity));

        ClientResponse response = clientService.findClient(clientEntity.getCode());

        assertNotNull(clientEntity.getId());
        assertEquals(response.getCode(), clientEntity.getCode());
        assertEquals(response.getName(), clientEntity.getName());
        assertEquals(response.getAddress(), clientEntity.getAddress());
        assertEquals(response.getCpf(), clientEntity.getCpf());
        assertEquals(response.getBirthDate(), clientEntity.getBirthDate());
    }

    @Test
    void shouldThrowFindClientNotExists() {
        String code = UUID.randomUUID().toString();

        when(clientRepository.findByCode(code)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> clientService.findClient(code));
    }

    @Test
    void shouldReturnClientListPageResponse() {
        Page<ClientEntity> page = Utils.createPageClientEntity();
        Pageable paging = PageRequest.of(1, 1);

        when(clientRepository.findAll(paging)).thenReturn(page);

        ClientResponsePageable response = clientService.getAllClientsByPage(paging);

        verify(clientRepository, times(1)).findAll(paging);
        assertEquals(2, response.getCount());
        assertEquals(0, response.getPage());
    }

    @Test
    void shouldReturnClientListResponse() {
        List<ClientEntity> clients = Utils.createClientEntityList();

        when(clientRepository.findAll()).thenReturn(clients);

        List<ClientResponse> response = clientService.getAllClients();

        assertEquals(response.size(), clients.size());
    }

    @Test
    void shouldThrowReturnClientListResponseNotExists() {
        List<ClientEntity> clients = new ArrayList<>();

        when(clientRepository.findAll()).thenReturn(clients);

        assertThrows(NotFoundException.class, () -> clientService.getAllClients());
    }

    @Test
    void shouldDeleteClient() {
        ClientEntity clientEntity = Utils.createClientEntity();

        when(clientRepository.findByCode(clientEntity.getCode())).thenReturn(Optional.of(clientEntity));

        clientService.deleteClient(clientEntity.getCode());

        verify(clientRepository, times(1)).delete(clientEntity);
    }

    @Test
    void shouldThrowDeleteClientNotExists() {
        String code = UUID.randomUUID().toString();

        when(clientRepository.findByCode(code)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> clientService.deleteClient(code));

        verify(clientRepository, never()).delete(any());
    }

    @Test
    void shouldReturnClientResponseWhenClientIsUpdated() {
        ClientRequest request = Utils.createClientRequest();
        ClientEntity clientEntity = Utils.createClientEntity();

        when(clientRepository.findByCode(clientEntity.getCode())).thenReturn(Optional.of(clientEntity));

        when(clientRepository.save(any())).thenReturn(clientEntity);

        ClientResponse response = clientService.updateClient(clientEntity.getCode(), request);

        verify(clientRepository, times(1)).findByCode(clientEntity.getCode());
        verify(clientRepository, times(1)).save(any());

        assertEquals(clientEntity.getCode(), response.getCode());
        assertEquals(clientEntity.getName(), response.getName());
        assertEquals(clientEntity.getAddress(), response.getAddress());
        assertEquals(clientEntity.getCpf(), response.getCpf());
        assertEquals(clientEntity.getBirthDate(), response.getBirthDate());
    }

    @Test
    void shouldThrowReturnClientResponseWhenClientIsUpdatedNotExists() {
        ClientRequest request = Utils.createClientRequest();
        String code = UUID.randomUUID().toString();

        when(clientRepository.findByCode(code)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> clientService.updateClient(code, request));

        verify(clientRepository, times(1)).findByCode(code);
        verify(clientRepository, never()).save(any());
    }
}
