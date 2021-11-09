package by.server.service;

import by.server.dao.StudentDAO;
import by.server.service.impl.StudentServiceImpl;

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
