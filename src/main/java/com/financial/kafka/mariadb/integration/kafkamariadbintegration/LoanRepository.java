package com.financial.kafka.mariadb.integration.kafkamariadbintegration;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository class to store the Loan Details in a MariaDB
 * Created by Aman on 10/31/2017.
 */
@Repository
public interface LoanRepository extends CrudRepository<LoanDataRecord, Long> {

    List<LoanDataRecord> findByTerm(String term);
}
