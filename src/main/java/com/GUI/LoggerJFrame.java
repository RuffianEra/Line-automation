package com.GUI;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggerJFrame extends WriterAppender {
    public void doAppend(LoggingEvent loggingEvent) {
        Chart.textAreaLog.append(new SimpleDateFormat("yyyy MM dd HH:mm:ss  ").format(new Date()) + loggingEvent.getMessage() + "\n");
    }
}
