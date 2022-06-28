package callback;

import org.csystem.util.console.Console;

public class KaplanCallback {
    public static void main(String[] args)
    {
        IStringToIntConverter iStringToIntConverter = s -> s.length();

        Util.display(iStringToIntConverter, "ankara");
        Util.display(s -> Util.countWhitespaces(s), "ankara ");
    }
}

class Util {
    public static void display(IStringToIntConverter converter, String t)
    {
        int result = converter.convert(t);
        //...
        Console.writeLine(result);
    }

    public static int countWhitespaces(String s)
    {
        int count = 0;

        for (int i = 0; i < s.length(); ++i)
            if (Character.isWhitespace(s.charAt(i)))
                ++count;

        return count;
    }
}

interface IStringToIntConverter {
    int convert(String s);
}
