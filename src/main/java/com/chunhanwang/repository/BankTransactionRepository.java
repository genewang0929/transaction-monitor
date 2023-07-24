package com.chunhanwang.repository;


import com.chunhanwang.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface BankTransactionRepository extends MongoRepository<BankTransaction, String> {
    List<BankTransaction> findByIbanAndDate(String iban, String date);
    Page<BankTransaction> findByIban(String iban, Pageable pageable);
}
