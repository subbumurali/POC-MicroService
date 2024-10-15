package com.example.LICPOC.POC_MicroService.Agent_BizManagement.repository;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Data
public class ResponseAgentPeerPerformanceDTO {
    private String id;
    private Integer policyCount;
    private @Field("totpol") Integer totalPolicyValue;
    private @Field("totprem") Integer totalPremiumAmount;
    private @Field("ticketsize") Float overallTicketSize;
}

