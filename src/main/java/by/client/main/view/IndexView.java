package by.client.main.view;

import by.client.service.StudentService;

public class IndexView extends PresentationView {
    public IndexView(StudentService studentService) {
        super(studentService);
    }

    @Override
    public void show() {
        System.out.println("1: Get\n2: exit");
    }

    @Override
    public PresentationView getInput(String input) {
        return switch (input) {
            case "1" -> new GetSelectView(this.studentService);
            case "2" -> null;
            default -> throw new IllegalArgumentException();
        };
    }
}