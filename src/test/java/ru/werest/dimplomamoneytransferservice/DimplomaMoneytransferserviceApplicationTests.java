package ru.werest.dimplomamoneytransferservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.werest.dimplomamoneytransferservice.request.ConfirmOperationRequest;
import ru.werest.dimplomamoneytransferservice.request.TransferRequest;
import ru.werest.dimplomamoneytransferservice.request.object.Amount;
import ru.werest.dimplomamoneytransferservice.response.TransferResponse;

import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class DimplomaMoneytransferserviceApplicationTests {

    String HOST = "http://localhost:";
    int PORT = 5500;

    @Autowired
    TestRestTemplate restTemplate;

    @Container
    GenericContainer<?> transfer_service = new GenericContainer<>("transfer_service")
            .withExposedPorts(5500);


    @Test
    void Positive() {
        Integer transferServicePort = transfer_service.getMappedPort(5500);

        TransferRequest request = new TransferRequest();
        request.setCardFromNumber("3333333333333333");
        request.setCardFromValidTill("11/25");
        request.setCardFromCVV("555");
        request.setCardToNumber("5555555555555555");
        request.setAmount(new Amount(450L, "RUR"));

        ResponseEntity<String> response = restTemplate.postForEntity(
                HOST + transferServicePort + "/transfer",
                request,
                String.class
        );
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        ConfirmOperationRequest confirmOperationRequest = new ConfirmOperationRequest();
        confirmOperationRequest.setOperationId("1");
        confirmOperationRequest.setCode("0000");

        response = restTemplate.postForEntity(
                HOST + transferServicePort + "/confirmOperation",
                confirmOperationRequest,
                String.class
        );
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void NegativeSecond() {
        Integer transferServicePort = transfer_service.getMappedPort(5500);

        TransferRequest request = new TransferRequest();
        //request.setCardFromNumber("3333333333333333");
        request.setCardFromValidTill("11/25");
        request.setCardFromCVV("555");
        request.setCardToNumber("5555555555555555");
        request.setAmount(new Amount(450L, "RUR"));

        ResponseEntity<TransferResponse> response = restTemplate.postForEntity(
                HOST + transferServicePort + "/transfer",
                request,
                TransferResponse.class
        );
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    void NegativeThird() {
        Integer transferServicePort = transfer_service.getMappedPort(5500);

        TransferRequest request = new TransferRequest();
        request.setCardFromNumber("3333333333333333");
        request.setCardFromValidTill("11/25");
        request.setCardFromCVV("555");
        request.setCardToNumber("5555555555555555");
        request.setAmount(new Amount(450L, "RUR"));

        ResponseEntity<TransferResponse> response = restTemplate.postForEntity(
                HOST + transferServicePort + "/transfer",
                request,
                TransferResponse.class
        );
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        ConfirmOperationRequest confirmOperationRequest = new ConfirmOperationRequest();
        confirmOperationRequest.setOperationId(Objects.requireNonNull(response.getBody()).getOperationId());
        confirmOperationRequest.setCode("000");

        ResponseEntity<String> responseConfirm = restTemplate.postForEntity(
                HOST + transferServicePort + "/confirmOperation",
                confirmOperationRequest,
                String.class
        );
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseConfirm.getStatusCode());
    }

}
