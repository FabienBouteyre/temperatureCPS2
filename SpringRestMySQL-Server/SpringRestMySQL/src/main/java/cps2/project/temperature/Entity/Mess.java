package cps2.project.temperature.Entity;

public class Mess {

    private String name = "";
    private String message = "";
    private String url = "";

    public Mess() {
    }

    public Mess(String message) {
        this.message = message;
    }

    public Mess(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public Mess(String name, String message, String url) {
        this.name = name;
        this.message = message;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
