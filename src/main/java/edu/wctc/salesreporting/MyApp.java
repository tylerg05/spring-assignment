package edu.wctc.salesreporting;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MyApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AppConfig.class);
        context.refresh();

        SaleCalculator calc = (SaleCalculator)context.getBean("saleCalculator");
        calc.prepareReport();
    }
}
