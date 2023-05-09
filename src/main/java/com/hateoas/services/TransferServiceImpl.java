package com.hateoas.services;

import com.hateoas.entity.Account;
import com.hateoas.entity.AccountStatus;
import com.hateoas.entity.Transfer;
import com.hateoas.exception.APIException;
import com.hateoas.model.APIReponse;
import com.hateoas.repository.AccountRepository;
import com.hateoas.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service("TransferService")
public class TransferServiceImpl implements TransferService {

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private AccountRepository accountRepository;

    List<AccountStatus> accountStatuses = Arrays.asList(AccountStatus.INACTIVE, AccountStatus.DISABLED, AccountStatus.DORMANT);

    @Override
    public APIReponse transfer(Transfer transfer) {
        APIReponse apiReponse = new APIReponse();
        Optional<Account> optionalRetrievedFromAccount = accountRepository.findById(transfer.getFromAccountNumber());
        if (optionalRetrievedFromAccount.isPresent()) {
            Account retrievedFromAccount = optionalRetrievedFromAccount.get();

            if (transfer == null) {
                apiReponse.setMessage("Field cannot have null value.");
                throw new APIException(HttpStatus.BAD_REQUEST, "Field cannot have null value.");
            } else if (!accountRepository.existsById(transfer.getFromAccountNumber())) {
                apiReponse.setMessage("Source account doesn't exist.");
                throw new APIException(HttpStatus.BAD_REQUEST, "Destination account doesn't exist.");
            } else if (!accountRepository.existsById(transfer.getToAccountNumber())) {
                apiReponse.setMessage("Destination account doesn't exist.");
                throw new APIException(HttpStatus.BAD_REQUEST, "Destination account doesn't exist.");
            } else if (accountStatuses.contains(retrievedFromAccount.getAccountStatus())) {
                apiReponse.setMessage("Source account is either inactive, disabled or dormant. Transfer operation cannot be performed.");
                throw new APIException(HttpStatus.BAD_REQUEST, "Source account is either inactive, disabled or dormant. Transfer operation cannot be performed.");
            } else {
                Optional<Account> optionalRetrieveToAccount = accountRepository.findById(transfer.getToAccountNumber());
                if (optionalRetrieveToAccount.isPresent()) {
                    Account retrieveToAccount = optionalRetrieveToAccount.get();
                    if (accountStatuses.contains(retrieveToAccount.getAccountStatus())) {
                        apiReponse.setMessage("Destination account is either inactive, disabled or dormant. Transfer operation cannot be performed.");
                        throw new APIException(HttpStatus.BAD_REQUEST, "Destination account is either inactive, disabled or dormant. Transfer operation cannot be performed.");
                    } else if (retrievedFromAccount.getAmount().compareTo(transfer.getTransferAmount()) == -1) {
                        apiReponse.setMessage("Not enough funds to proceed with transfer. You have " + retrievedFromAccount.getAmount() + " amount in your account " + retrievedFromAccount.getAccountNumber() + ".");
                        throw new APIException(HttpStatus.BAD_REQUEST, "Not enough funds to proceed with transfer.");
                    }
                    retrievedFromAccount.setAmount(retrievedFromAccount.getAmount().subtract(transfer.getTransferAmount()));
                    retrieveToAccount.setAmount(retrieveToAccount.getAmount().add(transfer.getTransferAmount()));
                    apiReponse.setMessage("Transfered successfully.");
                    apiReponse.setAccount(retrievedFromAccount);
                }
            }
        }
        return apiReponse;
    }
}