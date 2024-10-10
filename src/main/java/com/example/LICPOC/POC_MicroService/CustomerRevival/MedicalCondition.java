package com.example.LICPOC.POC_MicroService.CustomerRevival;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MedicalCondition {
    private Boolean in_good_health;
    private Boolean consulted_doctor_last_five_years;
    private Boolean hospital_admission_history;
    private Diagnosed_Condition diagnosed_conditions;
}
