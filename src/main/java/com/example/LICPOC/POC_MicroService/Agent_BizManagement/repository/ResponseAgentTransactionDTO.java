package com.example.LICPOC.POC_MicroService.Agent_BizManagement.repository;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Data
public class ResponseAgentTransactionDTO {
       private String agent_id;
       private String policy_year;
       //private String product_id;
       //private String quarter;
       //private String premium;
       private Integer policyCount_2023;
       private Integer policyCount_2024;

       private @Field("totpol_2023") Integer totalPolicyValue_2023;
       private @Field("totprem_2023") Integer totalPremiumAmount_2023;
       private @Field("totrenewal_2023") Integer totalRenewalAmount_2023;

       private @Field("totpol_2024") Integer totalPolicyValue_2024;
       private @Field("totprem_2024") Integer totalPremiumAmount_2024;
       private @Field("totrenewal_2024") Integer totalRenewalAmount_2024;

       private @Field("ticketsize_2023") Integer overallTicketSize_2023;
       private @Field("ticketsize_2024") Integer overallTicketSize_2024;
}
