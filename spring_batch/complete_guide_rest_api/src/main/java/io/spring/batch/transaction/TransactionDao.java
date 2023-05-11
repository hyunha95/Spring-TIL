package io.spring.batch.transaction;

import java.util.List;

public interface TransactionDao {

    public List<Transaction> getTransactionByAccountNumber(String accountNumber);
}
