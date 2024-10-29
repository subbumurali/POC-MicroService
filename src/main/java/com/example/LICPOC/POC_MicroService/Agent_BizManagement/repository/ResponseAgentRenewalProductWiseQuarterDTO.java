package com.example.LICPOC.POC_MicroService.Agent_BizManagement.repository;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Data
public class ResponseAgentRenewalProductWiseQuarterDTO {
    private String agent_id;
    private String policy_year;
    private String product_id;
    private Integer quarter;
    private String premium;
    private Boolean policy_active;
    private Integer policyCount;

    private @Field("totpol") Integer totalPolicyValue;
    private @Field("totrenewal") Integer totalRenewalAmount;

    private @Field("ticketsize") Integer overallTicketSize;
}
