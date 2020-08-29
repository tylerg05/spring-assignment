package edu.wctc.salesreporting;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SalesReport {
    void generateDetailReport(List<Sale> salesList);
    void generateSummaryReport(List<Sale> summarySales);
}
