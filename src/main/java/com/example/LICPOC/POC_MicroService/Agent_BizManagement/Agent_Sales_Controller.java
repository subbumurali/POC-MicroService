package com.example.LICPOC.POC_MicroService.Agent_BizManagement;

import com.example.LICPOC.POC_MicroService.Agent_BizManagement.repository.AgentSalesDataDB;
import com.example.LICPOC.POC_MicroService.Agent_BizManagement.repository.ResponseAgentTransactionDTO;
import com.example.LICPOC.POC_MicroService.Agent_BizManagement.repository.SearchRepository;
import com.example.LICPOC.POC_MicroService.Agent_BizManagement.repository.SearchRepositoryImplementation;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import jakarta.websocket.server.PathParam;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RestController
public class Agent_Sales_Controller {
    @Autowired
    AgentSalesDataDB agentSalesDataDB;

    @Autowired
    MongoTemplate agentSalesDataMongoTemplate;

    @Autowired
    MongoClient agentSalesDataClient;

    @Autowired
    MongoConverter agentSalesDataConverter;

    @GetMapping("/api/v1/searchagenttransaction/{agent_id1}")
    public List<ResponseAgentTransactionDTO> searchTransactionData(@PathVariable String agent_id1){
  //      MatchOperation matchOperation1 = match(new Criteria("agent_id").in(agent_id1));
    //    MatchOperation matchOperation2 = match(new Criteria("current_year").is(true));
        //GroupOperation groupOperation1 = Aggregation.group("agent_id").count().as("policyCount");
  //      GroupOperation groupOperation2 = Aggregation.group("_id").count().as("count");
//        GroupOperation groupOperation3 = Aggregation.group("premium").sum("$premium").as("totalpremiumamount");
  //      GroupOperation groupOperation4 = Aggregation.group("premium").count().as("totalpolicycount");

   //     Aggregation myAggregation = Aggregation.newAggregation(Agent_Sales_Details.class, matchOperation1, matchOperation2, groupOperation3, groupOperation4);

    //    AggregationResults<ResponseAgentTransactionDTO> aggregationResults = agentSalesDataMongoTemplate.aggregate(myAggregation, "agent_transaction_data", ResponseAgentTransactionDTO.class);
      //  return aggregationResults.getMappedResults();

        TypedAggregation<Agent_Sales_Details> agentSalesDetailsTypedAggregation =
                newAggregation(Agent_Sales_Details.class, match(new Criteria("agent_id").in(agent_id1)),
                match(new Criteria("current_year").is(true)),
                group("premium")
                        .count().as("policyCount")
                        .sum("pol_value").as("totpol")
                        .sum("premium_amt").as("totprem"));
        AggregationResults<ResponseAgentTransactionDTO> aggregationResults =
                agentSalesDataMongoTemplate.aggregate(agentSalesDetailsTypedAggregation, ResponseAgentTransactionDTO.class);

        return aggregationResults.getMappedResults();
    }

    //@GetMapping("/api/v1/getagentsalesdata/{agent_id}")
    public List<Agent_Sales_Details> getAgentSalesDetails(@PathVariable String agent_id) {
        List<Agent_Sales_Details> agentSalesDetailsList = new ArrayList<Agent_Sales_Details>();
        agentSalesDetailsList.add(agentSalesDataDB.findById(agent_id).get());
        return agentSalesDetailsList;
    }

    @PostMapping("/api/v1/addagentsalestransactiondata")
    public List<Agent_Sales_Details> addAgentSalesData(@RequestBody List<Agent_Sales_Details> agentSalesDetailsList) {
        return new ArrayList<>(agentSalesDataDB.saveAll(agentSalesDetailsList));
    }

    @DeleteMapping("/api/v1/deleteallagentsalesdata")
    public void deleteAllAgentSalesDetails() {
        agentSalesDataDB.deleteAll();
    }
}
