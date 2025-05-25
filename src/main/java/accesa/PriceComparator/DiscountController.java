package accesa.PriceComparator;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/discounts")
public class DiscountController {
	private final CsvLoader loader;
	
	public DiscountController(CsvLoader loader) {
		this.loader = loader;
	}
	
	@GetMapping("/new")
	public List<DiscountRecord> getNewDiscounts(
			@RequestParam(name = "days", defaultValue = "1") int days
	){
		LocalDate threshold = LocalDate.now().minusDays(days);
		
		return loader.getDiscountRecords().stream()
				.filter(d -> d.getFrom_date().isAfter(threshold))
				.collect(Collectors.toList());
	}
	
	@GetMapping("/best")
	public List<DiscountRecord> getsBestDiscounts(
		@RequestParam(defaultValue = "5") int topN
		) {
		return loader.getDiscountRecords().stream()
				.sorted(Comparator.comparing(DiscountRecord::getPercentage_of_discount).reversed())
				.limit(topN)
				.collect(Collectors.toList());
	}
}
