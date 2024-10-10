package com.example.LICPOC.POC_MicroService.Agent_BizManagement;

import com.example.LICPOC.POC_MicroService.CustomerRevival.SubscriberDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AgentSalesDataDB extends MongoRepository<Agent_Sales_Details, String> {

}
