package com.reporting.reports.service;

import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface ITransactionService {

    public String export(String reportFormat) throws FileNotFoundException, JRException, IOException;
}
