package com.example.demo.logic;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.Transformer;
import com.example.demo.exceptions.DemoExceptionUnchecked;
import com.example.demo.repository.BalanceRepository;
import com.example.demo.service.BalancesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;

@Service
public class BalancesImpl implements BalancesService {
    Logger logger = LoggerFactory.getLogger(BalancesImpl.class);

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    BalanceRepository balanceRepository;

    @Override
    public ResponseEntity<ResponseDto> get(Long accountId) {
        String balanceUri = "https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/{accountId}/balance";
        HttpEntity<Void> requestEntity = new HttpEntity<>(addHeader());
        try {
            ResponseEntity<ResponseDto> response = restTemplate.exchange(
                    balanceUri, HttpMethod.GET, requestEntity, ResponseDto.class, accountId);
            String info = response.getBody() != null
                    ? "balance of " + accountId.toString() : "not found for  " + accountId;
            saveBalance(response);
            logger.info(info);
            return response;

        } catch (RuntimeException e) {
            ResponseDto response = new ResponseDto();
            response.setDemoError(e.getMessage());
            response.setErrorCode("customErrorCode");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void saveBalance(ResponseEntity<ResponseDto> response) {
        try {
            Optional.ofNullable(response.getBody()).ifPresent(
                    balance -> balanceRepository.save(Transformer.transformBalance(
                            balance.getPayload())));
        } catch (RuntimeException e) {
            throw new DemoExceptionUnchecked("custom error message", e);
        }
    }

    private HttpHeaders addHeader() {
        return getHttpHeaders(logger);
    }

    static HttpHeaders getHttpHeaders(Logger logger) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Auth-Schema", "S2S");
        headers.set("Api-Key", "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP");
        headers.set("X-Time-Zone", "");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        logger.info("add header");
        return headers;
    }
}
