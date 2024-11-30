package ao.co.isptec.aplm.binasjc_app;

public class ChatMessage {
    private String message;
    private String dateTime;
    private boolean isSent;

    public ChatMessage(String dateTime, String message, boolean isSent) {
        this.dateTime = dateTime;
        this.message = message;
        this.isSent = isSent;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }
}
