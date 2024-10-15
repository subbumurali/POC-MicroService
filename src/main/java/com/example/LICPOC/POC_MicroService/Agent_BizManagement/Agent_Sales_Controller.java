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
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
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

    @GetMapping("/api/v1/searchagenttransaction/{agent_id1}/{agent_id2}")
    public List<ResponseAgentTransactionDTO> searchTransactionData(@PathVariable String agent_id1,
                                                                   @PathVariable String agent_id2){

            MatchOperation matchOperation1 = match(new Criteria("agent_id").in(agent_id1,agent_id2));
        //MatchOperation matchOperation1 = Aggregation.match(new Criteria("agent_id").in("agent001","agent002"));

        //MatchOperation matchOperation2 = match(new Criteria("current_year").is(true));
        GroupOperation groupOperation1 = Aggregation.group("agent_id").count().as("policyCount");

        Aggregation myAggregation = Aggregation.newAggregation(Agent_Sales_Details.class, matchOperation1, groupOperation1);

        AggregationResults<ResponseAgentTransactionDTO> aggregationResults = agentSalesDataMongoTemplate.aggregate(myAggregation, "agent_transaction_data", ResponseAgentTransactionDTO.class);

        System.out.println("DTO Result: " + aggregationResults.getMappedResults());
        return aggregationResults.getMappedResults();

        /**
        AggregateIterable<Document> agentSalesDataResult = agentSalesDataCollection.aggregate(Arrays.asList(new Document("$search",
                        new Document("text",
                                new Document("query",first_agent)
                                        .append("path", Arrays.asList("agent_id")))),
                new Document("$sort",
                        new Document("start_date", 1L))));
        //new Document("$limit", 5L)));
        System.out.println("Agent data retrieved: " + agentSalesDataResult);
        System.out.println("Agent data retrieved; string: " + agentSalesDataResult.toString());

        agentSalesDataResult.forEach(doc -> agentSalesDetailsList.add(agentSalesDataConverter.read(Agent_Sales_Details.class,doc)));

        System.out.println("Agent data retrieved: " + agentSalesDetailsList);
        System.out.println("Agent data retrieved; string: " + agentSalesDetailsList.toString());

        return agentSalesDetailsList;

**/








        //return new ArrayList<Agent_Sales_Details>(agentSalesDataDB).findAllById(first_agent);



        //Query agentSalesDataQuery = new Query();
        //agentSalesDataQuery.addCriteria(Criteria.where("agent_id").in("abcd","xcjdd"));
        //agentSalesDataQuery.addCriteria(Criteria.where("agent_id").is(second_agent));
        //Map<String, Object> agentSalesAccumulatedData = new HashMap<String, Object>();

        //MatchOperation matchOperation1 = match(new Criteria("agent_id").is(first_agent));
        //MatchOperation matchOperation1 = Aggregation.match(new Criteria("agent_id").in("agent001","agent002"));

        //MatchOperation matchOperation2 = match(new Criteria("current_year").is(true));
        //GroupOperation groupOperation1 = Aggregation.group("agent_id").count().as("NumberOfPolicies");

        //Aggregation myAggregation = Aggregation.newAggregation(Agent_Sales_Details.class, matchOperation1, groupOperation1);

        //AggregationResults<ResponseAgentTransactionDTO> aggregationResults = agentSalesDataMongoTemplate.aggregate(myAggregation, "agent_transaction_data", ResponseAgentTransactionDTO.class);

        //return aggregationResults.getMappedResults();

        //return null;
    }

    /***
     @GetMapping("/api/v1/searchagenttransaction/") public List<Agent_Sales_Details> searchAgentSalesTransaction(@PathParam("first_agent") String first_agent,
     @PathParam("second_agent") String second_agent)
     {
     Query agentSalesDataQuery = new Query();
     agentSalesDataQuery.addCriteria(Criteria.where("agent_id").in(first_agent,second_agent));
     //agentSalesDataQuery.addCriteria(Criteria.where("agent_id").is(second_agent));

     List<Agent_Sales_Details> agentSalesDetails = agentSalesDataMongoTemplate.find(agentSalesDataQuery, Agent_Sales_Details.class);
     return agentSalesDetails;
     }
     ***/

    @GetMapping("/api/v1/getagentsalesdata/{agent_id}")
    public List<Agent_Sales_Details> getAgentSalesDetails(@PathVariable String agent_id) {
        List<Agent_Sales_Details> agentSalesDetailsList = new ArrayList<Agent_Sales_Details>();
        agentSalesDetailsList.add(agentSalesDataDB.findById(agent_id).get());
        return agentSalesDetailsList;
    }

    @PostMapping("/api/v1/addagentsalesdata")
    public List<Agent_Sales_Details> addAgentSalesData(@RequestBody List<Agent_Sales_Details> agentSalesDetailsList) {
        return new ArrayList<>(agentSalesDataDB.saveAll(agentSalesDetailsList));
    }

    @DeleteMapping("/api/v1/deleteallagentsalesdata")
    public void deleteAllAgentSalesDetails() {
        agentSalesDataDB.deleteAll();
    }
}
