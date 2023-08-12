package com.example.demo.logic;

import com.example.demo.dao.TransferRepository;
import com.example.demo.dto.ResponseTransferDto;
import com.example.demo.dto.TransferDto;
import com.example.demo.dto.Transformer;
import com.example.demo.entity.Transfer;
import com.example.demo.exceptions.DemoExceptionUnchecked;
import com.example.demo.service.TransfersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.example.demo.logic.BalancesImpl.getHttpHeaders;

@Service
public class TransfersImpl implements TransfersService {
    Logger logger = LoggerFactory.getLogger(TransfersImpl.class);

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseEntity<ResponseTransferDto> post(Long accountId, TransferDto dto) {
        String transferUrl = "https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/{accountId}/payments/money-transfers";
        HttpEntity<TransferDto> requestEntity = new HttpEntity<>(dto, addHeader());
        try {
            ResponseEntity<ResponseTransferDto> response = restTemplate.exchange(
                    transferUrl, HttpMethod.POST, requestEntity, ResponseTransferDto.class,
                    accountId);
            String info = response.getBody() != null
                    ? "transfer of ".concat(accountId.toString()) : "not found for  " + accountId;
            Transfer transfer = Transformer.transformTransfer(dto);
            Optional.of(response.getStatusCode())
                    .filter(HttpStatus.OK::equals)
                    .ifPresent(el -> transferRepository.save(transfer));
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
