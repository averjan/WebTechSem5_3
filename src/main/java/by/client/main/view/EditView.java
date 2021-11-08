package by.client.main.view;

import by.client.entity.Student;
import by.client.main.viewModel.EditModelView;
import by.client.service.StudentService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class EditView extends PresentationView{
    public EditView(StudentService studentService, int id) {
        super(studentService);
        this.model = new EditModelView(studentService, id);
    }

    @Override
    public void show() {
        Student student;
        Scanner scanner = new Scanner(System.in);
        List<Student> items = this.model.getItems();
        if (!items.isEmpty()) {
            student = items.get(0);
        } else {
            System.out.println("Not found.");
            return;
        }

        System.out.println("Print name: ");
        student.setName(scanner.nextLine());
        System.out.println("Birthday: ");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            String stringDate = scanner.nextLine();
            try {
                student.setBirthday(LocalDate.parse(stringDate, dateTimeFormatter));
                break;
            } catch (DateTimeParseException ex) {
                System.out.println("Invalid format");
            }
        }

        System.out.println("Characteristic: ");
        student.setCharacteristic(scanner.nextLine());

        student.setLastModification(LocalDateTime.now());
        if (!this.studentService.edit(student)) {
            System.out.println("Error writing: Probably student changed by other client.");
        }
    }

    @Override
    public PresentationView getInput(String input) {
        return new EditSelectView(this.studentService);
    }
}
