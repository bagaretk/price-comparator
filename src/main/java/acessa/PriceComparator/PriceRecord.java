package acessa.PriceComparator;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class PriceRecord {
	private Product product;
	private LocalDate date;
	private float price;
	private String currency;
	private String store;
}
