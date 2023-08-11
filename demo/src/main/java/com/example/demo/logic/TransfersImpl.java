package com.example.demo.logic;

import com.example.demo.dao.TransferRepository;
import com.example.demo.dto.*;
import com.example.demo.entity.Transfer;
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
    public ResponseTransferDto post(String accountId, TransferDto dto) {
        String transferUrl = "https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/{accountId}/payments/money-transfers";
        HttpEntity<TransferDto> requestEntity = new HttpEntity<>(dto, addHeader());
        ResponseEntity<ResponseTransferDto> response = restTemplate.exchange(
                transferUrl, HttpMethod.POST, requestEntity, ResponseTransferDto.class,
                accountId);
        logger.info("transfer");
        Transfer transfer = Transformer.transformTransfer(dto);
        Optional.of(response.getStatusCode())
                .filter(HttpStatus.OK::equals)
                .ifPresent(el -> transferRepository.save(transfer));
        logger.info("save transfer");
        return response.getBody();
    }

    private HttpHeaders addHeader() {
        return getHttpHeaders(logger);
    }
}
