package by.client.main.view;

import by.client.service.StudentService;

public class IndexView extends PresentationView {
    public IndexView(StudentService studentService) {
        super(studentService);
    }

    @Override
    public void show() {
        System.out.println("1: Get\n2: Register\n3: Login\n4: exit");
    }

    @Override
    public PresentationView getInput(String input) {
        return switch (input) {
            case "1" -> new GetSelectView(this.studentService);
            case "2" -> new RegisterView(this.studentService);
            case "3" -> new LoginView(this.studentService);
            case "4" -> null;
            default -> throw new IllegalArgumentException();
        };
    }
}