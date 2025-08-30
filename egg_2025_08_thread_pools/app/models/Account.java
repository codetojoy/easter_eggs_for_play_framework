
package models;

public class Account {
    private final int id;
    private final String name;
    private final String address;
    private final String threadName;

    public Account(int id, String name, String address, String threadName) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.threadName = threadName;
    }

    @Override
    public String toString() {
        return "id: " + id + " name: " + name + " address: " + address + " threadName: " + threadName;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getThreadName() { return threadName; }
}
