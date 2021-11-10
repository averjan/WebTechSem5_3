package by.server.service;

import by.server.dao.StudentServerDAO;
import by.server.service.impl.StudentServerServiceImpl;

public class ServiceServerFactory {
    private static final ServiceServerFactory instance = new ServiceServerFactory();

    private ServiceServerFactory() {}

    public StudentServerService getStudentService(StudentServerDAO studentDAO) {

        return new StudentServerServiceImpl(studentDAO);
    }

    public static ServiceServerFactory getInstance() {
        return instance;
    }
}
