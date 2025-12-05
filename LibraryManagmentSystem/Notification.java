import java.time.LocalDateTime;

public class Notification {

    private String message;
    private LocalDateTime time;

    public Notification(String message) {
        this.message = message;
        this.time = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "[" + time + "] " + message;
    }
}

