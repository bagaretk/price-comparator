package accesa.PriceComparator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PriceAlert {
	private String productName;
	private float targetPrice;
}
