package pl.edu.uj.andriod.Zaliczenie.sql;

public enum TaskTable {
    ID("id"),
    TITLE("title"),
    DESCRIPTION("description"),
    DEADLINE("deadline"),
    STATE("state"), 
    PRIORITY("priority");
    public static final String TABLE_NAME = "tasks";
    public final String sqlName;

    TaskTable(String name) {
        this.sqlName = name;
    }
    
}
