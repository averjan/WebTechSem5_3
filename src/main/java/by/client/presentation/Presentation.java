package by.client.presentation;

import by.client.entity.role.UserRole;
import by.client.entity.user.User;
import by.client.presentation.view.GuestView;
import by.client.presentation.view.PresentationView;
import by.client.service.StudentService;

import java.util.Scanner;

public class Presentation {
    private PresentationView view;
    private User currentUser = null;
    public Presentation(StudentService studentService) {
        this.currentUser = new User();
        this.currentUser.setRole(UserRole.GUEST);
        this.view = new GuestView(studentService, this.currentUser);
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        while (this.view != null) {
            this.view.show();
            while (!getInput(scanner.nextLine())) {
                System.out.println("Invalid input");
            }
        }
    }

    private boolean getInput(String input) {
        try {
            this.view = this.view.getInput(input);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
