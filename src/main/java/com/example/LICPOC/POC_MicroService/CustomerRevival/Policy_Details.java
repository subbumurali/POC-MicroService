package com.example.LICPOC.POC_MicroService.CustomerRevival;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
@Setter
public class Policy_Details {
    @Id
    private String policy_number;
    private String policy_name;
    private String basic_sum_assured;
    private String policy_type;
    private String policy_term_years;
    private String payment_premium_term_years;
    private String frequency;
    private String nominee_name;
    private Date policy_commencement_date;
    private Date policy_maturity_date;
    private Date premium_due_date;
    private Boolean lapsed_status;
    private Boolean revived_status;
    private PremiumDetails premium_details;
 }
