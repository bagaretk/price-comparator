package accesa.PriceComparator.model;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Product {
	private String product_id;
	private String product_name;
	private String product_category;
	private String brand;
	private float package_quantity;
	private String package_unit;
}

