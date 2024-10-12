package com.example.LICPOC.POC_MicroService.Agent_BizManagement.repository;

import com.example.LICPOC.POC_MicroService.Agent_BizManagement.Agent_Sales_Details;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

public interface SearchRepository {
    List<Agent_Sales_Details> findByText(String text);
}
