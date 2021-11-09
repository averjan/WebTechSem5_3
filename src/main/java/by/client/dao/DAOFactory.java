package by.client.dao;

import by.client.dao.impl.StudentDAOImpl;

public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();

    private DAOFactory() {}

    public StudentDAO getStudentDAO() {
        return new StudentDAOImpl();
    }

    public static DAOFactory getInstance() {
        return instance;
    }
}
