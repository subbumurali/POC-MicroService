package com.example.LICPOC.POC_MicroService.Agent_BizManagement;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Endowment_Plan {
    private String pol_no;
    private Date start_date;
    private Integer pol_value;
    private Integer premium_amt;
}
