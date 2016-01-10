package pl.edu.uj.andriod.Zaliczenie.model;

public enum TaskState {
    NEW("new") {
        @Override
        public String toString() {
            return "czeka";
        }
    }, IN_PROGRESS("in progress") {
        @Override
        public String toString() {
            return "w trakcie";
        }
    }, DONE("done") {
        @Override
        public String toString() {
            return "zrobione";
        }
    };

    String sqlName;

    TaskState(String name) {
        sqlName = name;
    }

    public String getSqlName() {
        return sqlName;
    }

    public static TaskState parse(String string) {
        for (TaskState value : values())
            if (value.sqlName.equals(string))
                return value;
        return null;
    }

}
