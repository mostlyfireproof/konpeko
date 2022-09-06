package mostlyfireproof.sprucelog;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Logs events to stdout and the Minecraft chat
 *
 */
public class SpruceLog implements ILogger {
    final int MAX_LEVEL = 7;
    private final String name;
    private List<LogEntry> log = new ArrayList<LogEntry>();
    int maxShow = MAX_LEVEL;
    int minShow = 0;

    /**
     * Creates the logger
     * @param name name of the thing to log
     */
    public SpruceLog(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public List<LogEntry> getLog(int min, int max) {
        List<LogEntry> output = new ArrayList<>();
        for (LogEntry entry : this.log) {
            if (entry.getSeverityNum() >= min && entry.getSeverityNum() <= max) {
                output.add(entry);
            }
        }
        return output;
    }

    public List<LogEntry> getLog(int min) {
        return this.getLog(min, MAX_LEVEL);
    }

    public List<LogEntry> getLog() {
        return this.log;
    }

    /**
     * Sets a new minimum level of logs to output
     * @param i
     */
    public void setMinShow(int i) {
        this.minShow = i;
    }

    /**
     * Sets a new maximum level of logs to output
     * @param i
     */
    public void setMaxShow(int i) {
        this.maxShow = i;
    }

    /**
     * Dumps the log to text
     * @param min lowest level to export
     * @param max highest level to export
     * @return log as a string
     */
    public String dump(int min, int max) {
        StringBuilder output = new StringBuilder("Begin dump:\n");
        for (LogEntry entry : this.log) {
            if (entry.getSeverityNum() >= min && entry.getSeverityNum() <= max) {
                output.append(this.name).append(entry.toString()).append("\n");
            }
        }
        return output.toString();
    }

    public String dump() {
        return this.dump(0, MAX_LEVEL);
    }

    /**
     * Adds a new log entry
     * @param level severity level
     * @param message message
     */
    private void addEntry(Levels level, String message) {
        LogEntry entry = new LogEntry(level, message);
        log.add(entry);
        if (this.minShow <= entry.getSeverityNum() && entry.getSeverityNum() <= this.maxShow) {
            this.print(entry);
        }
    }

    public void debug(String msg) {
        this.addEntry(Levels.DEBUG, msg);
    }

    public void info(String msg) {
        this.addEntry(Levels.INFO, msg);
    }

    // add in more functions for the other severity levels

    public void error(String msg) {
        this.addEntry(Levels.ERROR, msg);
    }

    /**
     * Prints the log to chat and stdout
     * @param entry
     */
    public void print(LogEntry entry) {
        System.out.println(entry);
        try {
            MinecraftClient.getInstance().player.sendMessage(Text.literal(entry.getMessage()));
        } catch (NullPointerException e) {
            System.out.println(e);
        }
    }

}
