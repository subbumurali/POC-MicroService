package com.example.LICPOC.POC_MicroService.Agent_BizManagement;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Data
@Document("sales_target")
public class sales_target {
    private String agent_id;
    private String premium;
    private String product_id;
    private Integer policy_target;
}

