
package models;

// this is used as the client-side request and the server-side response
// a bit messy

public class Account {
    private int id;
    private String name = "";
    private String address = "";

    private boolean enrolled;
    private String message = "";

    // for Jackson
    public Account() {}

    // for client side (Foo)
    public Account(String name, String address) {
        this.name = name;
        this.address = address;
    }

    // for client side (Account)
    public Account(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    // for receipt from server
    public Account(int id, String name, boolean enrolled) {
        this.id = id;
        this.name = name;
        this.enrolled = enrolled;
    }

    @Override
    public String toString() {
        return "id: " + id + " name: " + name + " address: " + address + " enrolled: " + enrolled;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public boolean getEnrolled() { return enrolled; }
    public void setEnrolled(boolean enrolled) { this.enrolled = enrolled; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
