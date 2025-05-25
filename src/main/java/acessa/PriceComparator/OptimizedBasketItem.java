package acessa.PriceComparator;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OptimizedBasketItem {
	private String productName;
	private String store;
	private float price;
}
