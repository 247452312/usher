package top.uhyils.usher.runner;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import top.uhyils.usher.load.MyUrlClassLoader;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月19日 13时53分
 */
public class MyRunnable implements Runnable {


    private final MyUrlClassLoader myUrlClassLoader;

    private final String[] args;

    public MyRunnable(MyUrlClassLoader myUrlClassLoader, String[] args) {
        this.myUrlClassLoader = myUrlClassLoader;
        this.args = args;
        Thread.currentThread().setContextClassLoader(myUrlClassLoader);
    }

    @Override
    public void run() {
        Class<?> myMainClass = null;
        try {
            myMainClass = myUrlClassLoader.getMyMainClass();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        if (myMainClass == null) {
            return;
        }
        try {
            Method main = myMainClass.getDeclaredMethod("main", String[].class);
            main.setAccessible(true);
            Object[] obj = new Object[]{args};
            main.invoke(myMainClass, obj);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
