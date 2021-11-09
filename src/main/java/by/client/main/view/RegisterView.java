package by.client.main.view;

import by.client.entity.role.UserRole;
import by.client.entity.user.User;
import by.client.main.view.input.SetInputUser;
import by.client.service.StudentService;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.javatuples.Pair;

public class RegisterView extends PresentationView{
    private UserRole registerState = UserRole.GUEST;
    private List<Pair<String, SetInputUser>> inputs = Arrays.asList(
            new Pair<>("Login:", (user, input) -> {
                user.setLogin(input);
                return true;
            }),
            new Pair<>("Password:", (user, input) -> {
                user.setPassword(input);
                return true;
            }),
            new Pair<>("Choose role(1: Admin 2: User quit: Exit)", (user, input) -> {
                if (input.equals("1")) {
                    user.setRole(UserRole.ADMIN);
                    return true;
                } else if (input.equals("2")) {
                    user.setRole(UserRole.USER);
                    return true;
                }

                return false;
            })
    );

    public RegisterView(StudentService studentService) {
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
        /*
        System.out.println("Login:");
        user.setLogin(scanner.nextLine());

        System.out.println("Password:");
        user.setPassword(scanner.nextLine());

        System.out.println("Choose role(1: Admin 2: User quit: Exit)");
        String role;
        while (!(role = scanner.nextLine()).equals("quit")) {
            if (role.equals("1")) {
                user.setRole(UserRole.ADMIN);
                break;
            } else if (role.equals("2")) {
                user.setRole(UserRole.USER);
                break;
            }
        }

        if (role.equals("quit")) {
            return;
        }
        */

        User auth = this.studentService.register(user);
        if (auth == null) {
            System.out.println("User exists.");
        } else {
            this.registerState = auth.getRole();
        }
    }

    @Override
    public PresentationView getInput(String input) {
        return switch (this.registerState) {
            case USER -> new IndexView(this.studentService);
            case ADMIN -> new AdminView(this.studentService);
            case GUEST -> new GuestView(this.studentService);
        };
    }
}
