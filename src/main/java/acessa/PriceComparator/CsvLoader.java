package acessa.PriceComparator;
import com.opencsv.bean.CsvToBeanBuilder;

import ch.qos.logback.core.net.SyslogOutputStream;
import jakarta.annotation.PostConstruct;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.UncheckedException;
import org.springframework.stereotype.Component;


@Component
public class CsvLoader {

	private final List<PriceRecord> priceRecords = new ArrayList<>();
	public List<PriceRecord> getPriceRecords(){
		return priceRecords;
	}
	
	@PostConstruct
	public void loadCsv() {
		String store = "Lidl";
		LocalDate date = LocalDate.of(2025, 5, 1);
		var inputStream = getClass().getResourceAsStream("/static/lidl_2025-05-01.csv");
		if (inputStream == null) {
		    System.err.println("can`t find .csv file");
		    return;
		}
		
		try(var reader = new InputStreamReader(inputStream)) {
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
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
}
