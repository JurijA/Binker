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

    public String getHourAndMinute() {
        char[] hour_min = new char[5];
        this.getDay().getChars(11, 16, hour_min, 0);
        return String.valueOf(hour_min);
    }

    public String getMonthAndDay() {
        char[] month_day = new char[5];
        this.getDay().getChars(5, 11, month_day, 0);
        return String.valueOf(month_day);
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
