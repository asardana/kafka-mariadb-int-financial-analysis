package com.financial.kafka.mariadb.integration.kafkamariadbintegration;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Properties;

/**
 * Created by Aman on 10/21/2017.
 */
@Component
public class LoanDataKafkaConsumer implements CommandLineRunner {

    private static String kafkaBrokerEndpoint = null;
    private static String loanDataIngestTopic = null;

    @Autowired
    LoanRepository loanRepository;

    public static String cleanRecordField(String recordField) {
        return recordField.replaceAll("[\"\"%]", "").trim();
    }

    @Override
    public void run(String... args) throws Exception {
        // Read command Line Arguments for Kafka broker and Topic for publishing the Loan Records
        if (args != null) {

            kafkaBrokerEndpoint = args[0];
            loanDataIngestTopic = args[1];
        }

        this.consumeLoanStatFinancialData();
    }

    /**
     * Consumes the Loan Data Records from the Kafka Broker
     */
    private void consumeLoanStatFinancialData() {

        final Consumer<String, String> loanDataConsumer = createKafkaConsumer();

        while (true) {

            // Kafka Consumer polls the Topic every 5 seconds to get the new messages
            final ConsumerRecords<String, String> consumerLoanRecords = loanDataConsumer.poll(5000);

            consumerLoanRecords.forEach(conumerRecord -> {
                LoanDataRecord loanDataRecord = getLoanRecordEntity(conumerRecord.value().toString());
                loanRepository.save(loanDataRecord);
                System.out.println("Saved Record --> " + conumerRecord.key() + " | " + conumerRecord.offset() + " | "
                        + conumerRecord.partition());
            });
            loanDataConsumer.commitAsync();
        }
    }

    /**
     * Creates the Kafka Consumer with the required cofiguration
     *
     * @return KafkaConsumer
     */
    private KafkaConsumer<String, String> createKafkaConsumer() {

        Properties prop = new Properties();

        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBrokerEndpoint);
        prop.put(ConsumerConfig.GROUP_ID_CONFIG, "LoanDataKafkaConsumer");
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        final KafkaConsumer kafkaConsumer = new KafkaConsumer<String, String>(prop);
        kafkaConsumer.subscribe(Collections.singletonList(loanDataIngestTopic));

        return kafkaConsumer;
    }

    LoanDataRecord getLoanRecordEntity(String loanRecord) {

        LoanDataRecord loanDataRecord = new LoanDataRecord();

        if (!(loanRecord.isEmpty() || loanRecord.contains("member_id") || loanRecord.contains("Total amount funded in policy code"))) {

            // Few records have emp_title with comma separated values resulting in records getting rejected. Cleaning the data before creating Dataset
            String updatedLine = loanRecord.replace(", ", "|").replaceAll("[a-z],", "");

            String loanRecordSplits[] = updatedLine.split(",\"");

            loanDataRecord.setLoanAmt(Double.parseDouble(this.cleanRecordField(loanRecordSplits[2])));
            loanDataRecord.setFundedAmt(Double.parseDouble(this.cleanRecordField(loanRecordSplits[3])));
            loanDataRecord.setTerm(this.cleanRecordField(loanRecordSplits[5]));
            loanDataRecord.setIntRate(this.cleanRecordField(loanRecordSplits[6]));
            loanDataRecord.setGrade(this.cleanRecordField(loanRecordSplits[8]));
            loanDataRecord.setHomeOwnership(this.cleanRecordField(loanRecordSplits[12]));
            loanDataRecord.setAnnualIncome(Double.parseDouble(this.cleanRecordField(loanRecordSplits[13])));
            loanDataRecord.setAddressState(this.cleanRecordField(loanRecordSplits[23]));
            loanDataRecord.setPolicyCode(this.cleanRecordField(loanRecordSplits[51]));
        } else {
            System.out.println("Invalid Record line " + loanRecord);
        }

        return loanDataRecord;

    }


}
