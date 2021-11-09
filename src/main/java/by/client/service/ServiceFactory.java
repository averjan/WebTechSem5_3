package by.client.service;

import by.client.dao.StudentDAO;
import by.client.service.StudentService;
import by.client.service.impl.StudentServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private ServiceFactory() {}

    public StudentService getStudentService(StudentDAO studentDAO) {

        return new StudentServiceImpl(studentDAO);
    }

    public static ServiceFactory getInstance() {
        return instance;
    }
}
