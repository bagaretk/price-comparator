package acessa.PriceComparator;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prices")
public class PriceController {

	private final CsvLoader csvLoader;
	
	public PriceController(CsvLoader csvLoader) {
		this.csvLoader = csvLoader;
	}
	
	@GetMapping
	public List<PriceRecord> getAllPrices(){
		return csvLoader.getPriceRecords();
	}
}
