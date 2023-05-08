package com.hateoas.controller;

import com.hateoas.entity.Account;
import com.hateoas.entity.Transfer;
import com.hateoas.model.APIReponse;
import com.hateoas.services.AccountService;
import com.hateoas.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransferService transferService;

    @PostMapping
    public ResponseEntity<APIReponse> createAccount(@Valid @RequestBody Account account){
        return new ResponseEntity<>(accountService.createAccount(account), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{accountNumber}")
    public ResponseEntity<APIReponse> getAccountDetails(@PathVariable(value = "accountNumber", required = true) final Long accountNumber){
        return new ResponseEntity<>(accountService.getAccountByAccNum(accountNumber), HttpStatus.OK);
    }

    @PostMapping(value = "/deposit")
    public ResponseEntity<APIReponse> deposit(@Valid @RequestBody Account account){
        return new ResponseEntity<>(accountService.deposit(account), HttpStatus.OK);
    }

    @PostMapping(value = "/withdraw")
    public ResponseEntity<APIReponse> withdraw(@Valid @RequestBody Account account){
        return new ResponseEntity<>(accountService.withdraw(account), HttpStatus.OK);
    }

    @PostMapping(value = "/transfer")
    public ResponseEntity<APIReponse> transfer(@Valid @RequestBody Transfer transfer){
        return new ResponseEntity<>(transferService.transfer(transfer), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{accountNumber}")
    public ResponseEntity<APIReponse> delete(@PathVariable(value = "accountNumber", required = true) final Long accountNumber){
        return new ResponseEntity<>(accountService.delete(accountNumber), HttpStatus.NO_CONTENT);
    }

}