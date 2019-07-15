package log4jTest;

import Android.T1;
import com.LineAssistant.ParamStatic;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseListener;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainC {
    static ThreadPoolExecutor pool = new ThreadPoolExecutor(0, 10, 20000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque());

    public static void main(String[] args) throws Exception {
        T1.T2();
    }
    public static void event(){
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
                public void eventDispatched(AWTEvent event) {
                    System.out.println(event.getID());
            }
        }, AWTEvent.KEY_EVENT_MASK);
    }
}
