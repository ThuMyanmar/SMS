package sspd.sms.conponents;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.util.StringConverter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Time {

    public static void setupTimePicker(Spinner<LocalTime> timeSpinner) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");


        SpinnerValueFactory<LocalTime> valueFactory = new SpinnerValueFactory<LocalTime>() {
            private LocalTime value = LocalTime.of(8, 0); // Default Time

            @Override
            public void decrement(int steps) {
                value = value.minusMinutes(steps * 30); // 30 min decrement
                setValue(value);
            }

            @Override
            public void increment(int steps) {
                value = value.plusMinutes(steps * 30); // 30 min increment
                setValue(value);
            }
        };

        valueFactory.setValue(LocalTime.of(8, 0));


        valueFactory.setConverter(new StringConverter<>() {
            @Override
            public String toString(LocalTime time) {
                return (time != null) ? time.format(timeFormatter) : "";
            }

            @Override
            public LocalTime fromString(String string) {
                try {
                    return (string != null && !string.isEmpty()) ? LocalTime.parse(string, timeFormatter) : LocalTime.now();
                } catch (Exception e) {
                    return LocalTime.now();
                }
            }
        });

        timeSpinner.setValueFactory(valueFactory);
    }
}
