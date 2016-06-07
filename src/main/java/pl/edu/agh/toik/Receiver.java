package pl.edu.agh.toik;

import pl.edu.agh.student.smialek.tk.communications.server.CommunicationsServer;
import pl.edu.agh.student.smialek.tk.communications.server.SensorReading;
import pl.edu.agh.student.smialek.tk.communications.server.SensorReadingCallback;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Receiver {
    public static void main(String[] args) {
        final DateTimeFormatter dateFormatter = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.SHORT)
                .withZone(ZoneId.systemDefault());
        CommunicationsServer.registerCallback(new SensorReadingCallback() {
            @Override
            public void receive(SensorReading reading) {
                System.out.format("[%s] %s: <%s> %s\n",
                        dateFormatter.format(reading.getTimestamp().toInstant()), reading.getSensorName(),
                        reading.getColor(), reading.getValue()
                );
            }
        });
        CommunicationsServer.start(8080);
        System.out.println("Server started");
    }
}