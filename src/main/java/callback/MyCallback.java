package callback;


import org.csystem.util.console.Console;

import java.time.LocalTime;

public class MyCallback {
    public static void main(String[] args)
    {
        AlarmWithoutSecondApp.run(args);
    }
}

final class AlarmWithoutSecondApp {
    private static LocalTime getLocalTime(String [] args)
    {
        return LocalTime.of(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
    }

    private static void alarmTaskCallback()
    {
        Console.writeLine("\nArtık uyan");
        //...
    }

    private static void periodTaskCallback()
    {
        Console.write(".");
        //...
    }

    private AlarmWithoutSecondApp()
    {
    }

    public static void run(String [] args)
    {
        if (args.length != 3) {
            Console.Error.writeLine("Wrong number of arguments");
            System.exit(-1);
        }

        try {
            var alarm = new Alarm(getLocalTime(args));

            alarm.start(AlarmWithoutSecondApp::alarmTaskCallback, AlarmWithoutSecondApp::periodTaskCallback); //Method reference ile yapıldı
        }
        catch (NumberFormatException ignore) {
            Console.Error.writeLine("Invalid time values");
        }
    }
}
