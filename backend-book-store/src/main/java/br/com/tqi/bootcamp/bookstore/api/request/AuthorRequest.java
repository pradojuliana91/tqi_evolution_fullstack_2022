package br.com.tqi.bootcamp.bookstore.api.request;

import br.com.tqi.bootcamp.bookstore.model.AuthorEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorRequest {

    @Size(min = 5, max = 50, message = "size name must be between {min} and {max} characters")
    @NotBlank(message = "must have value")
    private String name;
    @Size(min = 5, max = 100, message = "size name must be between {min} and {max} characters")
    @NotBlank(message = "must have value")
    private String description;

    public AuthorEntity toEntity() {
        return AuthorEntity.builder()
                .code(UUID.randomUUID().toString().toUpperCase())
                .name(this.getName())
                .description(this.getDescription())
                .build();

    }
}
