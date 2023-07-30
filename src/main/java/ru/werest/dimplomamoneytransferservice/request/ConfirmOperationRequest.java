package ru.werest.dimplomamoneytransferservice.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import static ru.werest.dimplomamoneytransferservice.global.Messages.*;

@Data
public class ConfirmOperationRequest {
    public static final String OPERATION_ID_REGEXP = "^[0-9]*$";

    @NotBlank(message = OPERATION_ID_IS_NOT_INPUT)
    @Pattern(regexp = OPERATION_ID_REGEXP, message = OPERATION_ID_IS_NOT_VALID)
    private String operationId;


    @NotBlank(message = CONFIRM_CODE_IS_NOT_INPUT)
    private String code;

}
