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
        parameters.put("patientId", "MR000056");
        parameters.put("names", "Ferdis Fernado");
        parameters.put("dob", "02/02/1945");
        parameters.put("age", "75");
        parameters.put("sex", "M");
        parameters.put("caseType", "Suspect");
        parameters.put("demandeur", "Dr. Tinga Saint Paul");
        parameters.put("testingSite", "Libre Ville");
        parameters.put("diagnostic", "J0");
        parameters.put("suivi", "J20");
        parameters.put("testingDate", "25/09/2020");
        parameters.put("labrcvDate", "25/09/2020");
        parameters.put("technique", "Magic");
        parameters.put("kit", "Magic");
        parameters.put("equipment", "Spoon");
        parameters.put("prelevementType", "Fluid");
        parameters.put("geneSearched1", "N/A");
        parameters.put("geneSearched2", "N/A");
        parameters.put("valueOfCT1", "67.5");
        parameters.put("valueOfCT2", "104");
        parameters.put("analysisResult", "SARS-Cov-2 detecte");
        parameters.put("date", "26/09/2020");
        parameters.put("conclusion", "u gonna die anyway");
        parameters.put("doneAt", "Libre Ville");
        parameters.put("date1", "26/09/2020");
        parameters.put("responsable", "Dr. Henok Gebremefens");
//        parameters.put("logo", new String(Files.readAllBytes(Paths.get("/Users/Ferdis/reports/src/main/resources/logo.jpg"))));

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
