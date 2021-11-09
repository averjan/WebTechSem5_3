package by.client.main.view;

import by.client.entity.user.User;
import by.client.service.StudentService;

public class AdminView extends PresentationView {
    public AdminView(StudentService studentService, User user) {
        super(studentService, user);
    }

    @Override
    public void show() {
        System.out.println("1: Get\n2: Edit\n3: exit");
    }

    @Override
    public PresentationView getInput(String input) {
        return switch (input) {
            case "1" -> new GetSelectView(this.studentService, this.currentUser);
            case "2" -> new EditSelectView(this.studentService, this.currentUser);
            case "3" -> null;
            default -> throw new IllegalArgumentException();
        };
    }
}
