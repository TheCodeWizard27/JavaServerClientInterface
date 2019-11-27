package logger;

import java.util.ArrayList;
import java.util.List;

public class Logger {
    private static Logger logger;

    private List<LogObserver> observers = new ArrayList<>();

    private Logger(){

    }

    public static Logger getInstance(){
        if(logger == null)
            logger = new Logger();

        return logger;
    }

    public void addObserver(LogObserver observer){
        observers.add(observer);
    }

    public void removeObserver(LogObserver observer){
        if(observers.contains(observer))
            observers.remove(observer);
    }

    public void writeMessage(String message){
        for(LogObserver observer : this.observers)
            observer.addMessage(message);
    }

}
