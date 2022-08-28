package br.com.tqi.bootcamp.bookstore.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaleDetailRequest {

    @NotBlank(message = "must have value")
    private String bookCode;

    @NotNull(message = "must have value")
    @Min(value = 1, message = "must be greater than or equal {value} cents")
    @Max(value = 10000000, message = "must be less than or equal {value} cents")
    @Pattern(regexp = "^\\d+$", message = "must be a integer number")
    private String quantity;
}
