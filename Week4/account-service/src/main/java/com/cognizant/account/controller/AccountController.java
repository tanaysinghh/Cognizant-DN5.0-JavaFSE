package com.cognizant.account.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @GetMapping("/{number}")
    public Map<String, Object> getAccount(@PathVariable String number) {
        Map<String, Object> account = new HashMap<>();
        account.put("number", number);
        account.put("type", "savings");
        account.put("balance", 234343);
        return account;
    }
}
