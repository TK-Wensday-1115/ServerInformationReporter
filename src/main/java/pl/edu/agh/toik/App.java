package pl.edu.agh.toik;

import pl.edu.agh.tk.ToCSVConverter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        try {
            List<String> columnNames = new LinkedList<>();
            columnNames.add("Temperature");
            columnNames.add("Pressure");
            columnNames.add("Humidity");

            List<Integer> temperature = new LinkedList<>();
            temperature.add(22);
            temperature.add(25);
            temperature.add(29);
            List<Integer> pressure = new LinkedList<>();
            pressure.add(1010);
            pressure.add(1100);
            pressure.add(1040);
            List<Integer> humidity = new LinkedList<>();
            humidity.add(50);
            humidity.add(60);
            humidity.add(40);

            List<List<? extends Number>> listsOfData = new LinkedList<>();
            listsOfData.add(temperature);
            listsOfData.add(pressure);
            listsOfData.add(humidity);

            //Result file location: *\UserDir\Documents\CSVData\SENSOR_TEMP_ONLY
            ToCSVConverter.writeToFile("SENSOR_TEMP_ONLY", temperature, "Temperature");
            //Columns: Temperature, Pressure, Humidity
            //Result file location: *\UserDir\Documents\CSVData\SENSOR_3PARAMS
            ToCSVConverter.writeToFile("SENSOR_3PARAMS", listsOfData, columnNames);
            ToCSVConverter.writeToFileWithTimeStamp("SENSOR_TEMP_ONLY_withTimeStamp", temperature, "Temperature");
            ToCSVConverter.writeToFileWithTimeStamp("SENSOR_3PARAMS_withTimeStamp", listsOfData, columnNames);
            System.out.println("Done. Created files: SENSOR_TEMP_ONLY, SENSOR_3PARAMS, SENSOR_TEMP_ONLY_withTimeStamp, SENSOR_3PARAMS_withTimeStamp");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
