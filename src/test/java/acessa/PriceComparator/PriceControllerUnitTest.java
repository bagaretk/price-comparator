package acessa.PriceComparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import accesa.PriceComparator.controller.PriceController;
import accesa.PriceComparator.csv.CsvLoader;
import accesa.PriceComparator.model.PriceRecord;
import accesa.PriceComparator.model.Product;

public class PriceControllerUnitTest {
	@Test
    void returnsMatchingPriceRecord() {
        Product product = Product.builder()
                .product_id("P001")
                .product_name("lapte zuzu")
                .product_category("lactate")
                .build();

        PriceRecord record = PriceRecord.builder()
                .product(product)
                .store("Lidl")
                .price(9.90f)
                .currency("RON")
                .date(LocalDate.of(2025, 5, 1))
                .build();
        
        CsvLoader fakeLoader = new CsvLoader();
        fakeLoader.getPriceRecords().add(record); // manually insert data
        
        PriceController controller = new PriceController(fakeLoader);
        
        List<PriceRecord> result = controller.getPricesByProduct("P001", null, null, null, null);
        
        assertEquals(1, result.size());
        assertEquals("Lidl", result.get(0).getStore());
        assertEquals(9.90f, result.get(0).getPrice());
	}
}
