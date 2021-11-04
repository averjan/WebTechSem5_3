package by.server.service;

import by.client.entity.Student;

import java.util.Comparator;
import java.util.List;

public interface StudentService {

    /**
     * Searches for appliances by criteria.
     *
     * @param criteria the criteria for search
     * @return list of found appliances
     */
    boolean edit(Student newValue);

    /**
     * Gets sorted by comparator appliances.
     *
     * @param comparator for sorting appliances
     * @return list of sorted by comparator appliances
     */
    List<Student> getAll();

    /**
     * Gets minimal appliances by criteria.
     *
     * @param comparator defines minimal value criteria
     * @return list of appliances with minimal criteria defined by comparator
     */
    Student get(int id);

    /**
     * Appends appliances to model.
     *
     * @param appliances the list of appliances to add to model
     */
    boolean create(Student student);
}
