package edu.wctc.salesreporting;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class SaleCalculator {
    private SalesInput in;
    private SalesReport out;
    private ShippingPolicy ship;

    private List<Sale> salesList = new ArrayList<>();
    Scanner keyboard = new Scanner(System.in);

    public SaleCalculator(SalesInput in, SalesReport out, ShippingPolicy ship) {
        this.in = in;
        this.out = out;
        this.ship = ship;
        System.out.println("Report created");
    }

    public void prepareReport() {
        salesList = in.getSales();
        boolean freeShipping = checkFreeShipping();

        if (!freeShipping) {
            for (Sale sale : salesList) {
                sale.setShipping(ship.getShippingCost(sale));
            }
        }
        if (checkDetailReport()) {
            out.generateDetailReport(salesList);
        }
        else {
            List<Sale> summarySales;
            summarySales = summaryMath();
            out.generateSummaryReport(summarySales);
        }
    }

    public List<Sale> summaryMath() {
        List<Sale> summarySales = new ArrayList<>();
        for (Sale sale : salesList) {
            Sale tempSale = new Sale();
            tempSale.setCountry(sale.getCountry());
            for (Sale sale1 : salesList) {
                if (sale1.getCountry().equals(tempSale.getCountry())) {
                    tempSale.addToSaleAmount(sale1.getSaleAmount());
                    tempSale.addToTax(sale1.getTax());
                    tempSale.addToShipping(sale1.getShipping());
                }
            }
            if (summarySales.isEmpty())
                summarySales.add(tempSale);
            boolean isUnique = true;
            for (Sale sale1 : summarySales) {
                if (sale1.getCountry().equals(tempSale.getCountry())) {
                    isUnique = false;
                    break;
                }
            }
            if (isUnique) {
                summarySales.add(tempSale);
            }
        }
        return summarySales;
    }

    public boolean checkFreeShipping() {
        System.out.println("1) Free shipping");
        System.out.println("2) Flat-rate domestic shipping");
        return keyboard.nextLine().equals("1");
    }

    public boolean checkDetailReport() {
        System.out.println("1) Sales Detail Report");
        System.out.println("2) Sales Summary Report");
        return keyboard.nextLine().equals("1");
    }


}
