package edu.wctc.salesreporting;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.w3c.dom.UserDataHandler;
import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Configuration
@ComponentScan("edu.wctc.salesreporting")
public class AppConfig {
    @Bean
    public SalesInput salesInput() {
        return new SalesInput() {
            @Override
            public List<Sale> getSales() {
                List<Sale> salesList = new ArrayList<>();
                try {
                    File file = new File("sales.txt");
                    Scanner scanner = new Scanner(file);
                    int i = 0;
                    while (scanner.hasNextLine()) {
                        Sale newSale = new Sale();
                        //String[] data = scanner.nextLine().split(",");
                        Object[] data = scanner.nextLine().split(",");
                        newSale.setCustomer(data[0].toString());
                        newSale.setCountry(data[1].toString());
                        newSale.setSaleAmount(Double.parseDouble(data[2].toString()));
                        newSale.setTax(Double.parseDouble(data[3].toString()));
                        newSale.setShipping(0.00);
                        salesList.add(newSale);
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("An error occurred. File not found.");
                    e.printStackTrace();
                }
                return salesList;
            }
        };
    }

    @Bean
    public SalesReport salesReport() {
        return new SalesReport() {
            @Override
            public void generateDetailReport(List<Sale> salesList) {
                System.out.println("SALES DETAIL REPORT");
                System.out.printf("%-30s", "Customer");
                System.out.printf("%-20s", "Country");
                System.out.printf("%-9s", "Amount");
                System.out.printf("%-8s", "Tax");
                System.out.printf("%-8s", "Shipping");
                System.out.println();
                for (Sale sale : salesList) {
                    System.out.printf("%-30s", sale.getCustomer());
                    System.out.printf("%-20s", sale.getCountry());
                    System.out.printf("%-9s", sale.getSaleAmount());
                    System.out.printf("%-8s", sale.getTax());
                    System.out.printf("%-8.5s", sale.getShipping());;
                    System.out.println();
                }
            }

            @Override
            public void generateSummaryReport(List<Sale> summarySales) {
                System.out.println("SALES SUMMARY REPORT");
                System.out.printf("%-20s", "Country");
                System.out.printf("%-9s", "Amount");
                System.out.printf("%-8s", "Tax");
                System.out.printf("%-8s", "Shipping");
                System.out.println();
                for (Sale sale : summarySales) {
                    System.out.printf("%-20s", sale.getCountry());
                    System.out.printf("%-9s", sale.getSaleAmount());
                    System.out.printf("%-8s", sale.getTax());
                    System.out.printf("%-8.5s", sale.getShipping());
                    System.out.println();
                }
            }
        };
    }

    @Bean
    public ShippingPolicy shippingPolicy() {
        return new ShippingPolicy() {
            @Override
            public double getShippingCost(Sale sale) {
                switch (sale.getCountry()) {
                    case "United States":
                        return 5.99;
                    case "Japan":
                        return 14.50;
                    case "Scotland":
                        return 7.54;
                    case "India":
                        return 11.79;
                    default:
                        return 7.99;
                }
            }
        };
    }
}
