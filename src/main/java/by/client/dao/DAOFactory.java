package by.client.dao;

import by.client.dao.impl.StudentDAOImpl;
import by.client.entity.Student;

public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();

    /**
     * Private constructor.
     */
    private DAOFactory() {}

    /**
     * Gets new ApplicanceDAO object.
     *
     * @return new ApplianceDAO object.
     */
    public StudentDAO getStudentDAO() {
        return new StudentDAOImpl();
    }

    /**
     * Gets current DAOFactory object.
     *
     * @return instance of DAOFactory
     */
    public static DAOFactory getInstance() {
        return instance;
    }
}
