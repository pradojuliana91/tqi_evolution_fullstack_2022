package br.com.tqi.bootcamp.bookstore.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaleRequest {

    @NotBlank(message = "must have value")
    private String clientCode;

    @NotEmpty(message = "must have value")
    @NotNull(message = "must have value")
    @Valid
    private List<SaleDetailRequest> details;
}
