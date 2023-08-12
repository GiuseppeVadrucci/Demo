package com.example.demo.controller;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.ResponseTransactionDto;
import com.example.demo.dto.ResponseTransferDto;
import com.example.demo.dto.TransferDto;
import com.example.demo.service.BalancesService;
import com.example.demo.service.TransactionsService;
import com.example.demo.service.TransfersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DemoController {

    Logger logger = LoggerFactory.getLogger(DemoController.class);

    private final BalancesService balancesService;
    private final TransfersService transfersService;
    private final TransactionsService transactionsService;

    public DemoController(BalancesService balancesService,
                          TransfersService transfersService,
                          TransactionsService transactionsService) {
        this.balancesService = balancesService;
        this.transfersService = transfersService;
        this.transactionsService = transactionsService;
    }

    @GetMapping("/{accountId}/getBalance")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseDto> getBalance(@PathVariable Long accountId) {
        logger.info("getBalance api");
        return balancesService.get(accountId);
    }

    @PostMapping("/{accountId}/transfer")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseTransferDto> transfer(@PathVariable Long accountId, TransferDto transfer) {
        logger.info("transfer api");
        return transfersService.post(accountId, transfer);
    }

    @GetMapping("/getTransactions")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseTransactionDto> getTransactions(@RequestParam Long accountId,
                                                                  @RequestParam String fromAccountingDate,
                                                                  @RequestParam String toAccountingDate) {
        logger.info("getTransactions api");
        return transactionsService.getTransactions(accountId, fromAccountingDate, toAccountingDate);
    }
}
