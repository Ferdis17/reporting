package com.reporting.reports.service;

import com.reporting.reports.model.Transaction;
import com.reporting.reports.repository.TransactionRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.export.SimpleCsvExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    private TransactionRepository repository;
    private  String path = "/Users/Ferdis/jasper_reports";

    @Override
    public String export(String reportFormat) throws IOException, JRException {
        List<Transaction> transactions = (List) repository.findAll();

        // load .jrxml file and compile it
        File file = ResourceUtils.getFile("classpath:gabon_covid.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(transactions);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("labId", "LAB005");
        parameters.put("patientId", "Ferd");
        parameters.put("names", "Ferd");
        parameters.put("dob", "Ferd");
        parameters.put("age", "Ferd");
        parameters.put("sex", "Ferd");
        parameters.put("caseType", "Ferd");
        parameters.put("demandeur", "Ferd");
        parameters.put("testingSite", "Ferd");
        parameters.put("diagnostic", "Ferd");
        parameters.put("suivi", "Ferd");
        parameters.put("testingDate", "Ferd");
        parameters.put("labrcvDate", "Ferd");
        parameters.put("technique", "Ferd");
        parameters.put("kit", "Ferd");
        parameters.put("equipment", "Ferd");
        parameters.put("prelevementType", "Ferd");
        parameters.put("geneSearched1", "Ferd");
        parameters.put("geneSearched2", "Ferd");
        parameters.put("valueOfCT1", "Ferd");
        parameters.put("valueOfCT2", "Ferd");
        parameters.put("analysisResult", "Ferd");
        parameters.put("date", "Ferd");
        parameters.put("conclusion", "Ferd");
        parameters.put("doneAt", "Ferd");
        parameters.put("date1", "Ferd");
        parameters.put("responsible", "Ferd");
        parameters.put("logo", new String(Files.readAllBytes(Paths.get("/Users/Ferdis/reports/src/main/resources/logo.jpg"))));

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, (Connection) null);

        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "/results.pdf");
        }
        return "report was generated in the path: " + path;
    }
        private void exportCSV(JasperPrint jasperPrint) throws JRException {
            JRCsvExporter exporter = new JRCsvExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleWriterExporterOutput(path + "/transactions.csv"));
            SimpleCsvExporterConfiguration configuration = new SimpleCsvExporterConfiguration();
            configuration.setFieldDelimiter(",");
            exporter.setConfiguration(configuration);
            exporter.exportReport();
        }

}
