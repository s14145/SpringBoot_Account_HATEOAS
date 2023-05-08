package com.hateoas.services;

import com.hateoas.entity.Account;
import com.hateoas.entity.Transfer;
import com.hateoas.model.APIReponse;

public interface AccountService {

    APIReponse deposit(Account account);

    APIReponse getAccountByAccNum(Long accountNumber);

    APIReponse withdraw(Account account);

    APIReponse createAccount(Account account);

    APIReponse delete(Long accountNumber);

}