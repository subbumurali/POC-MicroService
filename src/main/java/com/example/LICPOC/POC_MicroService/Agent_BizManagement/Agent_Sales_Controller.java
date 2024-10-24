package com.example.LICPOC.POC_MicroService.Agent_BizManagement;

import com.example.LICPOC.POC_MicroService.Agent_BizManagement.repository.*;
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

    @GetMapping("/api/v1/agentsalesnewbusiness/{agent_id}/{cur_year}")
    public List<ResponseAgentTransactionDTO> searchTransactionNewBusiness(@PathVariable String agent_id,
                                                                          @PathVariable Boolean cur_year) {
        if (cur_year) {
            TypedAggregation<Agent_Sales_Details> agentSalesDetailsTypedAggregation =
                    newAggregation(Agent_Sales_Details.class, match(new Criteria("agent_id").in(agent_id)),
                            match(new Criteria("current_year").is(true)),
                            group("premium")
                                    .count().as("policyCount")
                                    .sum("pol_value").as("totpol")
                                    .sum("premium_amt").as("totprem"),
                                project("policyCount","totpol","totprem")
                                .and("totpol").divide("policyCount").as("ticketsize"));

            AggregationResults<ResponseAgentTransactionDTO> aggregationResults =
                    agentSalesDataMongoTemplate.aggregate(agentSalesDetailsTypedAggregation, ResponseAgentTransactionDTO.class);
            return aggregationResults.getMappedResults();
        } else {
            TypedAggregation<Agent_Sales_Details> agentSalesDetailsTypedAggregation =
                    newAggregation(Agent_Sales_Details.class, match(new Criteria("agent_id").in(agent_id)),
                            match(new Criteria("current_year").is(false)),
                            group("premium")
                                    .count().as("policyCount")
                                    .sum("pol_value").as("totpol")
                                    .sum("premium_amt").as("totprem"),
                            project("policyCount","totpol","totprem")
                            .and("totpol").divide("policyCount").as("ticketsize"));

            AggregationResults<ResponseAgentTransactionDTO> aggregationResults =
                    agentSalesDataMongoTemplate.aggregate(agentSalesDetailsTypedAggregation, ResponseAgentTransactionDTO.class);
            return aggregationResults.getMappedResults();
        }

    }

    @GetMapping("/api/v1/agentsalesrenewalbusiness/{agent_id}/{cur_year}")
    public List<ResponseAgentTransactionDTO> searchTransactionRenewalBusiness(@PathVariable String agent_id,
                                                                              @PathVariable Boolean cur_year) {

        if (cur_year) {
            TypedAggregation<Agent_Sales_Details> agentSalesDetailsTypedAggregation =
                    newAggregation(Agent_Sales_Details.class, match(new Criteria("agent_id").in(agent_id)),
                            match(new Criteria("current_year").is(true)),
                            group("premium")
                                    .count().as("policyCount")
                                    .sum("pol_value").as("totpol")
                                    .sum("premium_amt").as("totprem"));
            AggregationResults<ResponseAgentTransactionDTO> aggregationResults =
                    agentSalesDataMongoTemplate.aggregate(agentSalesDetailsTypedAggregation, ResponseAgentTransactionDTO.class);
            return aggregationResults.getMappedResults();
        } else {
            TypedAggregation<Agent_Sales_Details> agentSalesDetailsTypedAggregation =
                    newAggregation(Agent_Sales_Details.class, match(new Criteria("agent_id").in(agent_id)),
                            match(new Criteria("current_year").is(false)),
                            group("premium")
                                    .count().as("policyCount")
                                    .sum("pol_value").as("totpol")
                                    .sum("premium_amt").as("totprem"));
            AggregationResults<ResponseAgentTransactionDTO> aggregationResults =
                    agentSalesDataMongoTemplate.aggregate(agentSalesDetailsTypedAggregation, ResponseAgentTransactionDTO.class);
            return aggregationResults.getMappedResults();
        }
    }

    @GetMapping("/api/v1/agentsalesperformancecomparision/{agent_id}")
    public List<ResponseAgentPerformanceDTO> searchTransactionPerformanceComparision(@PathVariable String agent_id) {
            TypedAggregation<Agent_Sales_Details> agentSalesDetailsTypedAggregation =
                    newAggregation(Agent_Sales_Details.class, match(new Criteria("agent_id").in(agent_id)),
                            group("current_year")
                                    .count().as("policyCount")
                                    .sum("pol_value").as("totpol")
                                    .sum("premium_amt").as("totprem"),
                            project("policyCount","totpol","totprem")
                                    .and("totpol").divide("policyCount").as("ticketsize"));

            AggregationResults<ResponseAgentPerformanceDTO> aggregationResults =
                    agentSalesDataMongoTemplate.aggregate(agentSalesDetailsTypedAggregation, ResponseAgentPerformanceDTO.class);
            return aggregationResults.getMappedResults();
    }

    @GetMapping("/api/v1/agentsalespeerperformancecomparision/{agent_id1}/{agent_id2}")
    public List<ResponseAgentPeerPerformanceDTO> searchTransactionPeerPerformanceComparision(@PathVariable String agent_id1,
                                                                                             @PathVariable String agent_id2) {
        TypedAggregation<Agent_Sales_Details> agentSalesDetailsTypedAggregation =
                newAggregation(Agent_Sales_Details.class, match(new Criteria("agent_id").in(agent_id1,agent_id2)),
                                                          match(new Criteria("current_year").is(true)),
                        group("agent_id")
                                .count().as("policyCount")
                                .sum("pol_value").as("totpol")
                                .sum("premium_amt").as("totprem"),
                        project("policyCount","totpol","totprem")
                                .and("totpol").divide("policyCount").as("ticketsize"));

        AggregationResults<ResponseAgentPeerPerformanceDTO> aggregationResults =
                agentSalesDataMongoTemplate.aggregate(agentSalesDetailsTypedAggregation, ResponseAgentPeerPerformanceDTO.class);
        return aggregationResults.getMappedResults();
    }

    @GetMapping("/api/v1/agentoverviewofticketsize/{agent_id1}/{agent_id2}")
    public List<ResponseAgentOverviewOfTicketSize> agentOverviewOfTicketSize(@PathVariable String agent_id1,
                                                                                             @PathVariable String agent_id2) {
        TypedAggregation<Agent_Sales_Details> agentSalesDetailsTypedAggregation =
                newAggregation(Agent_Sales_Details.class, match(new Criteria("agent_id").in(agent_id1,agent_id2)),
                        group("agent_id","current_year","premium","plan_type","quarter")
                                .count().as("policyCount")
                                .sum("pol_value").as("totpol")
                                .sum("premium_amt").as("totprem"),
                                 project("id","agent_id","current_year","premium","plan_type","quarter","ticketsize")
                                .and("totpol").divide("policyCount").as("ticketsize"));

        AggregationResults<ResponseAgentOverviewOfTicketSize> aggregationResults =
                agentSalesDataMongoTemplate.aggregate(agentSalesDetailsTypedAggregation, ResponseAgentOverviewOfTicketSize.class);
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
