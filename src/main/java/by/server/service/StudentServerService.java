package by.server.service;

import by.client.entity.Student;
import by.client.entity.user.User;

import java.util.List;

public interface StudentServerService {

    boolean edit(Student newValue);

    List<Student> getAll();

    Student get(int id);

    boolean create(Student student);

    User login(User user);

    User register(User user);
}
