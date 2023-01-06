package com.springhow.examples.springboot.pdfdemo;


import com.springhow.examples.springboot.pdfdemo.pojo.Account;
import com.springhow.examples.springboot.pdfdemo.pojo.Address;
import com.springhow.examples.springboot.pdfdemo.pojo.Instrument;
import com.springhow.examples.springboot.pdfdemo.pojo.BeursOrder;
import com.springhow.examples.springboot.pdfdemo.pojo.Payment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BeursOrderHelper {

    public static BeursOrder getOrder() {
        BeursOrder order = new BeursOrder();
        order.setOrderId(1234);
        order.setDate("2023-01-09");
        Address address = new Address();
        address.setCity("Brugge");
        address.setStreet("2897  Grote Markt");
        address.setZipCode("8000");
        address.setState("West Vlaanderen");
        Account account = new Account();
        account.setPhoneNumber("050-312609");
        account.setEmail("klant1@temporary-mail.net");
        account.setName("Mr. Temp A rary");
        account.setAddress(address);
        order.setAccount(account);

        List<Instrument> instruments = new ArrayList<>();
        order.setInstruments(instruments);
        Instrument item1 = new Instrument();
        item1.setName("Colruyt");
        item1.setIsinCode("BE0974256852");
        item1.setCurrentPrice(BigDecimal.valueOf(22.99));
        item1.setHighest(BigDecimal.valueOf(29.89));
        item1.setLowest(BigDecimal.valueOf(8.05));
        item1.setQuantity(20500);
        item1.setMarket("BRU");
        item1.setShortName("COLR.BL");
        instruments.add(item1);

        Payment payment = new Payment();
        BigDecimal totalPrice = instruments.stream().map(item -> item.getCurrentPrice().multiply(BigDecimal.valueOf(item.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
        payment.setAmount(totalPrice);
        payment.setCardNumber("4105789111");
        payment.setCvv("123");
        payment.setMonth("04");
        payment.setYear("2030");
        order.setPayment(payment);
        return order;
    }
}
