package com.example.LICPOC.POC_MicroService.CustomerRevival;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubscriberDetailsDB extends MongoRepository<SubscriberDetails, String>{

}
