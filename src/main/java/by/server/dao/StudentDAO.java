package by.server.dao;

import by.client.entity.Student;

import java.util.List;

public interface StudentDAO {

    /**
     * Searches for appliances by criteria.
     *
     * @param criteria the criteria for search
     * @return list of found appliances
     */
    void edit(Student newValue);

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
    void create(Student item);
}
