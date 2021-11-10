package by.client.service;

import by.client.entity.Student;
import by.client.entity.user.User;

import java.util.List;

public interface StudentClientService {

    boolean edit(Student newValue);

    List<Student> getAll();

    Student get(int id);

    boolean create(Student student);

    User register(User user);

    User login(User user);
}
