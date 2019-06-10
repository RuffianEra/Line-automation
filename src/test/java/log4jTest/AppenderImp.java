package log4jTest;

import org.apache.log4j.Layout;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppenderImp extends WriterAppender {
    public AppenderImp(Layout lay){
        this.layout = lay;
    }

    @Override
    public void addFilter(Filter filter) {
        System.out.println("addFilter");
    }

    @Override
    public Filter getFilter() {
        System.out.println("getFilter");
        return null;
    }

    @Override
    public void clearFilters() {
        System.out.println("clearFilters");
    }

    @Override
    public void close() {
        System.out.println("close");
    }

    @Override
    public void doAppend(LoggingEvent loggingEvent) {
        System.out.println("------------------------------------------");
        System.out.println(loggingEvent.getLevel());
        System.out.println(loggingEvent.getMessage());
        System.out.println(new SimpleDateFormat("yyyy MM dd HH:mm:ss  ").format(new Date()));
        System.out.println("doAppend");
        System.out.println("------------------------------------------");
    }

    @Override
    public void setErrorHandler(ErrorHandler errorHandler) {
        System.out.println("setErrorHandler");
    }

    @Override
    public ErrorHandler getErrorHandler() {
        System.out.println("getErrorHandler");
        return null;
    }

    @Override
    public void setLayout(Layout layout) {
        System.out.println("layout");
    }

    @Override
    public Layout getLayout() {
        System.out.println("getLayout");
        return null;
    }

    @Override
    public void setName(String s) {
        System.out.println("setName");
    }

    @Override
    public boolean requiresLayout() {
        System.out.println("requiresLayout");
        return false;
    }
}