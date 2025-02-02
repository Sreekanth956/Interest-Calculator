package com.app.interestcalculator.IC.controller;


import com.app.interestcalculator.IC.model.SimpleInterestRequest;
import com.app.interestcalculator.IC.model.SimpleInterestResponse;
import com.app.interestcalculator.IC.service.InterestCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/interest")
public class InterestCalculator {

    @Autowired
    private InterestCalculatorService interestCalculatorService;


    @PostMapping("/calculate")
    public ResponseEntity<SimpleInterestResponse> calculateInterest(@RequestBody SimpleInterestRequest request) {
        //SimpleInterestResponse response = new SimpleInterestResponse();
        SimpleInterestResponse simpleInterestResponse = interestCalculatorService.calculateInterest(request);
        return ResponseEntity.ok(simpleInterestResponse);

    }

}
