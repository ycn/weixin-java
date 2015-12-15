package cc.ycn.common.util;

/**
 * Created by andy on 7/14/15.
 */
public class DebugTool {

    public static String getCallerName(Exception e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        return stackTrace[0].toString();
    }
}
