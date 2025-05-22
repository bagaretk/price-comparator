package acessa.PriceComparator;
import com.opencsv.bean.CsvToBeanBuilder;

import ch.qos.logback.core.net.SyslogOutputStream;
import jakarta.annotation.PostConstruct;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

import org.apache.commons.lang3.exception.UncheckedException;
import org.springframework.stereotype.Component;


@Component
public class CsvLoader {

	@PostConstruct
	public void loadCsv() {
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
				System.out.println(row);
			}
			System.out.println("First row: " + rows.get(0));
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
}
