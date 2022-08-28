package br.com.tqi.bootcamp.bookstore.api.request;

import br.com.tqi.bootcamp.bookstore.converter.LocalDateDeserializer;
import br.com.tqi.bootcamp.bookstore.converter.LocalDateSerializer;
import br.com.tqi.bootcamp.bookstore.model.ClientEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequest {

    @Size(min = 5, max = 50, message = "size name must be between {min} and {max} characters")
    @NotBlank(message = "must have value")
    private String name;

    @Size(min = 11, max = 11, message = "size name must be between {min} and {max} characters")
    @Pattern(regexp = "^\\d+$", message = "must be a integer number")
    @NotBlank(message = "must have value")
    private String cpf;

    @Size(min = 5, max = 100, message = "size name must be between {min} and {max} characters")
    @NotBlank(message = "must have value")
    private String address;

    @NotNull(message = "must have value")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthDate;

    public ClientEntity toEntity() {
        return ClientEntity.builder()
                .code(UUID.randomUUID().toString().toUpperCase())
                .name(this.getName())
                .cpf(this.getCpf())
                .address(this.getAddress())
                .birthDate(this.getBirthDate())
                .build();
    }

}
