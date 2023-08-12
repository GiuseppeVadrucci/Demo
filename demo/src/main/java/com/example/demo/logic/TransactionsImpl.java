package com.example.demo.logic;

import com.example.demo.dao.TransactionsRepository;
import com.example.demo.dto.ResponseTransactionDto;
import com.example.demo.dto.Transformer;
import com.example.demo.exceptions.DemoExceptionUnchecked;
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
    public ResponseEntity<ResponseTransactionDto> getTransactions(Long accountId) {
        String transactionsUri = "https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/"
                + accountId + "/transactions?fromAccountingDate=2019-01-01&toAccountingDate=2019-12-01";
        HttpEntity<Void> requestEntity = new HttpEntity<>(addHeader());
        try {
            ResponseEntity<ResponseTransactionDto> response = restTemplate.exchange(
                    transactionsUri, HttpMethod.GET, requestEntity,
                    ResponseTransactionDto.class);
            String info = response.getBody() != null
                    ? "transactions of ".concat(accountId.toString()) : "not found for  " + accountId;
            Optional.ofNullable(response.getBody())
                    .ifPresent(transaction -> transactionsRepository.saveAndFlush(
                            Transformer.transformTransaction(
                                    transaction.getPayload())));
            logger.info(info);
            return response;
        } catch (RuntimeException e) {
            throw new DemoExceptionUnchecked("custom error message:" +
                    "caught a runtime exception", e);
        }
    }


    private HttpHeaders addHeader() {
        return getHttpHeaders(logger);
    }
}
