package accesa.PriceComparator;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountRecord {
	private String productId;
	private String store;
	private LocalDate from_date;
	private LocalDate to_date;
	private float percentage_of_discount;
}
