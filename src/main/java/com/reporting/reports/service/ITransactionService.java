package com.reporting.reports.service;

import com.reporting.reports.model.Transaction;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.util.List;

public interface ITransactionService {

    public String export(String reportFormat) throws FileNotFoundException, JRException;
}
