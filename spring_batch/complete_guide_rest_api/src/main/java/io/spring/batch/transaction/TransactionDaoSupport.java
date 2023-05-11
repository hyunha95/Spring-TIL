package io.spring.batch.transaction;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class TransactionDaoSupport extends JdbcTemplate implements TransactionDao {

    public TransactionDaoSupport(DataSource dataSource) {
        super(dataSource);
    }

    @SuppressWarnings("unchecked")
    public List<Transaction> getTransactionByAccountNumber(String accountNumber) {
        return query("select t.id, t.timestamp, t. amount " +
                         "from transaction t inner join account_summary a on " +
                            "a.id = t.account_summary_id " +
                         "where a.account_number = ?",
                new Object[] {accountNumber},
                (rs, rowNum) -> {
                    Transaction transaction = new Transaction();
                    transaction.setAmount(rs.getDouble("amount"));
                    transaction.setTimestamp(rs.getDate("timestamp"));
                    return transaction;
                });
    }
}
