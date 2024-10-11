package com.example.LICPOC.POC_MicroService.CustomerRevival;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.crypto.KeyAgreementSpi;
import java.lang.reflect.Field;
import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class Revival_Controller {
    @Autowired
    SubscriberDetailsDB subscriberDetailsDB;

    @GetMapping("/api/v1/getpolicies/{user_id}")
    public SubscriberDetails getSingleUserDetails(@PathVariable String user_id){
        return subscriberDetailsDB.findById(user_id).get();
    }

    @GetMapping("/api/v1/getpolicies")
    public List<SubscriberDetails> getAllUserDetails(){
        return subscriberDetailsDB.findAll();
    }

    @PostMapping("/api/v1/addpolicies")
    public SubscriberDetails addUserDetails(@RequestBody SubscriberDetails subscriberDetails) {
        return subscriberDetailsDB.save(subscriberDetails);
    }

    @DeleteMapping("/api/v1/deletepolicies/{user_id}")
    public void deleteSingleUserDetails(@PathVariable String user_id)
    {
        subscriberDetailsDB.deleteById(user_id);
    }

    @DeleteMapping("/api/v1/deletepolicies")
    public void deleteAllUserDetails(){
        subscriberDetailsDB.deleteAll();
    }

    @PutMapping("/api/v1/updatepolicies/{user_id}")
    public SubscriberDetails updateSingleUserDetails(@PathVariable String user_id, @RequestBody SubscriberDetails subscriberDetails){
        subscriberDetailsDB.deleteById(user_id);
        subscriberDetailsDB.save(subscriberDetails);
        return subscriberDetails;
    }

    @PatchMapping("/api/v1/patchbyuserid/{user_id}")
    public SubscriberDetails patchSingleUserDetails(@PathVariable String user_id, @RequestBody Map<String, Object> updatedFields){
        SubscriberDetails existingSubscriberDetails = subscriberDetailsDB.findById(user_id).get();

        updatedFields.forEach((key,value)->{
            Field myField = ReflectionUtils.findField(SubscriberDetails.class, key);
            myField.setAccessible(true);
            ReflectionUtils.setField(myField,existingSubscriberDetails,value);
        });
        return subscriberDetailsDB.save(existingSubscriberDetails);
    }
}
