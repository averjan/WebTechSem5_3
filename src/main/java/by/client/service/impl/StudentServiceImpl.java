package by.client.service.impl;

import by.client.dao.StudentDAO;
import by.client.entity.Student;
import by.client.entity.user.User;
import by.client.service.StudentService;

import java.util.List;

public class StudentServiceImpl implements StudentService {
    private StudentDAO studentDAO;

    public StudentServiceImpl(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    public boolean edit(Student newValue) {
        return this.studentDAO.edit(newValue);
    }

    @Override
    public List<Student> getAll() {
        return this.studentDAO.getAll();
    }

    @Override
    public Student get(int id) {
        return this.studentDAO.get(id);
    }

    @Override
    public boolean create(Student student) {
        return this.studentDAO.create(student);
    }

    @Override
    public boolean register(User user) {
        return this.studentDAO.register(user);
    }

    @Override
    public boolean login(User user) {
        return this.studentDAO.login(user);
    }
}
