package com.hateoas.controller;

import com.hateoas.entity.Account;
import com.hateoas.entity.AccountStatus;
import com.hateoas.entity.Transfer;
import com.hateoas.model.APIReponse;
import com.hateoas.services.AccountService;
import com.hateoas.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransferService transferService;

    List<AccountStatus> accountStatuses = Arrays.asList(AccountStatus.INACTIVE, AccountStatus.DISABLED, AccountStatus.DORMANT);

    @PostMapping
    public ResponseEntity<APIReponse> createAccount(@Valid @RequestBody Account account){
        return new ResponseEntity<>(accountService.createAccount(account), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{accountNumber}")
    public ResponseEntity<?> getAccountDetails(@PathVariable(value = "accountNumber", required = true) final Long accountNumber){
        APIReponse accountDetails = accountService.getAccountByAccNum(accountNumber);
        Account account = (Account) accountDetails.getAccount();

        EntityModel<Account> resource = EntityModel.of(account);
        List<String> allowedactions = allowedActions(account);
        allowedactions.stream().forEach(action -> {
            if(action.equalsIgnoreCase("DEPOSIT")){
                resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).deposit(new Account())).withRel("deposit"));
            }
            if(action.equalsIgnoreCase("WITHDRAW")){
                resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).withdraw(new Account())).withRel("withdraw"));
            }
            if(action.equalsIgnoreCase("TRANSFER")){
                resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).transfer(new Transfer())).withRel("transfer"));
            }
        });

        //resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).deposit(new Account())).withRel("deposit"));
        return new ResponseEntity<>(resource, HttpStatus.OK);
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

    public List<String> allowedActions(Account account) {
        List<String> actions = new ArrayList<>();
        if (accountStatuses.contains(account.getAccountStatus())) {
            actions.add("No links to display as account is not active. Please contact customer support to make account active.");
        } else {
            if (account.getAmount().compareTo(BigDecimal.ONE) < 0) {
                actions.add("DEPOSIT");
            } else {
                actions.add("DEPOSIT");
                actions.add("TRANSFER");
                actions.add("WITHDRAW");
            }
        }
        return actions;
    }

}