package be.kuleuven.objects;

public class ChatMessage {
    private String message, day, time;
    private User user;

    public ChatMessage(String message, String day, String time, User user) {
        this.message = message;
        this.day = day;
        this.time = time;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "user='" + user + '\'' +
                ", day='" + day + '\'' +
                ", time='" + time + '\'' +
                ", message=" + message +
                '}';
    }
}
