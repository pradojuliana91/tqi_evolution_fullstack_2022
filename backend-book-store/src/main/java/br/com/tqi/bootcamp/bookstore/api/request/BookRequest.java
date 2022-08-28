package br.com.tqi.bootcamp.bookstore.api.request;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
public class BookRequest {

    @Size(min = 5, max=100, message = "size name must be between {min} and {max} characters")
    @NotBlank(message = "must have value")
    private String title;

    @NotBlank(message = "must have value")
    private String authorCode;

    @Size(min = 5, max=100, message = "size name must be between {min} and {max} characters")
    @NotBlank(message = "must have value")
    private String publisher;

    @NotNull(message = "must have value")
    @Size(min = 4, max = 4, message = "size name must be between {min} and {max} characters")
    @Pattern(regexp = "^\\d+$", message = "must be a integer number")
    private String yearPublication;

    @NotNull(message = "must have value")
    @Min(value = 100, message = "must be greater than or equal {value} cents")
    @Max(value = 10000000, message = "must be less than or equal {value} cents")
    @Pattern(regexp = "^\\d+$", message = "must be a integer number")
    private String saleValue;

    @NotNull(message = "must have value")
    private MultipartFile file;
}
