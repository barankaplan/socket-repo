package range;

import org.csystem.util.console.Console;

import java.util.function.Consumer;
import java.util.function.IntUnaryOperator;

class App {
    public static void main(String[] args)
    {
        IntUnaryOperator intUnaryOperator = n -> 8 * n + 1;

        var intRange = IntRange.ofClosed(10, 13220, intUnaryOperator);

        Util.doWork(intRange, val -> Console.write("%d ", val));

        Console.writeLine();

        for (var val : intRange)
            Console.write("%d ", val);
    }
}

class Util {
    public static <T> void doWork(Iterable<T> iterable, Consumer<T> consumer)
    {
        for (var val : iterable)
            consumer.accept(val);
    }
}
