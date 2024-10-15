package com.example.LICPOC.POC_MicroService.Agent_BizManagement.repository;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

//@Getter
//@Setter
@Data
public class ResponseAgentPerformanceDTO {
    private String id;
    private Integer policyCount;
    private @Field("totpol") Integer totalPolicyValue;
    private @Field("totprem") Integer totalPremiumAmount;
    private @Field("ticketsize") Float overallTicketSize;

    public ResponseAgentPerformanceDTO(String id, Integer policyCount, Integer totalPolicyValue, Integer totalPremiumAmount, Float overallTicketSize) {
        if(id=="true")
            this.id = "2024";
        else
            this.id = "2023";
        this.policyCount = policyCount;
        this.totalPolicyValue = totalPolicyValue;
        this.totalPremiumAmount = totalPremiumAmount;
        this.overallTicketSize = overallTicketSize;
    }

    public String id() {
        return id;
    }

    public Integer policyCount() {
        return policyCount;
    }

    public Integer totalPolicyValue() {
        return totalPolicyValue;
    }

    public Integer totalPremiumAmount() {
        return totalPremiumAmount;
    }

    public Float overallTicketSize() {
        return overallTicketSize;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPolicyCount(Integer policyCount) {
        this.policyCount = policyCount;
    }

    public void setTotalPolicyValue(Integer totalPolicyValue) {
        this.totalPolicyValue = totalPolicyValue;
    }

    public void setTotalPremiumAmount(Integer totalPremiumAmount) {
        this.totalPremiumAmount = totalPremiumAmount;
    }

    public void setOverallTicketSize(Float overallTicketSize) {
        this.overallTicketSize = overallTicketSize;
    }
}


