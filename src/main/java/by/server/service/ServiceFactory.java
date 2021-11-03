package by.server.service;

import by.server.dao.StudentDAO;
import by.server.service.impl.StudentServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    /**
     * Private constructor.
     */
    private ServiceFactory() {}

    /**
     * Gets new ApplianceService object.
     *
     * @return mew ApplianceService object.
     */
    public StudentService getStudentService(StudentDAO studentDAO) {

        return new StudentServiceImpl(studentDAO);
    }

    /**
     * Gets current ServiceFactory object.
     *
     * @return instance of ServiceFactory
     */
    public static ServiceFactory getInstance() {
        return instance;
    }
}
