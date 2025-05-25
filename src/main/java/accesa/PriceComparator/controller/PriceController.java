package accesa.PriceComparator.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import accesa.PriceComparator.csv.CsvLoader;
import accesa.PriceComparator.model.PriceRecord;

@RestController
@RequestMapping("/api/prices")
public class PriceController {

	private final CsvLoader csvLoader;
	
	public PriceController(CsvLoader csvLoader) {
		this.csvLoader = csvLoader;
	}
	
	@GetMapping
	public List<PriceRecord> getPricesByProduct(
		@RequestParam(name = "productName", required = false) String productName,
		@RequestParam(required = false) String store,
		@RequestParam(required = false) String category,
		@RequestParam(required = false) String brand,
		@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
		) {
		List<PriceRecord> allPriceRecords = csvLoader.getPriceRecords();

		return allPriceRecords.stream()
		        .filter(r -> productName == null || r.getProduct().getProduct_name().equalsIgnoreCase(productName))
		        .filter(r -> store == null || r.getStore().equalsIgnoreCase(store))
		        .filter(r -> category == null || r.getProduct().getProduct_category().equalsIgnoreCase(category))
		        .filter(r -> brand == null || r.getProduct().getBrand().equalsIgnoreCase(brand))
		        .filter(r -> date == null || r.getDate().isEqual(date))
		        .collect(Collectors.toList());
	}
	@GetMapping("/history")
	public List<PriceRecord> getPriceHistory(
	    @RequestParam(name = "productName") String productName,
	    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
	    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate
	) {
	    List<PriceRecord> allPriceRecords = csvLoader.getPriceRecords();

	    LocalDate from = (fromDate != null) ? fromDate : LocalDate.MIN;
	    LocalDate to = (toDate != null) ? toDate : LocalDate.now();

	    return allPriceRecords.stream()
	        .filter(r -> r.getProduct().getProduct_name().equalsIgnoreCase(productName))
	        .filter(r -> !r.getDate().isBefore(from) && !r.getDate().isAfter(to))
	        .sorted((r1, r2) -> r1.getDate().compareTo(r2.getDate()))
	        .collect(Collectors.toList());
	}
}
