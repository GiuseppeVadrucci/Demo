package com.example.demo.logic;

import com.example.demo.dao.BalanceRepository;
import com.example.demo.dto.DtoToEntity;
import com.example.demo.dto.ResponseDto;
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
    private BalanceRepository balanceRepository;


    @Autowired
    RestTemplate restTemplate;


    @Override
    public ResponseDto get(String accountId) {
        String balanceUri = "https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/{accountId}/balance";
        HttpEntity<Void> requestEntity = new HttpEntity<>(addHeader());
        ResponseEntity<ResponseDto> response = restTemplate.exchange(
                balanceUri, HttpMethod.GET, requestEntity, ResponseDto.class, accountId);
        logger.info("get balance");
        Optional.ofNullable(response.getBody())
                .ifPresent(balance -> balanceRepository.save(DtoToEntity.transformBalance(
                        balance.getPayload())));
        logger.info("save balance");
        return response.getBody();
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
