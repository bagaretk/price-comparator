package acessa.PriceComparator;
import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class PriceCsvRow {
	@CsvBindByName private String product_id;
	@CsvBindByName private String product_name;
	@CsvBindByName private String product_category;
	@CsvBindByName private String brand;
	@CsvBindByName private double package_quantity;
	@CsvBindByName private String package_unit;
	@CsvBindByName private double price;
	@CsvBindByName private String currency;
}
