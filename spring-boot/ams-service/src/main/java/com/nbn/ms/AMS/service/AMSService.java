package com.nbn.ms.AMS.service;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.nbn.ms.AMS.config.AppConfig;
import com.nbn.ms.AMS.model.AMSRequestDto;
import com.nbn.ms.AMS.model.AMSResponseDto;

import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;


@Service
public class AMSService {

    private static final String BACKEND_A = "backendA";


    @Autowired
    private AppConfig appconfig;
    
    @Autowired
    private RestTemplate restTemplate;
    
    private String URL;
    private String URL1;

    public AMSResponseDto externalConfig(AMSRequestDto amsRequestDto) {
    	
    	System.out.println("Inside postIncident() method ");
    	URL = appconfig.getConnectionURL();
    	URL1 = "http://localhost:32321/IMS/v1/incidents";
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<AMSRequestDto> request = new HttpEntity<>(amsRequestDto, headers);

       // return restTemplate.exchange(uRL, requestMethod, request, stringClass);
        System.out.println(appconfig.getConnectionURL());
        System.out.println(appconfig.getValue2());
        System.out.println(appconfig.getValue3());
        
        AMSResponseDto response = new AMSResponseDto();
        try {
    	  response = restTemplate.exchange(
    			URL,
    			HttpMethod.POST,
    			request,
    			AMSResponseDto.class
    		).getBody();
    } catch (ResourceAccessException e) {
    	System.out.println("In exception "+e);
    	response.setIncidentId(null);
    	response.setStatus("Connection-error");
    	throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "ConnectionException");
    } catch (Exception e) {
    	System.out.println("Inside Exception ");
    	throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
        return response;
    }
    
    
    @Bulkhead(name = BACKEND_A)
    @Retry(name = BACKEND_A)
    @CircuitBreaker(name = BACKEND_A, fallbackMethod = "fallback")
    public AMSResponseDto postIncident(AMSRequestDto amsRequestDto) {
    	
    	System.out.println("Inside postIncident() method ");
    	URL = appconfig.getConnectionURL();
    	URL1 = "http://localhost:32321/IMS/v1/incidents";
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<AMSRequestDto> request = new HttpEntity<>(amsRequestDto, headers);

       // return restTemplate.exchange(uRL, requestMethod, request, stringClass);
        System.out.println(appconfig.getConnectionURL());
        System.out.println(appconfig.getValue2());
        System.out.println(appconfig.getValue3());
        
        AMSResponseDto response = new AMSResponseDto();
        try {
    	  response = restTemplate.exchange(
    			URL1,
    			HttpMethod.POST,
    			request,
    			AMSResponseDto.class
    		).getBody();
    } catch (Exception e) {
    	System.out.println("Inside Exception ");
    	throw new HttpServerErrorException(HttpStatus.GATEWAY_TIMEOUT, "This is a remote exception");
    }
        return response;
    }
    
    
	private AMSResponseDto fallback(BulkheadFullException ex) {
        System.out.println(LocalTime.now() +"  "+ Thread.currentThread().getId()+ " Bulkhead Fallback  Executed " + ex.getMessage());
        AMSResponseDto response = new AMSResponseDto();
        response.setIncidentId(null);
        response.setStatus("error");
        
        return response; 
    }
	
	private AMSResponseDto fallback(HttpServerErrorException ex) { 
    	System.out.println(LocalTime.now() +"  "+ Thread.currentThread().getId()+ " Inside Circuit Breaker Fallback Method " + ex.getMessage()); 
        AMSResponseDto response = new AMSResponseDto();
        response.setIncidentId(null);
        response.setStatus("exhausted retry-error");
        
        return response;
	}
	
	private AMSResponseDto fallback(Exception ex) { 
    	System.out.println(LocalTime.now() +"  "+ Thread.currentThread().getId()+ " Inside Circuit Breaker Fallback Method " + ex.getMessage()); 
        AMSResponseDto response = new AMSResponseDto();
        response.setIncidentId(null);
        response.setStatus("Ciruit Breaker Open");
        
        return response;
	}
}
