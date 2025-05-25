package accesa.PriceComparator.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import accesa.PriceComparator.csv.CsvLoader;
import accesa.PriceComparator.model.PriceAlert;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final CsvLoader csvLoader;
    private final List<PriceAlert> alerts = new ArrayList<>();

    public AlertController(CsvLoader csvLoader) {
        this.csvLoader = csvLoader;
    }

 // POST /api/alerts
    @PostMapping
    public String addAlert(@RequestBody PriceAlert alert) {
        csvLoader.getAlerts().add(alert); // use existing list in CsvLoader
        System.out.printf("Alert added: %s ≤ %.2f RON%n", alert.getProductName(), alert.getTargetPrice());
        csvLoader.checkAlerts(); // run alert check immediately
        return "Alert registered.";
    }

    // GET /api/alerts
    @GetMapping
    public List<PriceAlert> getAllAlerts() {
        return csvLoader.getAlerts();
    }
}
