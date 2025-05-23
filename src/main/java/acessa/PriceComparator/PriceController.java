package acessa.PriceComparator;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prices")
public class PriceController {

	private final CsvLoader csvLoader;
	
	public PriceController(CsvLoader csvLoader) {
		this.csvLoader = csvLoader;
	}
	
	@GetMapping
	public List<PriceRecord> getPricesByProduct(
		@RequestParam(name = "productId", required = false) String productId,
		@RequestParam(required = false) String store,
		@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
		) {
		List<PriceRecord> allPriceRecords = csvLoader.getPriceRecords();

		return allPriceRecords.stream()
		        .filter(r -> productId == null || r.getProduct().getProduct_id().equalsIgnoreCase(productId))
		        .filter(r -> store == null || r.getStore().equalsIgnoreCase(store))
		        .filter(r -> date == null || r.getDate().isEqual(date))
		        .collect(Collectors.toList());
	}
}
