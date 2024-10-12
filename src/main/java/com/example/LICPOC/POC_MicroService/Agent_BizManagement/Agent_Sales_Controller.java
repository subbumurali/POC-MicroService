package com.example.LICPOC.POC_MicroService.Agent_BizManagement;

import com.example.LICPOC.POC_MicroService.Agent_BizManagement.repository.AgentSalesDataDB;
import com.example.LICPOC.POC_MicroService.Agent_BizManagement.repository.SearchRepository;
import com.example.LICPOC.POC_MicroService.Agent_BizManagement.repository.SearchRepositoryImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Agent_Sales_Controller {
    @Autowired
    AgentSalesDataDB agentSalesDataDB;

    @Autowired
    SearchRepository searchRepository;

    @GetMapping("/api/v1/searchagentdata/{text}")
    public List<Agent_Sales_Details> search(@PathVariable String text)
    {
        return searchRepository.findByText(text);
    }

    @GetMapping("/api/v1/getagentsalesdata/{agent_id}")
    public Agent_Sales_Details getAgentSalesDetails(@PathVariable String agent_id){
        return agentSalesDataDB.findById(agent_id).get();
    }

    @PostMapping("/api/v1/addagentsalesdata")
    public Agent_Sales_Details addAgentSalesData(@RequestBody Agent_Sales_Details agentSalesDetails) {
        return agentSalesDataDB.save(agentSalesDetails);
    }

    @DeleteMapping("/api/v1/deleteallagentsalesdata")
    public void deleteAllAgentSalesDetails(){
        agentSalesDataDB.deleteAll();
    }
}
