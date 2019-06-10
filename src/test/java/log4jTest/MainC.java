package log4jTest;

import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

public class MainC {
    public static void main(String[] args) {
        Logger log = Logger.getLogger(MainC.class);
        log.addAppender(new AppenderImp(new PatternLayout("%d{yyyy MM dd HH:mm:ss}  %m %n")));
        log.info("123");

    }
}
