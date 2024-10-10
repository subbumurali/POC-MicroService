package com.example.LICPOC.POC_MicroService.CustomerRevival;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

import java.awt.*;
import java.util.List;

@Getter
@Setter
@Data
@Document("SubscriberMaster_WIP")
public class SubscriberDetails {
    @Id
    private String user_id;
    private String subscriber_name;
    private String email_id;
    private String gender;
    private String contact_number;
    private String address;
    private OccupationalDetails occupational_details;
    private MedicalCondition medical_condition;
    private Cardiovascular_Details cardiovascular_details;
    private Life_Style lifestyle_details;

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<Policy_Details> policy_details;
}
