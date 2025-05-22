package acessa.PriceComparator;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Product {
	private String productId;
	private String name;
	private String category;
	private String brand;
	private double quality;
	private String unit;
}

