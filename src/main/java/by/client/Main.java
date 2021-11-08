package by.client;

import by.client.dao.DAOFactory;
import by.client.main.Presentation;
import by.client.main.view.IndexView;
import by.client.service.ServiceFactory;

public class Main {
    public static void main(String[] args) {
        ServiceFactory factory = ServiceFactory.getInstance();
        DAOFactory daoFactory = DAOFactory.getInstance();
        Presentation presentation = new Presentation(new IndexView(factory.getStudentService(daoFactory.getStudentDAO())));
        presentation.show();

    }
}
