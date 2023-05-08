package com.hateoas.services;

import com.hateoas.entity.Transfer;
import com.hateoas.model.APIReponse;

public interface TransferService {

    APIReponse transfer(Transfer transfer);
}
