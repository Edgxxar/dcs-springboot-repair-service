package de.dicos.springboot.repairservice.restful;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import de.dicos.springboot.repairservice.restful.config.RepairserviceConfig;
import de.dicos.springboot.repairservice.restful.exception.NoEstimateException;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class RepairEstimatorService
{
	private int modelColumn;
	private int actionColumn;
	private int priceColumn;
	private List<String[]> data;

	public RepairEstimatorService(RepairserviceConfig config)
	{
		try (CSVReader reader = new CSVReader(new FileReader(config.getPriceList(), StandardCharsets.ISO_8859_1))) {
			// read in all rows
			List<String[]> rows = reader.readAll();

			// load column names
			String[] headers = rows.remove(0);
			for (int i = 0; i < headers.length; i++) {
				String header = headers[i];
				switch (header) {
					case "Car Model":
						this.modelColumn = i;
						break;
					case "Repair Action":
						this.actionColumn = i;
						break;
					case "Price Estimation":
						this.priceColumn = i;
						break;
				}
			}

			// save remaining rows as data
			this.data = rows;
		} catch (IOException | CsvException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Looks up the price estimate for the given car model and repair action.
	 *
	 * @param model  the car model
	 * @param action the repair action
	 * @return price estimate
	 * @throws NoEstimateException if this repair action is not available for the given car model
	 */
	public double getEstimate(String model, String action)
		throws NoEstimateException
	{
		for (String[] row : data) {
			if (row[modelColumn].equals(model) && row[actionColumn].equals(action)) {
				// assume csv table contains correct data types
				return Double.parseDouble(row[priceColumn]);
			}
		}
		throw new NoEstimateException(model, action);
	}
}
