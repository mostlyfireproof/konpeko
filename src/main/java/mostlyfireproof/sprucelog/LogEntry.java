package mostlyfireproof.sprucelog;

import java.time.LocalTime;
import java.time.format.DateTimeFormatterBuilder;

/**
 * Represents a Cisco-styled log entry
 */
public class LogEntry implements ILogEntry {
    Levels severity;
    LocalTime time;
    String message;

    public LogEntry(Levels severity, String message) {
        this.time = LocalTime.now();
        this.severity = severity;
        this.message = message;
    }

    public LogEntry(Levels severity, String message, LocalTime time) {
        this.time = time;
        this.severity = severity;
        this.message = message;
    }

    public LocalTime getTime() {
        return this.time;
    }

    public Levels getSeverity() {
        return this.severity;
    }

    public int getSeverityNum() {
        switch (this.severity) {
            case EMERGENCY:
                return 0;
            case ALERT:
                return 1;
            case CRITICAL:
                return 2;
            case ERROR:
                return 3;
            case WARN:
                return 4;
            case NOTIFY:
                return 5;
            case INFO:
                return 6;
            case DEBUG:
                return 7;
            default:
                return -1;
        }
    }

    public String getMessage() {
        return this.message;
    }

    public String toString() {
        String s = this.time.toString() + " ";
        s += this.getSeverityNum() + " - ";
        s += this.message;
        return s;
    }

}
