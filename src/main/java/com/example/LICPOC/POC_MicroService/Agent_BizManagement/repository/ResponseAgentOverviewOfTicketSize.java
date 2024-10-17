package com.example.LICPOC.POC_MicroService.Agent_BizManagement.repository;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Data
public class ResponseAgentOverviewOfTicketSize {
    //private String id;
    private String agent_id;
    private String current_year;
    private String premium;
    private String plan_type;
    private String quarter;
    private @Field("ticketsize") Float overallTicketSize;

    public ResponseAgentOverviewOfTicketSize(String agent_id, String current_year, String premium, String plan_type, String quarter, Float overallTicketSize) {
        this.agent_id = agent_id;
        if(current_year=="true")
            this.current_year = "2024";
        else
            this.current_year = "2023";
        this.premium = premium;
        this.plan_type = plan_type;
        this.quarter = quarter;
        this.overallTicketSize = overallTicketSize;
    }
}
