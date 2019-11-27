package logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ConsoleLogger implements LogObserver {
    @Override
    public void addMessage(String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println(String.format("<%s> : %s", sdf.format(Calendar.getInstance().getTime()), message));
    }
}
