package accesa.PriceComparator.model;

import com.opencsv.bean.CsvBindByName;

import lombok.Data;

@Data
public class DiscountCsvRow {	
	@CsvBindByName private String product_id;
	@CsvBindByName private String product_name;
	@CsvBindByName private String product_category;
	@CsvBindByName private String brand;
	@CsvBindByName private double package_quantity;
	@CsvBindByName private String package_unit;
	@CsvBindByName private String from_date;
	@CsvBindByName private String to_date;
	@CsvBindByName private float percentage_of_discount;
}
