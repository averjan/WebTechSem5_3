package by.server.service.impl;

import by.client.entity.Student;
import by.server.dao.StudentDAO;
import by.server.service.StudentService;

import java.util.List;

public class StudentServiceImpl implements StudentService {
    private final StudentDAO studentDAO;

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
        return studentDAO.get(id);
    }

    @Override
    public boolean create(Student student) {
        return studentDAO.create(student);
    }
}
