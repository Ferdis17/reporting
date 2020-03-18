package com.reporting.reports.serviceTest;


import com.reporting.reports.model.Transaction;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.ui.jasperreports.JasperReportsUtils;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(JUnit4.class)
public class TransactionServiceTest {



    @Test
    public void exportTestFileExistAndReadable() throws Exception {
        //whenever a customer see transactions on UI, he should be be able to export/download/
        SetUp setUp = new SetUp().invoke();
        JasperReport jasperReport = setUp.getJasperReport();
        JRBeanCollectionDataSource dataSource = setUp.getDataSource();
        Map<String, Object> parameters = setUp.getParameters();

        File file = new File("/Users/Ferdis/jasper_reports");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint, file + "/transactions.pdf");

        assertThat(file.exists(), is(true));
        assertTrue("file readable", file.canRead());
    }

    @Test
    public void testRenderAsPdf() throws Exception {
        SetUp setUp = new SetUp().invoke();
        JasperReport jasperReport = setUp.getJasperReport();
        JRBeanCollectionDataSource dataSource = setUp.getDataSource();
        Map<String, Object> parameters = setUp.getParameters();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JasperReportsUtils.renderAsPdf(jasperReport, parameters, dataSource, out);
        byte[] output = out.toByteArray();
        assertPdfOutPutCorrect(output);
    }

    private void assertPdfOutPutCorrect(byte[] output) throws Exception {
        assertTrue("Output length should be greater than 0", (output.length > 0));
        String translated = new String(output, "US-ASCII");
        assertTrue("Output should start with %PDF%", translated.startsWith("%PDF"));
    }

    private class SetUp {
        private JasperReport jasperReport;
        private JRBeanCollectionDataSource dataSource;
        private Map<String, Object> parameters;

        public JasperReport getJasperReport() {
            return jasperReport;
        }

        public JRBeanCollectionDataSource getDataSource() {
            return dataSource;
        }

        public Map<String, Object> getParameters() {
            return parameters;
        }

        public SetUp invoke() throws FileNotFoundException, JRException {
            Transaction transaction1 = new Transaction(new Date(), "investment", "CHECKPAID", new BigDecimal(45000));
            Transaction transaction2 = new Transaction(new Date(), "investment", "CHECKPAID", new BigDecimal(45000));
            List<Transaction> transations = new ArrayList<>(Arrays.asList(transaction1, transaction2));
            String path = "/Users/Ferdis/jasper_reports";
            // load .jrxml file and compile it
            File file = ResourceUtils.getFile("classpath:transactions.jrxml");
            jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            dataSource = new JRBeanCollectionDataSource(transations);

            parameters = new HashMap<>();
            parameters.put("CreatedBy", "Ferdis");
            return this;
        }
    }
}
