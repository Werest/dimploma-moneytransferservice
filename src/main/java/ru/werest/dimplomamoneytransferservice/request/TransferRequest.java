package ru.werest.dimplomamoneytransferservice.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.werest.dimplomamoneytransferservice.request.object.Amount;

import static ru.werest.dimplomamoneytransferservice.global.Messages.*;

@Data
public class TransferRequest {
    public static final String REGEXP_TILL_DATE = "^(0[1-9]|1[0-2])\\/?([0-9]{4}|[0-9]{2})$";

    @NotBlank(message = NUMBER_FROM_CARD_NOT_INPUT)
    @Size(min = 16, max = 16, message = NUMBER_FROM_CARD_NOT_VALID)
    @Pattern(regexp = "\\d+")
    private String cardFromNumber;

    @NotBlank(message = NUMBER_TO_CARD_NOT_INPUT)
    @Size(min = 16, max = 16, message = NUMBER_TO_CARD_NOT_VALID)
    @Pattern(regexp = "\\d+")
    private String cardToNumber;

    @NotBlank(message = VALID_TILL_NOT_INPUT)
    @Size(min = 5, max = 5, message = VALID_TILL_NOT_INPUT)
    @Pattern(regexp = REGEXP_TILL_DATE)
    private String cardFromValidTill;

    @NotBlank(message = CVV_CVC2_NOT_INPUT)
    @Size(min = 3, max = 3, message = CVV_CVC2_NOT_INPUT)
    private String cardFromCVV;


    private Amount amount;
}
