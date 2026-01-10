
package models;

public class InputAccount {
    private final int id;
    private final String name;
    private final String address;
    private final String threadName;
    private final String elapsed;

    public InputAccount(int id, String name, String address, String threadName, String elapsed) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.threadName = threadName;
        this.elapsed = elapsed;
    }

    @Override
    public String toString() {
        return "id: " + id + 
                " name: " + name + 
                " address: " + address + 
                " threadName: " + threadName +
                " elapsed: " + elapsed;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getThreadName() { return threadName; }
    public String getElapsed() { return elapsed; }
    public String getAddress() { return address; }
}
