package com.example.LICPOC.POC_MicroService.Agent_BizManagement;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

public class Premium {
    private String single;

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<Endowment_Plan> endowment_plan;
}
