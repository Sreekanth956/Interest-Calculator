package com.app.interestcalculator.IC.service;

import com.app.interestcalculator.IC.model.SimpleInterestRequest;
import com.app.interestcalculator.IC.model.SimpleInterestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Period;
import java.time.temporal.ChronoUnit;

@Service
public class InterestCalculatorServiceImpl implements InterestCalculatorService {

//    @Autowired
//    private SimpleInterestRequest simpleInterestRequest;
//
//
//    public InterestCalculatorServiceImpl(SimpleInterestRequest simpleInterestRequest) {
//        this.simpleInterestRequest = simpleInterestRequest;
//    }

    @Override
    public SimpleInterestResponse calculateInterest(SimpleInterestRequest request) {

        validateRequest(request);

//        long daysBetween = ChronoUnit.DAYS.between(request.getFromDate(), request.getToDate());
//        BigDecimal durationInYears = BigDecimal.valueOf(daysBetween).divide(BigDecimal.valueOf(365), 2, RoundingMode.HALF_UP);
//
        long monthsBetween = ChronoUnit.MONTHS.between(request.getFromDate(), request.getToDate());

        Period period = Period.between(request.getFromDate(), request.getToDate());

        // Extract years, months, and days
        int years = period.getYears();
        int months = period.getMonths();
        int days = period.getDays();

        // Total months from years and months
        int totalMonths = (years * 12) + months;

        // Days per month based on the fromDate's month
        int daysInMonth = request.getFromDate().lengthOfMonth();

        // Convert days to fractional months
        BigDecimal fractionalMonth = BigDecimal.valueOf(days)
                .divide(BigDecimal.valueOf(daysInMonth), 5, RoundingMode.HALF_UP);

        BigDecimal addFractionalMonths = BigDecimal.valueOf(totalMonths).add(fractionalMonth);


        //request.getInterest().multiply(addFractionalMonths.round(new MathContext(2)));
        BigDecimal interestPerMonth = request.getPrincipalAmount().multiply(request.getInterest()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        BigDecimal totalInterest = interestPerMonth.multiply(addFractionalMonths);

        BigDecimal totalPayableAmount = totalInterest.add(request.getPrincipalAmount());

//        long monthsBetween = ChronoUnit.MONTHS.between(
//                fromDate.withDayOfMonth(1),  // Set to the first day of the month
//                toDate.withDayOfMonth(1)    // Set to the first day of the month
//        );

//        BigDecimal interestAmountToBePaid = request.getPrincipalAmount()
//                .multiply(request.getInterest())
//                .multiply(durationInYears)
//                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

//        BigDecimal totalInterestAmount = request.getPrincipalAmount().add(interestAmountToBePaid);

//        long monthsBetween = ChronoUnit.MONTHS.between(
//                request.getFromDate().withDayOfMonth(1),
//                request.getToDate().withDayOfMonth(1)
//        );
//
//        if (monthsBetween <= 0) {
//            throw new IllegalArgumentException("fromDate must be before toDate.");
//        }
//
//        // Step 1: Calculate per month interest
//        BigDecimal perMonthInterest = request.getPrincipalAmount()
//                .multiply(request.getInterest())
//                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
//
//        // Step 2: Calculate total interest amount
//        BigDecimal totalInterestAmount = perMonthInterest.multiply(BigDecimal.valueOf(monthsBetween));

        //return totalInterestAmount;

        SimpleInterestResponse response = new SimpleInterestResponse();
        response.setPrincipalAmount(request.getPrincipalAmount());
        response.setInterest(request.getInterest());
        response.setFromDate(request.getFromDate());
        response.setToDate(request.getToDate());
        response.setInterestPerMonth(interestPerMonth);
        response.setTotalInterestAmount(totalInterest);
        response.setTotalAmountToPay(totalPayableAmount);

        return response;
    }


    private void validateRequest(SimpleInterestRequest request) {
        if (request.getPrincipalAmount() == null || request.getPrincipalAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Principal amount must be greater than zero.");
        }
        if (request.getInterest() == null || request.getInterest().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Interest rate must be greater than zero.");
        }
        if (request.getFromDate() == null || request.getToDate() == null) {
            throw new IllegalArgumentException("Both fromDate and toDate must be provided.");
        }
        if (request.getFromDate().isAfter(request.getToDate())) {
            throw new IllegalArgumentException("fromDate cannot be after toDate.");
        }
    }


}
