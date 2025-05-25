package acessa.PriceComparator;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin //so it`s callable from frontend
@RestController
@RequestMapping("/api/basket")
public class BasketController {

	private final CsvLoader loader;
	private final PriceService priceService;
	
	public BasketController(CsvLoader loader, PriceService priceService) {
		this.loader = loader;
		this.priceService = priceService;
	}
	
	@PostMapping("/optimize")
	public List<OptimizedBasketItem> optimizeBasket(@RequestBody BasketRequest request){
		List<PriceRecord> prices = loader.getPriceRecords();
		
		return request.getBasket().stream()
				.map(productName -> prices.stream()
					.filter(p -> p.getProduct().getProduct_name().equalsIgnoreCase(productName))
					.min(Comparator.comparing(p -> priceService.getEffectivePrice(p)))
					.map(p -> new OptimizedBasketItem(
							productName,
							p.getStore(),
							priceService.getEffectivePrice(p)
							))
					.orElse(new OptimizedBasketItem(productName, "Not found", 0.0f))
				)
				.collect(Collectors.toList());	
	}
}
