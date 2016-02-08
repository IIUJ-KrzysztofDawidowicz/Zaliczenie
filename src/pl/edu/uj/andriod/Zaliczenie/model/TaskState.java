package pl.edu.uj.andriod.Zaliczenie.model;

public enum TaskState {
    NEW("new", "czeka"), IN_PROGRESS("in progress", "w trakcie"), DONE("done", "zrobione");

    final String sqlName;
    final String displayName;

    TaskState(String name, String displayName) {
        sqlName = name;
        this.displayName = displayName;
    }

    public String getSqlName() {
        return sqlName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static TaskState parse(String string) {
        for (TaskState value : values())
            if (value.sqlName.equals(string))
                return value;
        return null;
    }
}
