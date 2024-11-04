package com.example.LICPOC.POC_MicroService.Agent_BizManagement;

import com.example.LICPOC.POC_MicroService.Agent_BizManagement.repository.*;
import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.mongodb.client.model.Accumulators.mergeObjects;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.AggregationExpression.from;
import static org.springframework.data.mongodb.core.aggregation.SelectionOperators.First.first;
import static org.springframework.data.mongodb.core.aggregation.SetOperation.set;

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

    @GetMapping("/api/v1/agentsalesnewbusinessdata")
    public List<ResponseAgentNewBusinessDTO> searchTransactionNewBusiness() {
        TypedAggregation<Agent_Sales_Details> agentSalesDetailsTypedAggregation =
                newAggregation(Agent_Sales_Details.class, match(new Criteria("agent_id").in("agent001")
                                .andOperator(Criteria.where("new_policy").is(true))),
                        group("agent_id", "policy_year", "premium", "policy_active")
                                .count().as("policyCount")
                                .sum("pol_value").as("totpol")
                                .sum("premium_amt").as("totprem"),
                        //.sum("renewal_amt").as("totrenewal"),
                        project("agent_id", "policy_year", "premium", "policyCount", "policy_active",
                                "totpol", "totprem")
                                .and("totpol").divide("policyCount").as("ticketsize"),
                        sort(Sort.Direction.ASC, "agent_id", "policy_year", "premium", "newpolicy"));

        AggregationResults<ResponseAgentNewBusinessDTO> aggregationResults =
                agentSalesDataMongoTemplate.aggregate(agentSalesDetailsTypedAggregation, ResponseAgentNewBusinessDTO.class);

        return aggregationResults.getMappedResults();
    }

    @GetMapping("/api/v1/agentsalesrenewalbusinessdata")
    public List<ResponseAgentRenewalBusinessDTO> searchTransactionRenewalBusiness() {
        TypedAggregation<Agent_Sales_Details> agentSalesDetailsTypedAggregation =
                newAggregation(Agent_Sales_Details.class, match(new Criteria("agent_id").in("agent001")
                                .andOperator(Criteria.where("new_policy").is(false))),
                        group("agent_id", "policy_year", "premium", "policy_active")
                                .count().as("policyCount")
                                .sum("pol_value").as("totpol")
                                //.sum("premium_amt").as("totprem")
                                .sum("renewal_amt").as("totrenewal"),
                        project("agent_id", "policy_year", "premium", "policyCount", "policy_active",
                                "totpol", "totprem", "totrenewal")
                                .and("totpol").divide("policyCount").as("ticketsize"),
                        sort(Sort.Direction.ASC, "agent_id", "policy_year", "premium", "newpolicy"));

        AggregationResults<ResponseAgentRenewalBusinessDTO> aggregationResults =
                agentSalesDataMongoTemplate.aggregate(agentSalesDetailsTypedAggregation, ResponseAgentRenewalBusinessDTO.class);

        return aggregationResults.getMappedResults();
    }

    @GetMapping("/api/v1/agentsalesnewbusinessproductwise/{product_id}/{quarter_id}")
    public List<ResponseAgentNewProductWiseQuarterDTO> searchTransactionNewBusinessProductwise(
            @PathVariable String product_id,
            @PathVariable String quarter_id) {
        TypedAggregation<Agent_Sales_Details> agentSalesNewBusinessDetailsTypedAggregation =
                newAggregation(Agent_Sales_Details.class,
                        match(new Criteria("agent_id").is("agent001")),
                        match(new Criteria("new_policy").is(true)),
                        match(new Criteria("product_id").is(product_id)),
                        match(new Criteria("quarter").is(Integer.valueOf(quarter_id))),
                        group("agent_id", "policy_year", "product_id", "quarter", "premium", "policy_active")
                                .count().as("policyCount")
                                .sum("pol_value").as("totpol")
                                .sum("premium_amt").as("totprem"),
                        //.sum("renewal_amt").as("totrenewal"),
                        project("agent_id", "policy_year", "product_id", "quarter", "premium", "policy_active",
                                "policyCount", "totpol", "totprem")
                                .and("totpol").divide("policyCount").as("ticketsize"),
                        sort(Sort.Direction.ASC, "agent_id", "policy_year", "premium", "new_policy"));

        AggregationResults<ResponseAgentNewProductWiseQuarterDTO> aggregationResults =
                agentSalesDataMongoTemplate.aggregate(agentSalesNewBusinessDetailsTypedAggregation, ResponseAgentNewProductWiseQuarterDTO.class);

        return aggregationResults.getMappedResults();
    }

    @GetMapping("/api/v1/agentsalesrenewalbusinessproductwise/{product_id}/{quarter_id}")
    public List<ResponseAgentRenewalProductWiseQuarterDTO> searchTransactionRenewalBusinessProductwise(
            @PathVariable String product_id,
            @PathVariable String quarter_id) {
        TypedAggregation<Agent_Sales_Details> agentRenewalDetailsTypedAggregation =
                newAggregation(Agent_Sales_Details.class,
                        match(new Criteria("agent_id").is("agent001")),
                        match(new Criteria("new_policy").is(false)),
                        match(new Criteria("product_id").is(product_id)),
                        match(new Criteria("quarter").is(Integer.valueOf(quarter_id))),
                        group("agent_id", "policy_year", "product_id", "quarter", "premium", "policy_active")
                                .count().as("policyCount")
                                .sum("pol_value").as("totpol")
                                .sum("renewal_amt").as("totrenewal"),
                        //.sum("renewal_amt").as("totrenewal"),
                        project("agent_id", "policy_year", "product_id", "quarter", "premium", "policy_active",
                                "policyCount", "totpol", "totrenewal")
                                .and("totpol").divide("policyCount").as("ticketsize"),
                        sort(Sort.Direction.ASC, "agent_id", "policy_year", "premium", "new_policy"));

        AggregationResults<ResponseAgentRenewalProductWiseQuarterDTO> aggregationResults =
                agentSalesDataMongoTemplate.aggregate(agentRenewalDetailsTypedAggregation, ResponseAgentRenewalProductWiseQuarterDTO.class);

        return aggregationResults.getMappedResults();
    }

    @GetMapping("/api/v1/agentperformance")
    public List<ResponseAgentNOPTargetDTO> agentPerformance() {
        TypedAggregation<Agent_Sales_Details> agentSalesPerformanceTypedAggregation =
                newAggregation(Agent_Sales_Details.class,
                        match(new Criteria("agent_id").in("agent001", "agent002")),
                        match(new Criteria("policy_year").is("2024")),
                        group("agent_id", "premium", "product_id")
                                .count().as("policyCount")
                                .sum("pol_value").as("totpol"),
                        project("agent_id", "product_id", "premium", "policyCount")
                                .and("totpol").divide("policyCount").as("ticketsize"),
                        sort(Sort.Direction.ASC, "agent_id","premium","product_id"));

        AggregationResults<ResponseAgentNOPTargetDTO> aggregationResults =
                agentSalesDataMongoTemplate.aggregate(agentSalesPerformanceTypedAggregation, ResponseAgentNOPTargetDTO.class);

        return aggregationResults.getMappedResults();
    }

    @GetMapping("/api/v1/agenttargetperformance")
    public List<ResponseAgentPeerTargetDTO> agentTargetPerformance() {
        TypedAggregation<Agent_Sales_Details> agentSalesTargetTypedAggregation =
                newAggregation(Agent_Sales_Details.class,
                        match(new Criteria("agent_id").in("agent001")),
                        match(new Criteria("policy_year").is("2024")),
                        lookup("sales_target", "product_id", "product_id", "prdTargetMapping"),
                        group("agent_id", "premium", "product_id","prdTargetMapping")
                                .count().as("policyCount")
                                .sum("pol_value").as("totpol"),
                        project("agent_id", "product_id", "premium", "policyCount", "prdTargetMapping")
                                .and("totpol").divide("policyCount").as("ticketsize"));

        AggregationResults<ResponseAgentPeerTargetDTO> aggregationResults =
                agentSalesDataMongoTemplate.aggregate(agentSalesTargetTypedAggregation, ResponseAgentPeerTargetDTO.class);

        return aggregationResults.getMappedResults();
    }


    @DeleteMapping("/api/v1/deleteallagentsalesdata")
    public void deleteAllAgentSalesDetails() {
        agentSalesDataDB.deleteAll();
    }


    @PostMapping("/api/v1/addagentsalestransactiondata")
    public List<Agent_Sales_Details> addAgentSalesData
            (@RequestBody List<Agent_Sales_Details> agentSalesDetailsList) {
        return new ArrayList<>(agentSalesDataDB.saveAll(agentSalesDetailsList));
    }

}


