package by.client.dao;

import by.client.entity.Student;
import by.client.entity.user.User;

import java.util.List;

public interface StudentDAO {

    /**
     * Searches for appliances by criteria.
     *
     * @param criteria the criteria for search
     * @return list of found appliances
     */
    boolean edit(Student newValue);

    /**
     * Get all appliances.
     *
     * @return list of all appliances
     */
    List<Student> getAll();

    /**
     * @param id
     * @return
     */
    Student get(int id);

    /**
     * Saves appliances into db.
     *
     * @param appliances list of appliances to save into db
     */
    boolean create(Student item);

    boolean register(User user);
    boolean login(User user);
}
