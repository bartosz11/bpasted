package me.bartosz1.pastebin;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss:SSS");
    //Get timezone from TZ environment variable, use system default if not specified
    //yes, I like ternary expressions
    private static final ZoneId TIMEZONE = System.getenv("TZ") != null ? ZoneId.of(System.getenv("TZ")) : ZoneId.systemDefault();

    @Override
    public String format(LogRecord record) {
        String date = record.getInstant().atZone(TIMEZONE).format(dateTimeFormatter);
        //mfw atomic ref
        AtomicReference<String> threadName = new AtomicReference<>();
        getThread(record.getLongThreadID()).ifPresentOrElse(thread -> threadName.set(thread.getName()), () -> threadName.set("???"));
        return String.format("%s [%s] %s: %s \n", date, threadName.get(), record.getLevel().getName(), record.getMessage());
    }

    //java is sometimes so painful, like JUST GIVE ME THE FKING THREAD NAME INSTEAD OF SOME STUPID ID
    private static Optional<Thread> getThread(long threadId) {
        return Thread.getAllStackTraces().keySet().stream()
                .filter(t -> t.getId() == threadId)
                .findFirst();
    }
}
