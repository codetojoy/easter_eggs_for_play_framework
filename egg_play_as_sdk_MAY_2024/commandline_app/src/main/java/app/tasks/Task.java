package app.tasks;

import io.ebean.Database;

public interface Task {
    boolean run(Database database);
}
