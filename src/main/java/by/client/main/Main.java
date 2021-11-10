package by.client.main;

import by.client.dao.ClientDAOFactory;
import by.client.presentation.Presentation;
import by.client.service.ServiceClientFactory;

public class Main {
    public static void main(String[] args) {
        ServiceClientFactory factory = ServiceClientFactory.getInstance();
        ClientDAOFactory daoFactory = ClientDAOFactory.getInstance();
        Presentation presentation = new Presentation(factory.getStudentService(daoFactory.getStudentDAO()));
        presentation.show();
    }
}
