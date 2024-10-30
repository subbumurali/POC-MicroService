package com.example.LICPOC.POC_MicroService.Agent_BizManagement.repository;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ResponseAgentNOPTargetDTO {
    private String agent_id;
    private String product_id;
    private String premium;
    private Integer policyCount;
}
