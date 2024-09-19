package winner;

/**
 * Represents the main class of the Winner task tracking bot.
 */
public class Winner {

    /**
     * Returns a greeting message from Winner bot.
     *
     * @return Greeting message
     */
    public String getHelloMessage() {
        return Ui.winnerSaysHi();
    }

    /**
     * Checks if file storing tasks exists, else create file. Loads current tasks, processes user inputs and returns
     * a String representing a response based on the user input.
     *
     * @param input The user input String to be parsed.
     * @return String representing response generated by the bot, based on the user input.
     */
    public String getResponse(String input) {
        TaskList taskList = new TaskList();
        Storage.checkAndCreateFile();
        Storage.loadTasks(taskList.getTasks());
        String reply = "";

        try {
            reply = Parser.parseInput(input, taskList);
        } catch (WinnerException e) {
            reply = e.getMessage();
        }
        Storage.saveTasks(taskList.getTasks());
        return reply;
    }
}
