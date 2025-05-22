package acessa.PriceComparator;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Product {
	private String product_id;
	private String product_name;
	private String product_category;
	private String brand;
	private double package_quantity;
	private String package_unit;
}

