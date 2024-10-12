package com.example.LICPOC.POC_MicroService.Agent_BizManagement.repository;

import com.example.LICPOC.POC_MicroService.Agent_BizManagement.Agent_Sales_Details;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AgentSalesDataDB extends MongoRepository<Agent_Sales_Details, String> {

}
