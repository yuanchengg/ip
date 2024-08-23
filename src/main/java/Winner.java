import java.util.Scanner;
import java.util.ArrayList;

public class Winner {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

        System.out.println(("-".repeat(100) + "\n" +
                "Hello! I am Winner, your personal assistant chatbot!" + "\n" +
                "What can I do for you today?" + "\n" +
                "-".repeat(100)).indent(10));

        while (true) {
            String input = scanner.nextLine();

            if (input.matches("(?i)todo.*")) {
                String description = input.split("todo", 2)[1].trim();
                ToDo newToDo = new ToDo(description);
                tasks.add(newToDo);
                System.out.println(("-".repeat(100) + "\n" +
                        newToDo.addTaskToString() + "\n" +
                        "-".repeat(100)).indent(10));

            } else if (input.matches("(?i)deadline.*")) {
                String[] parts = input.split("(?i)deadline | (?i)by");
                String description = parts[1].trim();
                String by = parts[2].trim();
                Deadline newDeadline = new Deadline(description, by);
                tasks.add(newDeadline);
                System.out.println(("-".repeat(100) + "\n" +
                        newDeadline.addTaskToString() + "\n" +
                        "-".repeat(100)).indent(10));

            } else if (input.matches("(?i)event.*")) {
                String[] parts = input.split("(?i)event | (?i)from | (?i)to");
                String description = parts[1].trim();
                String start = parts[2].trim();
                String end = parts[3].trim();
                Event newEvent = new Event(description, start, end);
                tasks.add(newEvent);
                System.out.println(("-".repeat(100) + "\n" +
                        newEvent.addTaskToString() + "\n" +
                        "-".repeat(100)).indent(10));

            } else if (input.matches("(?i)list")) {
                int counter = 1;
                System.out.println(" ".repeat(10) + "-".repeat(38));
                System.out.println(" ".repeat(10) + "Here are the tasks you have in your list:");
                for (Task i:tasks) {
                    System.out.println(" ".repeat(10) + counter + ". " + i.taskToString());
                    counter++;
                }
                System.out.println(" ".repeat(10) + "-".repeat(38));

            } else if (input.matches("(?i)mark \\d+")) {
                int taskNumber = Integer.parseInt(input.replaceAll("[^0-9]", ""));
                Task task = tasks.get(taskNumber - 1);
                task.markDone();
                System.out.println(("-".repeat(38) + "\n" +
                        task.markDoneToString() + "\n" +
                        "-".repeat(38)).indent(10));

            } else if (input.matches("(?i)unmark \\d+")) {
                int taskNumber = Integer.parseInt(input.replaceAll("[^0-9]", ""));
                Task task = tasks.get(taskNumber - 1);
                task.unmarkDone();
                System.out.println(("-".repeat(38) + "\n" +
                        task.unmarkDoneToString() + "\n" +
                        "-".repeat(38)).indent(10));

            } else if (input.matches("(?i)bye")) {
                System.out.println(("-".repeat(100) + "\n" +
                        "Hope I have been of service!" + "\n" +
                        "Thank you and see you again soon :D" + "\n" +
                        "Remember!!! A WINNER NEVER LOSES!!!" + "\n" +
                        "-".repeat(100)).indent(10));
                break;

            }

            //else {
            //    tasks.add(new Task(input));
            //    System.out.println(("-".repeat(100) + "\n" +
            //            "added: " + input + "\n" +
            //            "-".repeat(100)).indent(10));
            //}

        }
        scanner.close();
    }
}