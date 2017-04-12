package plugin;

import java.util.List;

import delta.monstarz.shared.model.Person;


/**
 * The interface for a persistent User data structure.
 */

public interface IUserDAO
{
    /**
     * Adds a person to a persistent data store.
     */
    void addPerson(Person p);

    /**
     * Retrieves a list of all persons in the persistent data store.
     */
    List<Person> getPersons();
}
