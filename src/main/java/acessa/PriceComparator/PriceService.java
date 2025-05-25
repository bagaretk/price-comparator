package acessa.PriceComparator;

import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class PriceService {

	private final CsvLoader loader;
	
	public PriceService(CsvLoader loader) {
		this.loader = loader;
	}
	
	public float getEffectivePrice(PriceRecord priceRecord) {
        float base = priceRecord.getPrice();
        String store = priceRecord.getStore();
        String productId = priceRecord.getProduct().getProduct_id();
        LocalDate date = priceRecord.getDate();

        return loader.getDiscountRecords().stream()
                .filter(d -> d.getProductId().equals(productId))
                .filter(d -> d.getStore().equalsIgnoreCase(store))
                .filter(d -> !date.isBefore(d.getFrom_date()) && !date.isAfter(d.getTo_date()))
                .map(d -> base * (1 - d.getPercentage_of_discount() / 100f))
                .findFirst()
                .orElse(base);
    }		
}
