package com.example.log4j2sample.Controller;


import com.example.log4j2sample.Log4jSampleApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/accounts")
@Slf4j
public class CustomerController {

    @Autowired
    private Environment environment;

    String baseUrl = System.getenv("BASEURL");

    private static final Logger logger = LogManager.getLogger(Log4jSampleApplication.class);

    @GetMapping("/getAccountDetailsByAccountNo/{accountNo}")
    public String getAccountDetails(@PathVariable String accountNo) {
        logger.info("getAccountDetails--");
        String accountDetailsResponse = callAccountDetailsEndpointbyAccountNo(accountNo);
       //return accountDetailsResponse;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Object jsonTree = objectMapper.readTree(accountDetailsResponse);
            ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
            String formattedJson = writer.writeValueAsString(jsonTree);
            return formattedJson;
        } catch (Exception e) {
            e.printStackTrace();
            return "Data not found";
        }
    }

    private String callAccountDetailsEndpointbyAccountNo(String accountNo) {

        logger.info("callAccountDetailsEndpoint--" + accountNo);
        logger.info("baseUrl--" + baseUrl);
        RestTemplate restTemplate = new RestTemplate();
        // String baseUrl = "http://localhost:8990/bank/accounts";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + "/getAccountDetailsByNumber/")
                .path(accountNo);
        logger.info("Full set URL--" + builder.toUriString());
        ResponseEntity<String> response = restTemplate.exchange(
                builder.toUriString(), HttpMethod.GET, null, String.class);
        System.out.println("-------"+response.getStatusCode());
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            logger.error("Oops! Error calling /accountDetails/ endpoint");
            return "Error calling /getAccountDetails/ endpoint";
        }
    }

    private String callAccountDetailsEndpoint(String accountNo) {
        logger.info("Info log Inside Method");
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/getAccountDetails/", HttpMethod.GET, null, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            logger.error("Oops! Error calling /accountDetails/ endpoint");
            return "Error calling /getAccountDetails/ endpoint";
        }
    }


}
