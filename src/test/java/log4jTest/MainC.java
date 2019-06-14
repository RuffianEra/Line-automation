package log4jTest;

import com.LineAssistant.ParamStatic;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainC {
    static ThreadPoolExecutor pool = new ThreadPoolExecutor(0, 10, 20000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque());
    public static void main(String[] args) {
        System.out.println(ParamStatic.prop.getProperty("LineID"));
    }
}
