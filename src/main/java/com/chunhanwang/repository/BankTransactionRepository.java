package com.chunhanwang.repository;


import com.chunhanwang.entity.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface BankTransactionRepository extends MongoRepository<BankTransaction, String> {
    List<BankTransaction> findByIban(String iban);
    List<BankTransaction> findByDate(String date);
    List<BankTransaction> findByIbanAndDate(String iban, String date);
}
