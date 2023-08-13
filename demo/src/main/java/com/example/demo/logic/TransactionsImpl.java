package com.example.demo.logic;

import com.example.demo.dao.TransactionsRepository;
import com.example.demo.dto.ResponseTransactionDto;
import com.example.demo.dto.Transformer;
import com.example.demo.exceptions.DemoExceptionUnchecked;
import com.example.demo.service.TransactionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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
    public ResponseEntity<ResponseTransactionDto> getTransactions(Long accountId, String fromAccountingDate, String toAccountingDate) {
        String transactionsUri = "https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/"
                + accountId + "/transactions?"
                .concat("fromAccountingDate=" + fromAccountingDate)
                .concat("&toAccountingDate=" + toAccountingDate);
        HttpEntity<Void> requestEntity = new HttpEntity<>(addHeader());
        try {
            ResponseEntity<ResponseTransactionDto> response = restTemplate.exchange(
                    transactionsUri, HttpMethod.GET, requestEntity,
                    ResponseTransactionDto.class);
            String info = response.getBody() != null
                    ? "transactions of ".concat(accountId.toString()) : "not found for  " + accountId;
            saveTransactions(response);
            logger.info(info);
            return response;
        } catch (RuntimeException e) {
            ResponseTransactionDto response = new ResponseTransactionDto();
            response.setDemoError(e.getMessage());
            response.setErrorCode("customErrorCode");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void saveTransactions(ResponseEntity<ResponseTransactionDto> response) {
        try {
            Optional.ofNullable(response.getBody())
                    .ifPresent(transaction -> transactionsRepository.saveAllAndFlush(
                            response.getBody().getPayload().stream()
                                    .map(Transformer::transformTransaction).toList()));
        } catch (RuntimeException e) {
            throw new DemoExceptionUnchecked("custom error message", e);

        }
    }

    private HttpHeaders addHeader() {
        return getHttpHeaders(logger);
    }
}
