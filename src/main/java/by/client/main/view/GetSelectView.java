package by.client.main.view;

import by.client.entity.Student;
import by.client.service.StudentService;

import java.util.List;

public class GetSelectView extends PresentationView{
    public GetSelectView(StudentService studentService) {
        super(studentService);
    }

    @Override
    public void show() {
        List<Student> studentList = this.studentService.getAll();
        for (Student student : studentList) {
            System.out.println(student);
        }

        System.out.println("Print 'quit' to exit");
        System.out.println("Select student id: ");
    }

    @Override
    public PresentationView getInput(String input) {
        if ("quit".equals(input)) {
            return new IndexView(this.studentService);
        }

        int id;
       try {
        id = Integer.parseInt(input);
       } catch (NumberFormatException ex) {
           throw new IllegalArgumentException();
       }

       return new GetView(this.studentService, id);
    }
}
