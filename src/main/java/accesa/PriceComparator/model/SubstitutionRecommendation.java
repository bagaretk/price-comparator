package accesa.PriceComparator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubstitutionRecommendation {
	private String productName;
	private String store;
	private float unitPrice;
}
