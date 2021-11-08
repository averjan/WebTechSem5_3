package by.client.main.view;

import by.client.service.StudentService;

public class GuestView extends PresentationView{

    public GuestView(StudentService studentService) {
        super(studentService);
    }

    @Override
    public void show() {

    }

    @Override
    public PresentationView getInput(String input) {
        return null;
    }
}
