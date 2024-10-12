package com.example.LICPOC.POC_MicroService.Agent_BizManagement.repository;

import com.example.LICPOC.POC_MicroService.Agent_BizManagement.Agent_Sales_Details;
import com.mongodb.client.*;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class SearchRepositoryImplementation implements SearchRepository{

    @Autowired
    MongoClient agentSalesDataClient;

    @Autowired
    MongoConverter agentSalesDataConverter;

    @Override
    public List<Agent_Sales_Details> findByText(String text) {
        final List<Agent_Sales_Details> agentSalesDetailsList = new ArrayList<>();

        MongoDatabase agentDatabase = agentSalesDataClient.getDatabase("lic_poc");
        MongoCollection<Document> agentSalesDataCollection = agentDatabase.getCollection("agent_salesdata");

        AggregateIterable<Document> agentSalesDataResult = agentSalesDataCollection.aggregate(Arrays.asList(new Document("$search",
                new Document("text",
                new Document("query",text)
                .append("path", Arrays.asList("agent_id")))),
                new Document("$sort",
                new Document("start_date", 1L))));
                //new Document("$limit", 5L)));
        agentSalesDataResult.forEach(doc -> agentSalesDetailsList.add(agentSalesDataConverter.read(Agent_Sales_Details.class,doc)));
        return agentSalesDetailsList;
    }
}
