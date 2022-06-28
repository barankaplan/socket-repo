package mainpackage;

import factory.*;
import org.csystem.util.console.Console;
import product.ProductConverter;
import product.ProductInfo;
import range.IntRange;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class Sender {
    private static final Function<? super ProductInfo, ?> FUNCTION_KAPLAN = new Function<ProductInfo, Object>() {
        @Override
        public Integer apply(ProductInfo productInfo) {
            return productInfo.getName().length();
        }
    };

    private static final Consumer<ProductInfo> CONSUMER_KAPLAN = new Consumer<ProductInfo>() {
        @Override
        public void accept(ProductInfo productInfo) {
            productInfo.setCost(BigDecimal.valueOf(1111));
            Console.writeLine(productInfo.getCost());
        }
    };

    private static final Supplier<LocalDate> SUPPLIER_KAPLAN = new Supplier<LocalDate>() {
        @Override
        public LocalDate get() {
            return LocalDate.now();
        }
    };


    public static void main(String[] args) {

//        streamOperation1();
//        Console.writeLine("***");
//        streamOperation2();
//        Console.writeLine("***");
//        findFirstProductNotInStock();
//        Console.writeLine("***");
//        compareAndCount();
//        Console.writeLine("***");
//        ContainsHyphen();
//        Console.writeLine("***");
//        mapToIntStream();
//        Console.writeLine("***");
//        reduceInteger();
//        Console.writeLine("***");
//        mapReduceToFindGain();
//        Console.writeLine("***");
//        productsNotInStock();

//        Console.writeLine("***");
        productsInStock();

//        Console.writeLine("***");
//
//        stringOperations();
//        Console.writeLine("***");
//        IntStream.range(0, 11).forEach(n -> Console.writeLine("%d! = %d", n, FactorialUtil.factorial(n)));
//        Console.writeLine("***");
//
//        dealingWithIterableObjects();
//        Console.writeLine("***");
//
//        stringStreamUsingPrefix();
//        Console.writeLine("***");

//        giveMeRandomProducts();
//        Console.writeLine("***");

//        distinctProducts();
//        Console.writeLine("***");


//        mostExpensiveProductsToList();
//        Console.writeLine("***");

//        iterateWithSteps();
//        Console.writeLine("***");


//        concatViaStream();
//        Console.writeLine("***");

//        getAverageAge();
//        Console.writeLine("***");

//        partition();


//        grouping();

//        joining();

    }

    public static void joining() {
        try {
            var factory = MyProductFactory.loadFromTextFile("products-temp.csv");
            var products = factory.PRODUCTS;

            var str = products.stream().map(ProductInfo::getName)
                    .map(s -> "[[" + s + "]]")
                    .collect(Collectors.joining(" :: ", "{", "}"));

            Console.writeLine(str);
        }
        catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static void grouping() {
        try {
            var factory = PersonFactory.loadFromTextFile(Path.of("people.csv"));
            var people = factory.PEOPLE;

            Map<MaarialStatus, List<Person>> map = people.stream()
                    .collect(Collectors.groupingBy(Person::getMaritalStatus));

            if (map.containsKey(MaarialStatus.SINGLE)) {
                Console.writeLine("single ones:");
                map.get(MaarialStatus.SINGLE).forEach(Console::writeLine);
            }
            else
                Console.writeLine("any single");

            Console.writeLine("-------------------------------------------------");

            if (map.containsKey(MaarialStatus.MARRIED)) {
                Console.writeLine("married ones:");
                map.get(MaarialStatus.MARRIED).forEach(Console::writeLine);
            }
            else
                Console.writeLine("any married");

            Console.writeLine("-------------------------------------------------");

            if (map.containsKey(MaarialStatus.DIVORCED)) {
                Console.writeLine("divorced ones:");
                map.get(MaarialStatus.DIVORCED).forEach(Console::writeLine);
            }
            else
                Console.writeLine("Hiç boşanmış kişi yok");

            Console.writeLine("-------------------------------------------------");

            if (map.containsKey(MaarialStatus.WIDOW)) {
                Console.writeLine("widow ones:");
                map.get(MaarialStatus.WIDOW).forEach(Console::writeLine);
            }
            else
                Console.writeLine("any widows");

        }
        catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static void partition() {
        try {
            var factory = PersonFactory.loadFromTextFile(Path.of("people.csv"));
            var people = factory.PEOPLE;

            var ageThreshold = Console.readDouble("Age threshold:");

            Map<Boolean, List<Person>> map = people.stream()
                    .collect(Collectors.partitioningBy(p -> p.getAge() > ageThreshold));

            Console.writeLine(" folders than %.2f ", ageThreshold);
            map.get(true).forEach(Console::writeLine);
            Console.writeLine("--------------------------------");
            Console.writeLine(" younger than %.2f ", ageThreshold);
            map.get(false).forEach(Console::writeLine);
        }
        catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static void getAverageAge() {
        try {
            var factory = PersonFactory.loadFromTextFile(Path.of("people.csv"));
            var people = factory.PEOPLE;

            var ageThreshold = Console.readDouble("Enter the threshold:");

            people.stream()
                    .mapToDouble(Person::getAge)
                    .filter(age -> age > ageThreshold)
                    .average()
                    .ifPresentOrElse(avg -> Console.write("%f ", avg), () -> Console.writeLine("Hiç kişi yok"));
        }
        catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static void concatViaStream() {
        try {
            var names = NameFactory.loadFromTextFile(Path.of("names-temp.csv")).NAMES;
            var products = MyProductFactory.loadFromTextFile("products-temp.csv").PRODUCTS;

            var texts = Stream.concat(products.stream().map(ProductInfo::getName), names.stream())
                    .collect(Collectors.toList());

            texts.forEach(Console::writeLine);

        }
        catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static void iterateWithSteps() {
        var a = Console.readInt("first number:");
        var b = Console.readInt("second number:");
        var s = Console.readInt("amount of steps:");

        double[] numbers = DoubleStream.iterate(a, i -> i + s).limit(b - a + 1)
                .map(Math::sqrt)
                .filter(val -> val <= b).toArray();

        Arrays.stream(numbers).forEach(val -> Console.write("%f ", val));
        Console.writeLine();
    }

    public static void mostExpensiveProductsToList() {
        try {
            //comparable should be implemented!
            var productFactory = MyProductFactory.loadFromTextFile("products.csv");
            var products = productFactory.PRODUCTS;

            var n = Console.readInt("Enter a number:");

            List<ProductInfo> list = products.stream()
                    .sorted((p1, p2) -> p2.getPrice().compareTo(p1.getPrice()))
                    .limit(n)
                    .collect(Collectors.toList());

            Console.writeLine(list.getClass().getName());
            list.forEach(Console::writeLine);
        }
        catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static void distinctProducts() {
        try {
            var productFactory = MyProductFactory.loadFromTextFile("products-temp.csv");


            var n = Console.readInt("Enter a value between 100 and 200:");
            var products = new ArrayList<ProductInfo>();

            var random = new Random();

            //clonable ile farkli nesne referanslari döndürüpbunlari equals ve hashcode ile ayirt ediyoruz!
            //yani 1 numarali satidaki veri diger satirdaki 1 numara ile ayni
            //senaryoda farkli kopyalar cikarip equals ve hash code lar ile bunu sagladik !

            IntStream.range(0, n)
                    .mapToObj(i -> productFactory.getRandomProduct(random))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .forEach(products::add);
            products.forEach(Console::writeLine);

            Console.writeLine("--------------------");

            products.stream().distinct().forEach(Console::writeLine);
        }
        catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static void giveMeRandomProducts() {
        try {
            var productFactory = MyProductFactory.loadFromTextFile("products-temp.csv");

            var n = Console.readInt("enter a number:");

            var random = new Random();

            IntStream.range(0, n)
                    .mapToObj(i -> productFactory.getRandomProduct(random))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .forEach(Console::writeLine);
        }
        catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static void stringStreamUsingPrefix() {
        var a = new int[3];
        a[0] = 1;
        a[1] = 2;
        a[2] = 3;
        var prefix = Console.read("enter a prefix : ");

        IntStream.of(a).forEach(val -> Console.write("%d ", val));
        Console.writeLine();
        IntStream.of(a).mapToObj(val -> prefix + " " + val).forEach(val -> Console.write("%s ", val));

        Console.writeLine();
    }

    public static void dealingWithIterableObjects() {

        //stream methodu collection da, onun üstünde olan iterable da degil !
        //using iterable objects
        StreamSupport.stream(IntRange.of(0, 500, n -> 6 * n + 1).spliterator(), false).forEach(Console::writeLine);

        try {
            var productFactory = ProductFactory.loadFromTextFile("products.csv");
            if (productFactory.isEmpty())
                return;

            var products = productFactory.get().getProductsAsIterable();

            StreamSupport.stream(products.spliterator(), false)
                    .filter(p -> p.getStock() < 0)
                    .map(ProductConverter::new)
                    .map(ProductConverter::toProductStockDTO)
                    .forEach(Console::writeLine);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static class FactorialUtil {
        private FactorialUtil() {
        }

        public static int factorial(int n) {
            //identity result and val
            //r =1
            //r =1*2=2
            //r=2
            //r=2*3
            return n > 1 ? IntStream.rangeClosed(2, n).reduce(1, (a, b) -> a * b) : 1;
        }
    }

    public static void stringOperations() {
        IntStream.range(0, 26).forEach(i -> Console.write("%c", (char) ('A' + i)));
        Console.writeLine();

        IntStream.range(0, 26)
                .map(i -> 'a' + i)
                .forEach(code -> Console.write("%c", (char) code));
        Console.writeLine();

        var str = Console.read("Enter a text:");

        IntStream.range(0, str.length())
                .map(str::charAt)
                .forEach(ch -> Console.write("%c ", (char) ch));

        Console.writeLine();

        IntStream.range(0, str.length())
                .map(str::charAt)
                .map(Character::toLowerCase)
                .forEach(ch -> Console.write("%c ", (char) ch));

        Console.writeLine();

        IntStream.range(0, str.length())
                .map(i -> Character.toLowerCase(str.charAt(i)))
                .forEach(ch -> Console.write("%c ", (char) ch));

        Console.writeLine();


        IntStream.range(0, str.length())
                .map(str::charAt)
                .filter(ch -> !Character.isWhitespace(ch))
                .forEach(ch -> Console.write("%c", (char) ch));

        Console.writeLine();


        IntStream.range(0, str.length())
                .map(str::charAt)
                .filter(ch -> !Character.isWhitespace(ch))
                .map(Character::toLowerCase)
                .forEach(ch -> Console.write("%c", (char) ch));


        ArrayList<Character> val = new ArrayList<>();

        IntConsumer addToMyList = value -> val.add((char) value);

        IntStream.range(0, str.length())
                .map(str::charAt)
                .forEach(addToMyList);

        Console.writeLine();

        Console.writeLine(val.get(0));
        Console.writeLine(val.indexOf('n'));

        Console.writeLine();
    }

    public static void productsInStock() {
        try {
            var productFactory = ProductFactory.loadFromTextFile("products.csv");
            if (productFactory.isEmpty()) return;

            var products = productFactory.get().PRODUCTS;

            products.stream().filter(p -> p.getStock() > 0).map(ProductConverter::new).map(ProductConverter::toProductStockDTO).forEach(Console::writeLine);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static void productsNotInStock() {
        try {
            var productFactory = ProductFactory.loadFromTextFile("products.csv");
            if (productFactory.isEmpty()) return;

            var products = productFactory.get().PRODUCTS;

            products.stream().
                    filter(p -> p.getStock() <= 0)
                    .map(pi -> pi.getName() + " : " + pi.getStock()).
                    forEach(Console::writeLine);

            products.stream().filter(p -> p.getStock() <= 0).mapToInt(ProductInfo::getStock).reduce(Integer::sum).ifPresentOrElse(Console::writeLine, () -> Console.writeLine("all products are present"));
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static void mapReduceToFindGain() {
        try {
            var productFactory = ProductFactory.loadFromTextFile("products.csv");
            if (productFactory.isEmpty()) return;

            var products = productFactory.get().PRODUCTS;

            products.stream().filter(p -> p.getStock() > 0).map(p -> p.getPrice().subtract(p.getCost()).multiply(BigDecimal.valueOf(p.getStock()))).forEach(Console::writeLine);

            Console.writeLine("Total financial gain");

            var result = products.stream().filter(p -> p.getStock() > 0)//BigDecimal bu gibi yerlerde faydailidir !
                    .map(p -> p.getPrice().subtract(p.getCost()).multiply(BigDecimal.valueOf(p.getStock()))).reduce(BigDecimal::add);
            //reduce bir nesne referansi döndürürken foreach bir is yapar / kabaca consume eder
            result.ifPresentOrElse(Console::writeLine, () -> Console.writeLine("no products in stock"));
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static void reduceInteger() {
        try {
            var factory = NumberFactory.loadFromTextFile(Path.of("numbers.csv"));
            var numbers = factory.getNumbers();

            //using reduce with primitive types
            //callback and return an optional
            var result = IntStream.of(numbers).reduce(Integer::sum);

            result.ifPresentOrElse(Console::writeLine, () -> Console.writeLine("Stream is empty"));
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static void mapToIntStream() {
        try {
            var productFactory = ProductFactory.loadFromTextFile("products.csv");
            if (productFactory.isEmpty()) return;

            var products = productFactory.get().PRODUCTS;
            //not Stream<Integer> !!!  IntStream

            /*
            IntStream is a stream of primitive int values.

            Stream<Integer> is a stream of Integer objects.
             */
            products.stream().filter(p -> p.getTotal().compareTo(new BigDecimal("300000")) < 0 && p.getName().contains("-")).map(ProductInfo::getStock).forEach(Console::writeLine);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static void ContainsHyphen() {
        try {
            var productFactory = ProductFactory.loadFromTextFile("products.csv");
            if (productFactory.isEmpty()) return;

            var products = productFactory.get().PRODUCTS;

            var count = products.stream().filter(p -> p.getTotal().compareTo(new BigDecimal("300000")) < 0).map(ProductInfo::getName).filter(s -> s.contains("-")).count();

            Console.writeLine("Count:%d", count);

            products.stream().filter(p -> p.getTotal().compareTo(new BigDecimal("300000")) < 0).map(ProductInfo::getName).filter(s -> s.contains("-")).forEach(Console::writeLine);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static void compareAndCount() {
        try {
            var productFactory = ProductFactory.loadFromTextFile("products.csv");
            if (productFactory.isEmpty()) return;

            var products = productFactory.get().PRODUCTS;

            var count = products.stream().filter(p -> p.getTotal().compareTo(new BigDecimal("100000")) < 0).count();

            Console.writeLine("Count:%d", count);

            products.stream().filter(p -> p.getTotal().compareTo(new BigDecimal("100000")) < 0).map(ProductInfo::getName).forEach(Console::writeLine);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static void findFirstProductNotInStock() {
        try {
            var productFactory = ProductFactory.loadFromTextFile("products.csv");
            if (productFactory.isEmpty()) return;

            var products = productFactory.get().PRODUCTS;

            var optProduct = products.stream().filter(p -> p.getStock() <= 0).findFirst();

            optProduct.ifPresentOrElse(Console::writeLine, () -> Console.writeLine("All products are in stock"));
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static void streamOperation2() {
        try {
            var productFactory = ProductFactory.loadFromTextFile("products.csv");
            if (productFactory.isEmpty()) Console.writeLine("empty !");

            var products = productFactory.get().PRODUCTS;

            if (products.stream().allMatch(p -> p.getStock() > 0)) Console.writeLine("All products are in stock");
            else {
                Console.writeLine("products are not in stock");
                products.stream().filter(p -> p.getStock() <= 0).map(pi -> pi.getName().toUpperCase())
                        //map returns a new Stream<String> bir türden baska bir türe iliskin stream elde ederim
                        .forEach(Console::writeLine);//toString for String

                products.stream().filter(p -> p.getStock() <= 0).forEach(Console::writeLine);
                //toString for ProductInfo

            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static void streamOperation1() {
        try {
            var productFactory = ProductFactory.loadFromTextFile("products.csv");
            if (productFactory.isEmpty()) Console.writeLine("empty !");

            var stock = Console.readInt("Enter the amount of the product you are seeking for: ");

            var products = productFactory.get().PRODUCTS;

            //predicate- returns boolean
            Predicate<ProductInfo> productInfoPredicate = pi -> pi.getStock() == stock && pi.getCost().intValue() > 300;

            Function<ProductInfo, String> productInfoStringFunction = pi -> pi.getName().toUpperCase();

            products.stream().filter(productInfoPredicate).map(productInfoStringFunction).forEach(Console::writeLine);


            products.stream().filter(productInfoPredicate).map(FUNCTION_KAPLAN).forEach(Console::writeLine);


            products.stream().filter(productInfoPredicate).forEach(CONSUMER_KAPLAN);


            System.out.println(SUPPLIER_KAPLAN.get());


        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }


}
