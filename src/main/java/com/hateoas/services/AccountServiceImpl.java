package com.hateoas.services;

import com.hateoas.entity.Account;
import com.hateoas.entity.AccountStatus;
import com.hateoas.exception.APIException;
import com.hateoas.model.APIReponse;
import com.hateoas.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service("AccountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    List<AccountStatus> accountStatuses = Arrays.asList(AccountStatus.INACTIVE, AccountStatus.DISABLED, AccountStatus.DORMANT);


    @Override
    public APIReponse createAccount(Account account) {
        APIReponse apiReponse = new APIReponse();
        if (account == null) {
            apiReponse.setMessage("Field cannot have null value.");
            throw new APIException(HttpStatus.BAD_REQUEST, "Field cannot have null value.");
        } else if (accountRepository.existsById(account.getAccountNumber())) {
            apiReponse.setMessage("Account already exists.");
            throw new APIException(HttpStatus.BAD_REQUEST, "Account already exists.");
        } else if (account.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            apiReponse.setMessage("Amount has to be more than 0.");
        } else {
            Account savedAccount = accountRepository.save(account);
            apiReponse.setMessage("Account saved successfully.");
            apiReponse.setAccount(savedAccount);
        }
        return apiReponse;
    }

    @Override
    public APIReponse getAccountByAccNum(Long accountNumber) {
        APIReponse apiReponse = new APIReponse();

        if (accountNumber == null) {
            apiReponse.setMessage("Account number cannot have null value.");
            throw new APIException(HttpStatus.BAD_REQUEST, "Field cannot have null value.");
        } else if (!accountRepository.existsById(accountNumber)) {
            apiReponse.setMessage("Account doesn't exist.");
            throw new APIException(HttpStatus.BAD_REQUEST, "Account doesn't exist.");
        } else {
            Optional<Account> optionalRetrieveAccount = accountRepository.findById(accountNumber);
            if (optionalRetrieveAccount.isPresent()) {
                Account retrieveAccount = optionalRetrieveAccount.get();
                apiReponse.setMessage("Account retrieved successfully.");
                apiReponse.setAccount(retrieveAccount);
            }
        }
        return apiReponse;
    }

    @Override
    public APIReponse deposit(Account account) {
        APIReponse apiReponse = new APIReponse();
        Optional<Account> optionalRetrievedAccount = accountRepository.findById(account.getAccountNumber());
        if (optionalRetrievedAccount.isPresent()) {
            Account retrievedAccount = optionalRetrievedAccount.get();

            if (account == null) {
                apiReponse.setMessage("Field cannot have null value.");
                throw new APIException(HttpStatus.BAD_REQUEST, "Field cannot have null value.");
            } else if (!accountRepository.existsById(account.getAccountNumber())) {
                apiReponse.setMessage("Account doesn't exist.");
                throw new APIException(HttpStatus.BAD_REQUEST, "Account doesn't exist.");
            }else if(accountStatuses.contains(retrievedAccount.getAccountStatus())){
                apiReponse.setMessage("Account is either inactive, disabled or dormant. Deposit operation cannot be performed.");
                throw new APIException(HttpStatus.BAD_REQUEST, "Account is either inactive, disabled or dormant. Deposit operation cannot be performed.");
            }else {
                BigDecimal newAmount = retrievedAccount.getAmount().add(account.getAmount());
                retrievedAccount.setAmount(newAmount);
                accountRepository.save(retrievedAccount);
                apiReponse.setMessage("Deposited successfully.");
                apiReponse.setAccount(retrievedAccount);
            }
        }
        return apiReponse;
    }

    @Override
    public APIReponse withdraw(Account account) {
        APIReponse apiReponse = new APIReponse();
        Optional<Account> optionalRetrievedAccount = accountRepository.findById(account.getAccountNumber());
        if (optionalRetrievedAccount.isPresent()) {
            Account retrievedAccount = optionalRetrievedAccount.get();

            if (account == null) {
                apiReponse.setMessage("Field cannot have null value.");
                throw new APIException(HttpStatus.BAD_REQUEST, "Field cannot have null value.");
            } else if (!accountRepository.existsById(account.getAccountNumber())) {
                apiReponse.setMessage("Account doesn't exist.");
                throw new APIException(HttpStatus.BAD_REQUEST, "Account doesn't exist.");
            } else if(accountStatuses.contains(retrievedAccount.getAccountStatus())) {
                apiReponse.setMessage("Account is either inactive, disabled or dormant. Withdraw operation cannot be performed.");
                throw new APIException(HttpStatus.BAD_REQUEST, "Account is either inactive, disabled or dormant. Withdraw operation cannot be performed.");
            } else if (retrievedAccount.getAmount().compareTo(account.getAmount()) == -1) {
                apiReponse.setMessage("Insufficient Funds. " + "You have " + retrievedAccount.getAmount() + " amounts in your account number " + retrievedAccount.getAccountNumber() + ".");
                throw new APIException(HttpStatus.BAD_REQUEST, "Insufficient Funds.");
            } else {
                BigDecimal newAmount = retrievedAccount.getAmount().subtract(account.getAmount());
                retrievedAccount.setAmount(newAmount);
                accountRepository.save(retrievedAccount);
                apiReponse.setMessage("Withdraw Successful.");
                apiReponse.setAccount(retrievedAccount);
            }
        }
        return apiReponse;
    }

    @Override
    public APIReponse delete(Long accountNumber) {
        APIReponse apiReponse = new APIReponse();

        if (accountNumber == null) {
            apiReponse.setMessage("Account number cannot have null value.");
            throw new APIException(HttpStatus.BAD_REQUEST, "Field cannot have null value.");
        } else if (!accountRepository.existsById(accountNumber)) {
            apiReponse.setMessage("Account doesn't exist.");
            throw new APIException(HttpStatus.BAD_REQUEST, "Account doesn't exist.");
        } else {
            Optional<Account> optionalRetrieveAccount = accountRepository.findById(accountNumber);
            if (optionalRetrieveAccount.isPresent()) {
                Account retrieveAccount = optionalRetrieveAccount.get();
                accountRepository.delete(retrieveAccount);
                apiReponse.setMessage("Account deleted successfully.");
                apiReponse.setAccount(retrieveAccount);
            }
        }
        return apiReponse;
    }

}