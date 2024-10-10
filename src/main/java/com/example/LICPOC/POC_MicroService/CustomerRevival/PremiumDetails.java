package com.example.LICPOC.POC_MicroService.CustomerRevival;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PremiumDetails {
    private String premium_amount;
    private String gst;
    private String premium_amount_incl_gst;
    private String unpaid_premiums;
    private String total_premium_due;
    private String late_fees;
    private String total_amount_due;
}
