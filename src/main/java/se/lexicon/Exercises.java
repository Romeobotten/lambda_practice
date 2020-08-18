package se.lexicon;

import se.lexicon.data.DataStorage;
import se.lexicon.model.Gender;
import se.lexicon.model.Person;

import java.time.LocalDate;
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
        System.out.println(storage.findMany(erik));

        System.out.println("----------------------");
    }

    /*
        2.	Find all females in the collection using findMany().
     */
    public static void exercise2(String message){
        System.out.println(message);
        Predicate<Person> bites = b -> b.getGender().equals(Gender.FEMALE);
        System.out.println(storage.findMany(bites));

        System.out.println("----------------------");
    }

    /*
        3.	Find all who are born after (and including) 2000-01-01 using findMany().
     */
    public static void exercise3(String message){
        System.out.println(message);

        Predicate<Person> teens = t -> t.getBirthDate().isAfter(LocalDate.of(2000,01,01));
        System.out.println(storage.findMany(teens));
        System.out.println("----------------------");
    }

    /*
        4.	Find the Person that has an id of 123 using findOne().
     */
    public static void exercise4(String message){
        System.out.println(message);
        Predicate<Person> oneTwoThree = o -> o.getId() == 123;
        System.out.println(storage.findOne(oneTwoThree));

        System.out.println("----------------------");
    }

    /*
        5.	Find the Person that has an id of 456 and convert to String with following content:
            “Name: Nisse Nilsson born 1999-09-09”. Use findOneAndMapToString().
     */
    public static void exercise5(String message){
        System.out.println(message);
        Predicate<Person> fourFiveSix = o -> o.getId() == 456;
        Function<Person, String> printout = p -> p.toString();
        System.out.println(storage.findOneAndMapToString(fourFiveSix, printout));

        System.out.println("----------------------");
    }

    /*
        6.	Find all male people whose names start with “E” and convert each to a String using findManyAndMapEachToString().
     */
    public static void exercise6(String message){
        System.out.println(message);
        Predicate<Person> emale = e -> e.getFirstName().startsWith("E") && e.getGender().equals(Gender.MALE);
        Function<Person, String> printout = p -> p.toString();
        System.out.println(storage.findManyAndMapEachToString(emale, printout));

        System.out.println("----------------------");
    }

    /*
        7.	Find all people who are below age of 10 and convert them to a String like this:
            “Olle Svensson 9 years”. Use findManyAndMapEachToString() method.
     */
    public static void exercise7(String message){
        System.out.println(message);
        Predicate<Person> kids = k -> k.getBirthDate().isAfter(LocalDate.of(2010,8,21));
        Function<Person, String> printout = p -> p.getFirstName().concat(" " + p.getLastName() + " " +
                (p.getBirthDate().compareTo(LocalDate.now())) + " years");
        System.out.println(storage.findManyAndMapEachToString(kids, printout));

        System.out.println("----------------------");
    }

    /*
        8.	Using findAndDo() print out all people with firstName “Ulf”.
     */
    public static void exercise8(String message){
        System.out.println(message);
        System.out.println(message);
        Predicate<Person> sameName = p -> p.getFirstName().equals("Ulf");
        Consumer<Person> accept = a -> a.toString(); // What is the use of consumer here?

        storage.findAndDo(sameName,accept);
        System.out.println("----------------------");
    }

    /*
        9.	Using findAndDo() print out everyone who have their lastName contain their firstName.
     */
    public static void exercise9(String message){
        System.out.println(message);
        Predicate<Person> sameName = p -> p.getLastName().contains(p.getFirstName());
        Consumer<Person> accept = a -> a.toString(); // What is the use of consumer here?

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
                if(p.getFirstName().charAt(i) !=
                        p.getFirstName().charAt(p.getFirstName().length() - 1 - i)) return false; // Not a palindrome
                System.out.println(p.getFirstName().charAt(p.getFirstName().length() - 1 - i));
            }
            return true;
        };
        Consumer<Person> accept = a -> a.toString();
        storage.findAndDo(palindrome,accept);

        System.out.println("----------------------");
    }

    /*
        11.	Using findAndSort() find everyone whose firstName starts with A sorted by birthdate.
     */
    public static void exercise11(String message){
        System.out.println(message);
        //Write your code here

        System.out.println("----------------------");
    }

    /*
        12.	Using findAndSort() find everyone born before 1950 sorted reversed by lastest to earliest.
     */
    public static void exercise12(String message){
        System.out.println(message);
        //Write your code here

        System.out.println("----------------------");
    }

    /*
        13.	Using findAndSort() find everyone sorted in following order: lastName > firstName > birthDate.
     */
    public static void exercise13(String message){
        System.out.println(message);
        //Write your code here

        System.out.println("----------------------");
    }
}
