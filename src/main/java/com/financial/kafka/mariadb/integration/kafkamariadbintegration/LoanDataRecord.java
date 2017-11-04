package com.financial.kafka.mariadb.integration.kafkamariadbintegration;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity class representing the Loan Record Data
 * Created by Aman on 10/14/2017.
 */
@Entity
@Table(name = "loan_details")
public final class LoanDataRecord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double loanAmt;
    private double fundedAmt;
    private String term;
    private String intRate;
    private String grade;
    private String homeOwnership;
    private double annualIncome;
    private String addressState;
    private String policyCode;

    public LoanDataRecord(double loanAmt, double fundedAmt, String term, String intRate, String grade, String homeOwnership, double annualIncome, String addressState, String policyCode) {
        this.loanAmt = loanAmt;
        this.fundedAmt = fundedAmt;
        this.term = term;
        this.intRate = intRate;
        this.grade = grade;
        this.homeOwnership = homeOwnership;
        this.annualIncome = annualIncome;
        this.addressState = addressState;
        this.policyCode = policyCode;
    }

    public LoanDataRecord() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLoanAmt() {
        return loanAmt;
    }

    public void setLoanAmt(double loanAmt) {
        this.loanAmt = loanAmt;
    }

    public double getFundedAmt() {
        return fundedAmt;
    }

    public void setFundedAmt(double fundedAmt) {
        this.fundedAmt = fundedAmt;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getIntRate() {
        return intRate;
    }

    public void setIntRate(String intRate) {
        this.intRate = intRate;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getHomeOwnership() {
        return homeOwnership;
    }

    public void setHomeOwnership(String homeOwnership) {
        this.homeOwnership = homeOwnership;
    }

    public double getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(double annualIncome) {
        this.annualIncome = annualIncome;
    }

    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    public String getPolicyCode() {
        return policyCode;
    }

    public void setPolicyCode(String policyCode) {
        this.policyCode = policyCode;
    }

    @Override
    public String toString() {
        return "LoanDataRecord{" +
                "id=" + id +
                ", loanAmt=" + loanAmt +
                ", fundedAmt=" + fundedAmt +
                ", term='" + term + '\'' +
                ", intRate='" + intRate + '\'' +
                ", grade='" + grade + '\'' +
                ", homeOwnership='" + homeOwnership + '\'' +
                ", annualIncome=" + annualIncome +
                ", addressState='" + addressState + '\'' +
                ", policyCode='" + policyCode + '\'' +
                '}';
    }
}
