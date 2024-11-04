package com.example.LICPOC.POC_MicroService.Agent_BizManagement.repository;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Data
public class ResponseAgentPeerTargetDTO {
    private String agent_id;
    private String product_id;
    private String premium;
    private Integer policyCount;
    private Object prdTargetMapping;
}

