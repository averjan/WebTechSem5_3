package by.client.main.view;

import by.client.entity.role.UserRole;
import by.client.entity.user.User;
import by.client.main.view.input.SetInputUser;
import by.client.service.StudentService;
import org.javatuples.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LoginView extends PresentationView{
    private UserRole loginState = UserRole.GUEST;
    private List<Pair<String, SetInputUser>> inputs = Arrays.asList(
            new Pair<>("Login:", (user, input) -> {
                user.setLogin(input);
                return true;
            }),
            new Pair<>("Password:", (user, input) -> {
                user.setPassword(input);
                return true;
            })
    );

    public LoginView(StudentService studentService) {
        super(studentService);
    }

    @Override
    public void show() {
        Scanner scanner = new Scanner(System.in);
        User user = new User();

        int i = 0;
        String input;
        System.out.println("Enter 'quit' to exit.");
        while (i < inputs.size()) {
            System.out.println(inputs.get(i).getValue0());
            input = scanner.nextLine();
            if (input.equals("quit")) {
                return;
            }

            if (inputs.get(i).getValue1().setInput(user, input)) {
                i++;
            } else {
                System.out.println("Invalid input!");
            }
        }

        User auth = this.studentService.login(user);
        if (auth == null) {
            System.out.println("User not found");
        } else {
            this.loginState = auth.getRole();
        }
    }

    @Override
    public PresentationView getInput(String input) {
        return switch (this.loginState) {
            case USER -> new IndexView(this.studentService);
            case ADMIN -> new AdminView(this.studentService);
            case GUEST -> new GuestView(this.studentService);
        };
    }
}
