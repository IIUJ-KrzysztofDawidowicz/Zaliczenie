package pl.edu.uj.andriod.Zaliczenie.model;

import java.util.Date;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static pl.edu.uj.andriod.Zaliczenie.model.TaskState.*;


public final class Task {
    // Required fields
    private final String title;
    private final String description;
    private TaskState state;
    private boolean priority = false;

    // Optional fields
    private Long id; // optional for new tasks
    private Date deadline;

    public Task(String title, String description) {
        this.title = requireNonNull(title);
        this.description = requireNonNull(description);
        this.state = NEW;
    }

    public Long getId() {
        return id;
    }

    public Task setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public Task setDeadline(Date deadline) {
        this.deadline = deadline;
        return this;
    }

    public TaskState getState() {
        return state;
    }

    public Task setState(TaskState state) {
        this.state = state;
        return this;
    }

    @Override
    public String toString() {
        return String.format("%s%n%s%nStan: %s%nData: %tR", title, description, state, deadline);
    }

    public void nextState() {
        switch (state) {
            case NEW:
                state = IN_PROGRESS;
                break;
            case IN_PROGRESS:
                state = DONE;
                break;
            default:
            	state = NEW;
                break;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (getClass() != o.getClass()) return false;
        Task other = (Task) o;
        return Objects.equals(id, other.id) &&
                Objects.equals(title, other.title) &&
                Objects.equals(description, other.description) &&
                Objects.equals(state, other.state) &&
                Objects.equals(deadline, other.deadline);

    }

    public boolean isPriority() {
        return priority;
    }

    public Task setPriority(boolean priority) {
        this.priority = priority;
        return this;
    }
}
