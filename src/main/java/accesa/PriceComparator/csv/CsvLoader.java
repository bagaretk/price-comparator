package accesa.PriceComparator.csv;
import com.opencsv.bean.CsvToBeanBuilder;

import accesa.PriceComparator.model.DiscountCsvRow;
import accesa.PriceComparator.model.DiscountRecord;
import accesa.PriceComparator.model.PriceAlert;
import accesa.PriceComparator.model.PriceCsvRow;
import accesa.PriceComparator.model.PriceRecord;
import accesa.PriceComparator.model.Product;
import jakarta.annotation.PostConstruct;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;


@Component
public class CsvLoader {

	private final List<PriceRecord> priceRecords = new ArrayList<>();
	private final List<DiscountRecord> discountRecords = new ArrayList<>();
	private final List<PriceAlert> alerts = new ArrayList<>();
	
	public void checkAlerts() {
	    for (PriceAlert alert : alerts) {
	        this.getPriceRecords().stream()
	            .filter(p -> p.getProduct().getProduct_name().equalsIgnoreCase(alert.getProductName()))
	            .filter(p -> p.getPrice() <= alert.getTargetPrice())
	            .forEach(p -> System.out.printf("ALERT: %s is now %.2f RON at %s%n",alert.getProductName(), p.getPrice(), p.getStore()));
	    }
	}
	
	public List<PriceRecord> getPriceRecords(){
		return priceRecords;
	}
	
	public List<DiscountRecord> getDiscountRecords(){
		return discountRecords;
	}
	
	public List<PriceAlert> getAlerts() {
		return alerts;
	}
	
	private String extractStore(String filename) {
		return filename.split("_")[0];
	}
	
	private LocalDate extractDate(String filename) {
		String[] parts = filename.replace(".csv", "").split("_");
		String date = parts[parts.length - 1];
		return LocalDate.parse(date);
	}
	
	private void loadPriceFile(String fileName, Reader reader) {
		String store = extractStore(fileName);
		LocalDate date = extractDate(fileName);
		
		List<PriceCsvRow> rows = new CsvToBeanBuilder<PriceCsvRow>(reader)
				.withType(PriceCsvRow.class)
				.withIgnoreLeadingWhiteSpace(true)
				.withSeparator(';')
				.build()
				.parse();
		for (PriceCsvRow row : rows) {
			Product product = Product.builder()
					.product_id(row.getProduct_id())
					.product_name(row.getProduct_name())
					.product_category(row.getProduct_category())
					.brand(row.getBrand())
					.package_quantity(row.getPackage_quantity())
					.package_unit(row.getPackage_unit())
					.build();
			PriceRecord record = PriceRecord.builder()
					.product(product)
					.date(date)
					.price(row.getPrice())
					.currency(row.getCurrency())
					.store(store)
					.build();	
			priceRecords.add(record);
		}
		System.out.println("✅ Loaded: " + priceRecords.size() + " price records");
	}
	
	private void loadDiscountFile(String fileName, Reader reader) {
		String store = extractStore(fileName);
		LocalDate date = extractDate(fileName);
		
		List<DiscountCsvRow> rows = new CsvToBeanBuilder<DiscountCsvRow>(reader)
				.withType(DiscountCsvRow.class)
				.withIgnoreLeadingWhiteSpace(true)
				.withSeparator(';')
				.build()
				.parse();
		for (DiscountCsvRow row : rows) {	
			DiscountRecord discount = DiscountRecord.builder()
					.productId(row.getProduct_id())
					.from_date(LocalDate.parse(row.getFrom_date()))
					.to_date(LocalDate.parse(row.getTo_date()))
					.percentage_of_discount(row.getPercentage_of_discount())
					.store(store)
					.build();	
			discountRecords.add(discount);
		}
		System.out.println("✅ Loaded: " + discountRecords.size() + " discount records");
	}
	
	@PostConstruct
	public void loadAllCsvFiles() {
		var staticFolder = new File(getClass().getResource("/static").getFile());
		
		if(!staticFolder.exists() || !staticFolder.isDirectory()) {
			System.err.println("Static folder not found!");
			return;
		}		
		
		File[] files = staticFolder.listFiles((dir,name) -> name.endsWith(".csv"));
		if(files == null) {
			return;
		}
		for(File file : files) {
			String filename = file.getName();
		
			try(var reader = new InputStreamReader(new FileInputStream(file))){
				if(filename.contains("discounts")) {
					loadDiscountFile(filename,reader);
				} else {
					loadPriceFile(filename,reader);
				}
			} catch (IOException e) {
				System.err.println("Error loading " + filename + " : " + e.getMessage());
			}
		}
	}			
}
