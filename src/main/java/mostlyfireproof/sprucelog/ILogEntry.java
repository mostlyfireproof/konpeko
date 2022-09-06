package mostlyfireproof.sprucelog;

import java.time.LocalTime;

public interface ILogEntry {
    LocalTime getTime();
    String getMessage();
    Levels getSeverity();
    int getSeverityNum();
}
