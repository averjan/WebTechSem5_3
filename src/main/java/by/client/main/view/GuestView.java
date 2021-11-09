package by.client.main.view;

import by.client.service.StudentService;

public class GuestView extends PresentationView{

    public GuestView(StudentService studentService) {
        super(studentService);
    }

    @Override
    public void show() {
        System.out.println("1: Register\n2: Login\n3: exit");
    }

    @Override
    public PresentationView getInput(String input) {
        return switch (input) {
            case "1" -> new RegisterView(this.studentService);
            case "2" -> new LoginView(this.studentService);
            case "3" -> null;
            default -> throw new IllegalArgumentException();
        };
    }
}
