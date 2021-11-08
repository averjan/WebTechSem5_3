package by.client.main.view;

import by.client.service.StudentService;

public class AdminView extends PresentationView {
    public AdminView(StudentService studentService) {
        super(studentService);
    }

    @Override
    public void show() {
        System.out.println("1: Get\n2: Edit\n3: exit");
    }

    @Override
    public PresentationView getInput(String input) {
        return switch (input) {
            case "1" -> new GetSelectView(this.studentService);
            case "2" -> new EditSelectView(this.studentService);
            case "3" -> null;
            default -> throw new IllegalArgumentException();
        };
    }
}
