package services;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ApiResult {
    @JsonProperty("enrolled")
    private boolean enrolled;

    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private int id;

    @JsonProperty("message")
    private String message;

    public boolean getEnrolled() { return enrolled; }
    public void setEnrolled(boolean enrolled) { this.enrolled = enrolled; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}

