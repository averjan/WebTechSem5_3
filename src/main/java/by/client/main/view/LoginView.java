package by.client.main.view;

import by.client.entity.user.User;
import by.client.service.StudentService;

import java.util.Scanner;

public class LoginView extends PresentationView{
    private boolean loginState = false;

    public LoginView(StudentService studentService) {
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
        if (!this.studentService.login(user)) {
            System.out.println("Error writing: Probably student changed by other client.");
        }

        this.loginState = true;
    }

    @Override
    public PresentationView getInput(String input) {
        return this.loginState ? new AdminView(this.studentService)
                : new IndexView(this.studentService);
    }
}
