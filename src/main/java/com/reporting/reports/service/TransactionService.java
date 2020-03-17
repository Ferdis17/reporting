package com.reporting.reports.service;

import com.reporting.reports.model.Transaction;
import com.reporting.reports.repository.TransactionRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    private TransactionRepository repository;

    @Override
    public String export(String reportFormat) throws FileNotFoundException, JRException {
        List<Transaction> transactions = (List) repository.findAll();
        String path = "/Users/Ferdis/jasper_reports";
        // load .jrxml file and compile it
        File file = ResourceUtils.getFile("classpath:transactions.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(transactions);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("CreatedBy", "Ferd");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "/transactions.pdf");
        } else {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "/transactions.html");
        }

        return "report was generated in the path: " + path;
    }
}
