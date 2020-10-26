package com.reporting.reports.controller;

import com.reporting.reports.service.ITransactionService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/report")
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    @GetMapping (value = "/report/{format}")
    public String generateTransactionReport(@PathVariable String format) throws IOException, JRException {

       return transactionService.export(format);
    }
}
