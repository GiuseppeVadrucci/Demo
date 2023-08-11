package com.example.demo.logic;

import com.example.demo.dao.TransactionsRepository;
import com.example.demo.dto.DtoToEntity;
import com.example.demo.dto.ResponseTransactionDto;
import com.example.demo.service.TransactionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.example.demo.logic.BalancesImpl.getHttpHeaders;

@Service
public class TransactionsImpl implements TransactionsService {

    Logger logger = LoggerFactory.getLogger(TransactionsImpl.class);

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public ResponseTransactionDto getTransactions(String accountId) {
        String transactionsUri = "https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/"
                + accountId + "/transactions?fromAccountingDate=2019-01-01&toAccountingDate=2019-12-01";
        HttpEntity<Void> requestEntity = new HttpEntity<>(addHeader());
        ResponseEntity<ResponseTransactionDto> response = restTemplate.exchange(
                transactionsUri, HttpMethod.GET, requestEntity,
                ResponseTransactionDto.class);
        logger.info("transaction");
        Optional.ofNullable(response.getBody())
                .ifPresent(transaction -> transactionsRepository.saveAndFlush(DtoToEntity.transformTransaction(
                        transaction.getPayload())));
        logger.info("save transaction");
        return response.getBody();
    }


    private HttpHeaders addHeader() {
        return getHttpHeaders(logger);
    }
}