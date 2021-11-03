package by.server.service.impl;

import by.server.dao.DAOFactory;
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
    public void edit(Student newValue) {
        this.studentDAO.edit(newValue);
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
    public void create(Student student) {
        studentDAO.create(student);
    }
}
