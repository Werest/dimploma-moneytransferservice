package ru.werest.dimplomamoneytransferservice.request.object;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import static ru.werest.dimplomamoneytransferservice.global.Messages.*;

@Data
@AllArgsConstructor
public class Amount {
    @NotBlank(message = SUM_NOT_INPUT)
    @Min(value = 1, message = SUM_NOT_POSITIVE)
    private Long value;

    @NotBlank(message = CURRENCY_NOT_INPUT)
    private String currency;
}
