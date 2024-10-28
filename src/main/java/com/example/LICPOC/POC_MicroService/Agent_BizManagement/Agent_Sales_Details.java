package com.example.LICPOC.POC_MicroService.Agent_BizManagement;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Data
@Document("sales_data")
public class Agent_Sales_Details {
    private String agent_id;
    private String premium;
    private String product_id;
    private String pol_no;
    private Date start_date;
    private Integer pol_value;
    private Integer premium_amt;
    private Integer renewal_amt;
    private String policy_year;
    private Integer quarter;
    private String new_policy;
    private Boolean policy_active;

    //@Id
    //private String agent_id;

    //@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    //private List<Premium> premium;
}

