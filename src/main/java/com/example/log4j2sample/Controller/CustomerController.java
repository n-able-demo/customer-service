package com.example.log4j2sample.Controller;


import com.example.log4j2sample.Log4jSampleApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/accounts")
@Slf4j
public class CustomerController {


    private static final Logger logger = LogManager.getLogger(Log4jSampleApplication.class);

    @GetMapping("/getAccountDetailsByAccountNo/{accountNo}")
    public String getAccountDetails(@PathVariable String accountNo) {
        String accountDetailsResponse = callAccountDetailsEndpoint(accountNo);
        return accountDetailsResponse;
    }

    private String callAccountDetailsEndpoint(String accountNo) {
        logger.info("Info log Inside Method");
        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "http://localhost:8990/bank/accounts";
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
