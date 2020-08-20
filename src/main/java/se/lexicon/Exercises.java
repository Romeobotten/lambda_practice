package se.lexicon;

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
        Predicate<Person> erik = e -> e.getFirstName().equalsIgnoreCase("Erik");

        List<Person> eriks = storage.findMany(erik);

        for(Person person: eriks) {
            System.out.println(person.toString());
        }

        System.out.println("----------------------");
    }

    /*
        2.	Find all females in the collection using findMany().
     */
    public static void exercise2(String message){
        System.out.println(message);
        Predicate<Person> boss = b -> b.getGender().equals(Gender.FEMALE);
        List<Person> bosses = storage.findMany(boss);

        for(Person person: bosses) {
            System.out.println(person.toString());
        }
        System.out.println("----------------------");
    }

    /*
        3.	Find all who are born after (and including) 2000-01-01 using findMany().
     */
    public static void exercise3(String message){
        System.out.println(message);

        Predicate<Person> teens = t -> t.getBirthDate().isAfter(LocalDate.ofYearDay(1999,365));
        List<Person> teenagers = storage.findMany(teens);

        for(Person person: teenagers) {
            System.out.println(person.toString());
        }
        System.out.println("----------------------");
    }

    /*
        4.	Find the Person that has an id of 123 using findOne().
     */
    public static void exercise4(String message){
        System.out.println(message);

        Predicate<Person> oneTwoThree = o -> o.getId() == 123;
        System.out.println(storage.findOne(oneTwoThree).toString());

        System.out.println("----------------------");
    }

    /*
        5.	Find the Person that has an id of 456 and convert to String with following content:
            “Name: Nisse Nilsson born 1999-09-09”. Use findOneAndMapToString().
     */
    public static void exercise5(String message){
        System.out.println(message);
        Predicate<Person> fourFiveSix = o -> o.getId() == 456;
        Function<Person, String> printout = p -> "Name: ".concat(p.getFirstName() + " " + p.getLastName()).
                concat(" born " + p.getBirthDate().toString() + "\n");
        System.out.println(storage.findOneAndMapToString(fourFiveSix, printout));

        System.out.println("----------------------");
    }

    /*
        6.	Find all male people whose names start with “E” and convert each to a String using findManyAndMapEachToString().
     */
    public static void exercise6(String message){
        System.out.println(message);
        Predicate<Person> eMale = e -> e.getFirstName().startsWith("E") && e.getGender().equals(Gender.MALE);
        Function<Person, String> printout = p -> p.toString();
        List<String> eMen = storage.findManyAndMapEachToString(eMale, printout);

        for(String manString: eMen) {
            System.out.println(manString);
        }
        System.out.println("----------------------");
    }

    /*
        7.	Find all people who are below age of 10 and convert them to a String like this:
            “Olle Svensson 9 years”. Use findManyAndMapEachToString() method.
     */
    public static void exercise7(String message){
        System.out.println(message);
        Predicate<Person> kids = k -> k.getBirthDate().isAfter(LocalDate.now().minusYears(10));
        Function<Person, String> printout = p -> p.getFirstName().concat(" " + p.getLastName() + " " +
                (LocalDate.now().compareTo(p.getBirthDate())) + " years " + p.getBirthDate().toString());

        List<String> kidsPrintout = storage.findManyAndMapEachToString(kids, printout);

        for(String kidString: kidsPrintout) {
            System.out.println(kidString);
        }
        System.out.println("----------------------");
    }

    /*
        8.	Using findAndDo() print out all people with firstName “Ulf”.
     */
    public static void exercise8(String message){
        System.out.println(message);

        Predicate<Person> nameUffe = p -> p.getFirstName().equals("Ulf"); // Lambda, sending condition
        Consumer<Person> accept = a -> System.out.println(a.toString());
        // What is the use of consumer here? Do we want to adjust the printout?

        storage.findAndDo(nameUffe,accept);
        System.out.println("----------------------");
    }

    /*
        9.	Using findAndDo() print out everyone who have their lastName contain their firstName.
     */
    public static void exercise9(String message){
        System.out.println(message);
        Predicate<Person> sameName = p -> p.getLastName().toLowerCase().contains(p.getFirstName().toLowerCase());
        Consumer<Person> accept = a -> System.out.println(a.toString());
        // What is the use of consumer here?

        storage.findAndDo(sameName,accept);
        System.out.println("----------------------");
    }

    /*
        10.	Using findAndDo() print out the firstName and lastName of everyone whose firstName is a palindrome.
     */
    public static void exercise10(String message){
        System.out.println(message);
        Predicate<Person> palindrome = p -> {
            for (int i = 0; i < p.getFirstName().length(); i++) { // Checking if name is a Palindrome
                if(p.getFirstName().toLowerCase().charAt(i) !=  // Not a palindrome
                        p.getFirstName().toLowerCase().charAt(p.getFirstName().length() - 1 - i)) return false;
            } // I am checking it twice like SantaClaus :-D
            return true;
        };
        Consumer<Person> printout = a -> System.out.println(a.getFirstName().concat(" " + a.getLastName()));
        storage.findAndDo(palindrome,printout);

        System.out.println("----------------------");
    }

    /*
        11.	Using findAndSort() find everyone whose firstName starts with A sorted by birthdate.
     */
    public static void exercise11(String message){
        System.out.println(message);
        Predicate<Person> aPerson = a -> a.getFirstName().startsWith("A");

        Comparator<Person> compareDate = Comparator.comparing(p -> p.getBirthDate());

        List<Person> aNamesAge = storage.findAndSort(aPerson, compareDate);
        for(Person person: aNamesAge) {
            System.out.println(person.toString());
        }
        System.out.println("----------------------");
    }

    /*
        12.	Using findAndSort() find everyone born before 1950 sorted reversed by lastest to earliest.
     */
    public static void exercise12(String message){
        System.out.println(message);

        Predicate<Person> oldPerson = o -> o.getBirthDate().isBefore(LocalDate.ofYearDay(1950,1));
        Comparator<Person> compareDate = Comparator.comparing(p -> p.getBirthDate());
        List<Person> aNamesAge = storage.findAndSort(oldPerson, compareDate.reversed());

        for(Person person: aNamesAge) {
            System.out.println(person.toString());
        }
        System.out.println("----------------------");
    }

    /*
        13.	Using findAndSort() find everyone sorted in following order: lastName > firstName > birthDate.
     */
    public static void exercise13(String message){
        System.out.println(message);

        Comparator<Person> compareAll = Comparator.comparing(Person :: getLastName).thenComparing
                (Person :: getFirstName).thenComparing(Person :: getBirthDate);
        List<Person> sortedList = storage.findAndSort(compareAll);

        for(Person person: sortedList) {
            System.out.println(person.toString());
        }
        System.out.println("----------------------");
    }
}
