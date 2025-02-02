package com.app.interestcalculator.IC.service;

import com.app.interestcalculator.IC.model.SimpleInterestRequest;
import com.app.interestcalculator.IC.model.SimpleInterestResponse;

public interface InterestCalculatorService {

    public SimpleInterestResponse calculateInterest(SimpleInterestRequest request);

}
