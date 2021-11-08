package by.client.main;

import by.client.main.view.PresentationView;

import java.util.Scanner;

public class Presentation {
    private PresentationView view;
    public Presentation(PresentationView view) {
        this.view = view;
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