/***
@GetMapping("/api/v1/agentsalesrenewalbusinessproductwise/{product_id}/{quarter_id}")
public List<ResponseAgentNewBusinessDTO> searchTransactionRenewalBusinessProductwise(
        @PathVariable String product_id,
        @PathVariable String quarter_id) {
    TypedAggregation<Agent_Sales_Details> agentSalesDetailsTypedAggregation =
            newAggregation(Agent_Sales_Details.class, match(new Criteria("agent_id").in("agent001")
                            .andOperator(Criteria.where("new_policy").is(false))
                            .andOperator(Criteria.where("product_id").is("product_id"))
                            .andOperator(Criteria.where("quarter").is("quarter_id"))),
                    group("agent_id", "policy_year", "premium", "policy_active")
                            .count().as("policyCount")
                            .sum("pol_value").as("totpol")
                            .sum("premium_amt").as("totprem"),
                    project("agent_id", "policy_year", "premium", "policyCount", "policy_active",
                            "totpol", "totprem")
                            .and("totpol").divide("policyCount").as("ticketsize"),
                    sort(Sort.Direction.ASC, "agent_id", "policy_year", "premium", "newpolicy"));

    AggregationResults<ResponseAgentNewBusinessDTO> aggregationResults =
            agentSalesDataMongoTemplate.aggregate(agentSalesDetailsTypedAggregation, ResponseAgentNewBusinessDTO.class);

    return aggregationResults.getMappedResults();
}
***/


    /***
     @GetMapping("/api/v1/agentsalesnewbusiness/{agent_id}/{cur_year}") public List<ResponseAgentTransactionDTO> searchTransactionNewBusiness(@PathVariable String agent_id,
     @PathVariable Boolean cur_year) {
     if (cur_year) {
     TypedAggregation<Agent_Sales_Details> agentSalesDetailsTypedAggregation =
     newAggregation(Agent_Sales_Details.class, match(new Criteria("agent_id").in(agent_id)),
     match(new Criteria("current_year").is(true)),
     group("premium")
     .count().as("policyCount")
     .sum("pol_value").as("totpol")
     .sum("premium_amt").as("totprem"),
     project("policyCount", "totpol", "totprem")
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
     project("policyCount", "totpol", "totprem")
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
                        project("policyCount", "totpol", "totprem")
                                .and("totpol").divide("policyCount").as("ticketsize"));

        AggregationResults<ResponseAgentPerformanceDTO> aggregationResults =
                agentSalesDataMongoTemplate.aggregate(agentSalesDetailsTypedAggregation, ResponseAgentPerformanceDTO.class);
        return aggregationResults.getMappedResults();
    }

    @GetMapping("/api/v1/agentsalespeerperformancecomparision/{agent_id1}/{agent_id2}")
    public List<ResponseAgentPeerPerformanceDTO> searchTransactionPeerPerformanceComparision(@PathVariable String
                                                                                                     agent_id1,
                                                                                             @PathVariable String agent_id2) {
        TypedAggregation<Agent_Sales_Details> agentSalesDetailsTypedAggregation =
                newAggregation(Agent_Sales_Details.class, match(new Criteria("agent_id").in(agent_id1, agent_id2)),
                        match(new Criteria("current_year").is(true)),
                        group("agent_id")
                                .count().as("policyCount")
                                .sum("pol_value").as("totpol")
                                .sum("premium_amt").as("totprem"),
                        project("policyCount", "totpol", "totprem")
                                .and("totpol").divide("policyCount").as("ticketsize"));

        AggregationResults<ResponseAgentPeerPerformanceDTO> aggregationResults =
                agentSalesDataMongoTemplate.aggregate(agentSalesDetailsTypedAggregation, ResponseAgentPeerPerformanceDTO.class);
        return aggregationResults.getMappedResults();
    }

    @GetMapping("/api/v1/agentoverviewofticketsize/{agent_id1}/{agent_id2}")
    public List<ResponseAgentOverviewOfTicketSize> agentOverviewOfTicketSize(@PathVariable String agent_id1,
                                                                             @PathVariable String agent_id2) {
        TypedAggregation<Agent_Sales_Details> agentSalesDetailsTypedAggregation =
                newAggregation(Agent_Sales_Details.class, match(new Criteria("agent_id").in(agent_id1, agent_id2)),
                        group("agent_id", "current_year", "premium", "plan_type", "quarter")
                                .count().as("policyCount")
                                .sum("pol_value").as("totpol")
                                .sum("premium_amt").as("totprem"),
                        project("id", "agent_id", "current_year", "premium", "plan_type", "quarter", "ticketsize")
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
    public List<Agent_Sales_Details> addAgentSalesData
            (@RequestBody List<Agent_Sales_Details> agentSalesDetailsList) {
        return new ArrayList<>(agentSalesDataDB.saveAll(agentSalesDetailsList));
    }

    @DeleteMapping("/api/v1/deleteallagentsalesdata")
    public void deleteAllAgentSalesDetails() {
        agentSalesDataDB.deleteAll();
    }
}

 @GetMapping("/api/v1/agentsalesproductwisebusiness/{agent_id}/{product_id}/{quarter}")
 public List<ResponseAgentProductWiseQuarterDTO> searchTransactionProductWiseQtrWise(@PathVariable String agent_id,
 @PathVariable String product_id,
 @PathVariable String quarter) {
 TypedAggregation<Agent_Sales_Details> agentSalesDetailsTypedAggregation =
 newAggregation(Agent_Sales_Details.class,
 match(new Criteria("plan_type").in(product_id)),
 match(new Criteria("agent_id").is(agent_id)),
 match(new Criteria("current_year").is(true)),
 match(new Criteria("quarter").is(quarter)),
 group("product_id")
 .count().as("policyCount")
 .sum("pol_value").as("totpol")
 .sum("premium_amt").as("totprem"),
 project("id", "agent_id", "product_id", "policyCount", "totpol", "totprem"));
 AggregationResults<ResponseAgentProductWiseQuarterDTO> aggregationResults =
 agentSalesDataMongoTemplate.aggregate(agentSalesDetailsTypedAggregation, ResponseAgentProductWiseQuarterDTO.class);
 return aggregationResults.getMappedResults();
 }


 @GetMapping("/api/v1/agentsalesnewbusiness")
 public List<ResponseAgentTransactionDTO> searchTransactionNewBusiness() {
 MatchOperation matchOperation_2023 = match(new Criteria("agent_id").is("agent001")
 .andOperator(match(new Criteria("policy_year").is("2023")),
 GroupOperation groupOperation_2023 =
 group("policy_year")
 .count().as("policyCount_2023")
 .sum("pol_value").as("totpol_2023")
 .sum("premium_amt").as("totprem_2023")
 .sum("renewal_amt").as("totrenewal_2023");

 MatchOperation matchOperation_2024 = match(new Criteria("policy_year").is("2024"));
 GroupOperation groupOperation_2024 =
 group("policy_year")
 .count().as("policyCount_2024")
 .sum("pol_value").as("totpol_2024")
 .sum("premium_amt").as("totprem_2024")
 .sum("renewal_amt").as("totrenewal_2024");
 ProjectionOperation projectionOperation =
 project("agent_id", "policy_year", "policyCount_2023", "totpol_2023", "totprem_2023",
 "totrenewal_2023", "policyCount_2024", "totpol_2024", "totprem_2024", "totrenewal_2024")
 .and("totpol_2023").divide("policyCount_2023").as("ticketsize_2023")
 .and("totpol_2024").divide("policyCount_2024").as("ticketsize_2024");

 Aggregation myAggregation = newAggregation(matchOperation_2023, groupOperation_2023, projectionOperation);

 AggregationResults<ResponseAgentTransactionDTO> aggregationResults =
 agentSalesDataMongoTemplate.aggregate(myAggregation, "sales_data", ResponseAgentTransactionDTO.class);

 return aggregationResults.getMappedResults();
 }
***/

