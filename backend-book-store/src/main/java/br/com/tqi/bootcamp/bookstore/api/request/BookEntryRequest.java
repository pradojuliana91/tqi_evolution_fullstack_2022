package br.com.tqi.bootcamp.bookstore.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookEntryRequest {

    @NotNull(message = "must have value")
    @Min(value = 1, message = "must be greater than or equal {value} cents")
    @Max(value = 10000000, message = "must be less than or equal {value} cents")
    @Pattern(regexp = "^\\d+$", message = "must be a integer number")
    private String quantity;

    @NotNull(message = "must have value")
    @Min(value = 100, message = "must be greater than or equal {value} cents")
    @Max(value = 10000000, message = "must be less than or equal {value} cents")
    @Pattern(regexp = "^\\d+$", message = "must be a integer number")
    private String costValue;
}
