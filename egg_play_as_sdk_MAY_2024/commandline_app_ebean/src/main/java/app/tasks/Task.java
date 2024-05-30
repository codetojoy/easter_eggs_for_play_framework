package app.tasks;

public interface Task {
    boolean run();

    default void emitIdentity() {
        System.out.println("TRACER running : " + this.getClass().getSimpleName());
    }
}
