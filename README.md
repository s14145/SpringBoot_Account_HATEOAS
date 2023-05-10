# Spring Boot Account REST API Using HATEOAS

This Spring Boot application runs on port 6061.

The technologies used in this application:

1. Spring Boot

2. Spring Boot Devtools

3. Lombok

4. H2 Database

5. Spring Boot Data JPA

6. HATEOAS (Hypermedia As The Engine Of Application State)

7. SSL (Secure Socket Layer)
##
H2 Database URI: https://localhost:6061/h2-console

**The REST API endpoints exposed by this application are: **

1. POST Account: https://localhost:6061/api/v1/accounts

2. GET Account: https://localhost:6061/api/v1/accounts/{accountNumber}

3. POST Deposit: https://localhost:6061/api/v1/accounts/deposit

4. POST Withdraw: https://localhost:6061/api/v1/accounts/withdraw

5. POST Transfer: https://localhost:6061/api/v1/accounts/transfer

6. DELETE Account: https://localhost:6061/api/v1/accounts/1289876287



**Sample POST Account request payload:**

{

    "accountNumber": 1289876287,
    "amount": 50000,
    "rateOfInterest": 15,
    "accountType": "CHECKING",
    "accountStatus": "ACTIVE"
    
}

**Sample POST Transfer request payload:**

{

    "toAccountNumber": 1289876287,
    "fromAccountNumber": 1378965479,
    "transferAmount": 5000,
    "transferMode": "ACH"
    
}




