package se.lexicon.data;


import se.lexicon.model.Person;
import se.lexicon.util.PersonGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 * Create implementations for all methods. I have already provided an implementation for the first method *
 */
public class DataStorageImpl implements DataStorage {

    private static final DataStorage INSTANCE;

    static {
        INSTANCE = new DataStorageImpl();
    }

    private final List<Person> personList;

    private DataStorageImpl() {
        personList = PersonGenerator.getInstance().generate(800);
    }

    static DataStorage getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Person> findMany(Predicate<Person> filter) {

        List<Person> result = new ArrayList<>();
        for (Person person : personList) {
            if (filter.test(person)) {
                result.add(person);
            }
        }
        return result; // only the persons who passed the test
    }

    @Override
    public Person findOne(Predicate<Person> filter) { // If we want a specific person

        for (Person person : personList) {
            if (filter.test(person)) {
                return person;
            }
        }
        return null;
    }

    @Override
    public String findOneAndMapToString(Predicate<Person> filter, Function<Person, String> personToString) {

        return personToString.apply(findOne(filter)); // When we need a custom outprint or something
    }

    @Override
    public List<String> findManyAndMapEachToString(Predicate<Person> filter, Function<Person, String> personToString) {

        List<String> selection = new ArrayList<>(); // Is it a better way to do this calling findMany?
        for (Person person : personList) {
            if (filter.test(person)) {
                selection.add(personToString.apply(person));
            }
        }
        return selection;
    }

    @Override
    public void findAndDo(Predicate<Person> filter, Consumer<Person> consumer) {

        for (Person person : findMany(filter)) {
            if (filter.test(person)) {
                consumer.accept(person);
            }
        }
    }

    @Override
    public List<Person> findAndSort(Comparator<Person> comparator) {

        List<Person> selection = personList;
        selection.sort(comparator); // This might be sorting both selection and personList?
        return selection;
    }

    @Override
    public List<Person> findAndSort(Predicate<Person> filter, Comparator<Person> comparator) {

        List<Person> selection = findMany(filter);
        selection.sort(comparator);
        return selection;
    }
}