package by.client.main;

import by.client.dao.DAOFactory;
import by.client.presentation.Presentation;
import by.client.service.ServiceFactory;

public class Main {
    public static void main(String[] args) {
        ServiceFactory factory = ServiceFactory.getInstance();
        DAOFactory daoFactory = DAOFactory.getInstance();
        Presentation presentation = new Presentation(factory.getStudentService(daoFactory.getStudentDAO()));
        presentation.show();
    }
}
