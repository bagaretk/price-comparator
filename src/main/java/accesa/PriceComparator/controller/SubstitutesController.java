package accesa.PriceComparator.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import accesa.PriceComparator.csv.CsvLoader;
import accesa.PriceComparator.model.SubstitutionRecommendation;

@RestController
@RequestMapping("/api/substitutes")
public class SubstitutesController {
	private final CsvLoader csvLoader;
		
	public SubstitutesController(CsvLoader csvLoader) {
		this.csvLoader = csvLoader;
	}
	
	@GetMapping
	public List<SubstitutionRecommendation> getSubstitutes(
		    @RequestParam String productName
		) {
		    return csvLoader.getPriceRecords().stream()
		        .filter(r -> r.getProduct().getProduct_name().toLowerCase().contains(productName.toLowerCase()))
		        .map(r -> {
		            float unitPrice = r.getPrice() / r.getProduct().getPackage_quantity();
		            return new SubstitutionRecommendation(
		                r.getProduct().getProduct_name(),
		                r.getStore(),
		                unitPrice
		            );
		        })
		        .sorted(Comparator.comparing(SubstitutionRecommendation::getUnitPrice))
		        .collect(Collectors.toList());
		}
}
