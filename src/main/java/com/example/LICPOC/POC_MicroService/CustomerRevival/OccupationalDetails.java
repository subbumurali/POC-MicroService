package com.example.LICPOC.POC_MicroService.CustomerRevival;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OccupationalDetails {
    private String present_occupation;
    private String employer_name;
    private Date emp_start_date;
    private String kind_of_occupation;
    private String annual_income;
    private String involve_hazardous_activity;
    //private MedicalCondition medical_condition;
}
