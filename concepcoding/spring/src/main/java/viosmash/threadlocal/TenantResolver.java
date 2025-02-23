package viosmash.threadlocal;

public class TenantResolver {
    public static String shareData = "hello world";
    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();


}
