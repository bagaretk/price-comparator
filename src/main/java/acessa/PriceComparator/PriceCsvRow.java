package acessa.PriceComparator;
import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class PriceCsvRow {
	@CsvBindByName private String productId;
	@CsvBindByName private String name;
	@CsvBindByName private String category;
	@CsvBindByName private String brand;
	@CsvBindByName private double quantity;
	@CsvBindByName private String unit;
	@CsvBindByName private double price;
	@CsvBindByName private String currency;
}
