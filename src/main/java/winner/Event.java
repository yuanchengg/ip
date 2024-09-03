package winner;

public class Event extends Task {
    protected String start;
    protected String end;

    public Event(String description, String start, String end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    @Override
    public String taskToString() {
        if (isDone) {
            return "[E] [X] " + description + " (" + start + " - " + end + ")";
        }
        return "[E] [ ] " + description + " (" + start + " - " + end + ")";
    }

    @Override
    public String markDone() {
        return super.markDone() + "\n"
                + " ".repeat(5) + "[E] [X] " + description + " (" + start + " - " + end + ")";
    }

    @Override
    public String unmarkDone() {
        return super.unmarkDone() + "\n"
                + " ".repeat(5) + "[E] [ ] " + description + " (" + start + " - " + end + ")";
    }

    @Override
    public String deleteTask() {
        if (isDone) {
            return super.deleteTask() + "\n"
                    + " ".repeat(5) + "[E] [X] " + description + " (" + start + " - " + end + ")";
        }
        return super.deleteTask() + "\n"
                + " ".repeat(5) + "[E] [ ] " + description + " (" + start + " - " + end + ")";
    }

}
