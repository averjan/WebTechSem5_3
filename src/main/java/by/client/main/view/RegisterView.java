package by.client.main.view;

import by.client.entity.user.User;
import by.client.service.StudentService;

import java.util.Scanner;

public class RegisterView extends PresentationView{
    private boolean registerState;

    public RegisterView(StudentService studentService) {
        super(studentService);
    }

    @Override
    public void show() {
        Scanner scanner = new Scanner(System.in);
        User user = new User();
        System.out.println("Login:");
        user.setLogin(scanner.nextLine());
        System.out.println("Password:");
        user.setPassword(scanner.nextLine());
        if (!this.studentService.register(user)) {
            System.out.println("Error authorization");
        }

        this.registerState = true;
    }

    @Override
    public PresentationView getInput(String input) {
        return registerState ? new AdminView(this.studentService)
                : new IndexView(this.studentService);
    }
}
