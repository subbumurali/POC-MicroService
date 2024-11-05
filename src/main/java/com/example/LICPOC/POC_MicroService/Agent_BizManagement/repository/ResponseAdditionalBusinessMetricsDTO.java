package com.example.LICPOC.POC_MicroService.Agent_BizManagement.repository;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Data
public class ResponseAdditionalBusinessMetricsDTO {
    private String agent_id;
    private String premium;
    private String product_id;
    private String policy_year;

    private @Field("totrider") Integer totalRider;
    private @Field("totaverage") Integer totalAverageSumInsured;
}

