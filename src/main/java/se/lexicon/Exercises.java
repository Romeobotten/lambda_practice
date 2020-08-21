package se.lexicon;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.w3c.dom.ls.LSOutput;
import se.lexicon.data.DataStorage;
import se.lexicon.model.Gender;
import se.lexicon.model.Person;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Exercises {

    private final static DataStorage storage = DataStorage.INSTANCE;

    /*
       1.	Find everyone that has firstName: “Erik” using findMany().
    */
    public static void exercise1(String message){
        System.out.println(message);

        storage.findMany(e -> e.getFirstName().equalsIgnoreCase("Erik")).forEach(System.out::println);

        System.out.println("----------------------");
    }

    /*
        2.	Find all females in the collection using findMany().
     */
    public static void exercise2(String message){
        System.out.println(message);

        storage.findMany(f -> f.getGender() == Gender.FEMALE).forEach(System.out::println); // only Predicate

        System.out.println("----------------------");
    }

    /*
        3.	Find all who are born after (and including) 2000-01-01 using findMany().
     */
    public static void exercise3(String message){
        System.out.println(message);

//        Predicate<Person> teens = t -> t.getBirthDate().isAfter(LocalDate.ofYearDay(1999,365));
//        List<Person> teenagers = storage.findMany(teens);

        // only Predicate
        storage.findMany(t -> t.getBirthDate().isAfter(LocalDate.ofYearDay(1999,365))).forEach(System.out::println);

//        for(Person person: teenagers) {
//            System.out.println(person.toString());
//        }
        System.out.println("----------------------");
    }

    /*
        4.	Find the Person that has an id of 123 using findOne().
     */
    public static void exercise4(String message){
        System.out.println(message);

//        Predicate<Person> oneTwoThree = o -> o.getId() == 123;
//        System.out.println(storage.findOne(oneTwoThree).toString());

        System.out.println(storage.findOne(o -> o.getId() == 123)); // only Predicate
        System.out.println("----------------------");
    }

    /*
        5.	Find the Person that has an id of 456 and convert to String with following content:
            “Name: Nisse Nilsson born 1999-09-09”. Use findOneAndMapToString().
     */
    public static void exercise5(String message){
        System.out.println(message);

        // Predicate and Function
        System.out.println(storage.findOneAndMapToString(o -> o.getId() == 456,
                p -> "Name: ".concat(p.getFirstName() + " " + p.getLastName()).concat(" born " + p.getBirthDate())));

        System.out.println("----------------------");
    }

    /*
        6.	Find all male people whose names start with “E” and convert each to a String using findManyAndMapEachToString().
     */
    public static void exercise6(String message){
        System.out.println(message);
//        Predicate<Person> eMale = ;
//        Function<Person, String> printout = ;
//        List<String> eMen = storage.findManyAndMapEachToString(eMale, printout);

        // Predicate and Function
        storage.findManyAndMapEachToString(e -> e.getFirstName().startsWith("E") && e.getGender().equals(Gender.MALE),
                p -> p.toString()).forEach(System.out::println);

//        for(String manString: eMen) {
//            System.out.println(manString);
//        }
        System.out.println("----------------------");
    }

    /*
        7.	Find all people who are below age of 10 and convert them to a String like this:
            “Olle Svensson 9 years”. Use findManyAndMapEachToString() method.
     */
    public static void exercise7(String message){
        System.out.println(message);

        // Predicate and Function, a little too complicated function to get the year output right
        storage.findManyAndMapEachToString(k -> k.getBirthDate().isAfter(LocalDate.now().minusYears(10)),
                p -> p.getFirstName().concat(" " + p.getLastName() + " " +
                 (LocalDate.now().compareTo(p.getBirthDate())+(Math.max(-1,Math.min(0,LocalDate.now().getDayOfYear()-p.getBirthDate().getDayOfYear())))) +
                        " years  - born " + p.getBirthDate())).forEach(System.out::println);

        System.out.println("----------------------");
    }

    /*
        8.	Using findAndDo() print out all people with firstName “Ulf”.
     */
    public static void exercise8(String message){
        System.out.println(message);

        // What is the use of consumer here? Do we want to adjust the printout?

        storage.findAndDo(p -> p.getFirstName().equals("Ulf"),a -> System.out.println(a)); // Predicate and Consumer

        System.out.println("----------------------");
    }

    /*
        9.	Using findAndDo() print out everyone who have their lastName contain their firstName.
     */
    public static void exercise9(String message){
        System.out.println(message);
//        Old code:
//        Predicate<Person> sameName = p -> p.getLastName().toLowerCase().contains(p.getFirstName().toLowerCase());
//        Consumer<Person> accept = a -> System.out.println(a.toString());

        // What is the use of consumer here? Do we want to adjust the printout?

        storage.findAndDo(p -> p.getLastName().toLowerCase().contains(p.getFirstName().toLowerCase()), // Predicate
                a -> System.out.println(a));  // Consumer

        System.out.println("----------------------");
    }

    /*
        10.	Using findAndDo() print out the firstName and lastName of everyone whose firstName is a palindrome.
     */
    public static void exercise10(String message){
        System.out.println(message);

        // this loop is checking if firstname is a palindrome, I am checking it twice like SantaClaus :-D
        storage.findAndDo(p -> {
            for (int i = 0; i < p.getFirstName().length(); i++) {
                if(p.getFirstName().toLowerCase().charAt(i) !=
                        p.getFirstName().toLowerCase().charAt(p.getFirstName().length() - 1 - i)) return false;
            } return true;
        },a -> System.out.println(a.getFirstName().concat(" " + a.getLastName()))); // Predicate and Consumer

        System.out.println("----------------------");
    }

    /*
        11.	Using findAndSort() find everyone whose firstName starts with A sorted by birthdate.
     */
    public static void exercise11(String message){
        System.out.println(message);
//        Old code:
//        Predicate<Person> aPerson = a -> a.getFirstName().startsWith("A");
//        Comparator<Person> compareDate = Comparator.comparing(p -> p.getBirthDate());

        storage.findAndSort(a -> a.getFirstName().startsWith("A"),  // Predicate
                Comparator.comparing(p -> p.getBirthDate())).forEach(System.out::println);  // Comparator

//        List<Person> aNamesAge = storage.findAndSort(aPerson, compareDate);
//        for(Person person: aNamesAge) {
//            System.out.println(person.toString());
//        }
        System.out.println("----------------------");
    }

    /*
        12.	Using findAndSort() find everyone born before 1950 sorted reversed by lastest to earliest.
     */
    public static void exercise12(String message){
        System.out.println(message);
//        Old code:
//        Predicate<Person> oldPerson = o -> o.getBirthDate().isBefore(LocalDate.ofYearDay(1950,1));
//        Comparator<Person> compareDate = Comparator.comparing(p -> p.getBirthDate());
//        List<Person> aNamesAge = storage.findAndSort(oldPerson, compareDate.reversed());

        storage.findAndSort(o -> o.getBirthDate().isBefore(LocalDate.ofYearDay(1950,1)), // Predicate
                Comparator.comparing(Person::getBirthDate).reversed()).forEach(System.out::println); // Comparator
//        Old code:
//        for(Person person: aNamesAge) {
//            System.out.println(person.toString());
//        }
        System.out.println("----------------------");
    }

    /*
        13.	Using findAndSort() find everyone sorted in following order: lastName > firstName > birthDate.
     */
    public static void exercise13(String message){
        System.out.println(message);
//        Old code:
//        Comparator<Person> compareAll = Comparator.comparing(Person :: getLastName).thenComparing
//                (Person :: getFirstName).thenComparing(Person :: getBirthDate);
//        List<Person> sortedList = storage.findAndSort(compareAll);

        storage.findAndSort(Comparator.comparing(Person :: getLastName).thenComparing  // only Comparator
                (Person :: getFirstName).thenComparing(Person :: getBirthDate)).forEach(System.out::println);

        System.out.println("----------------------");
    }

    /*
    14.	Using findAndSort() find everyone who has retired (65 years) and sort by youngest first.
 */
    public static void exercise14(String message){
        System.out.println(message);
//        Old code:
//        Comparator<Person> compareAll = Comparator.comparing(Person :: getLastName).thenComparing
//                (Person :: getFirstName).thenComparing(Person :: getBirthDate);
//        List<Person> sortedList = storage.findAndSort(compareAll);

        storage.findAndSort(o -> o.getBirthDate().isBefore(LocalDate.now().minusYears(65)), // Predicate
                Comparator.comparing(Person::getBirthDate).reversed()).forEach(System.out::println); // Comparator

        System.out.println("----------------------");
    }

    /*
    15.	Find all people who has birthday today and convert them to a String like this:
        “Happy birthday Olle Svensson 29 years today!”. Use findManyAndMapEachToString() method.
 */
    public static void exercise15(String message){
        System.out.println(message);

        storage.findManyAndMapEachToString(k -> k.getBirthDate().getDayOfMonth() == LocalDate.now().getDayOfMonth() &&
                k.getBirthDate().getMonth().equals(LocalDate.now().getMonth()),
                p -> "Happy birthday " + p.getFirstName().concat(" " + p.getLastName() + " " +
                        (LocalDate.now().compareTo(p.getBirthDate())+
                                (Math.max(-1,Math.min(0,LocalDate.now().getDayOfYear()-p.getBirthDate().getDayOfYear())))) +
                        " years today!  - born " + p.getBirthDate())).forEach(System.out::println);

        System.out.println("----------------------");
    }

    /*
        16.	Find all persons who are neither male or female in the collection using findMany().
     */
    public static void exercise16(String message){
        System.out.println(message);

        storage.findMany(f -> f.getGender() == Gender.NONBINARY).forEach(System.out::println); // only Predicate

        System.out.println("----------------------");
    }

}
