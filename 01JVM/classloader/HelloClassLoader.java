package jvm;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author itguoy
 * @date 2021-09-14 15:57
 */
public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) {
        try {
            String clsName = "Hello";
            Object obj = new HelloClassLoader().findClass(clsName).newInstance();
            Method helloMethod = obj.getClass().getDeclaredMethod("hello");
            helloMethod.setAccessible(true);
            helloMethod.invoke(obj);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String filePath = "E:\\workspace-my\\geek-learn\\01JVM\\jvm-classloader\\src\\jvm\\Hello.xlass";
        byte[] helloBytes = getFileByte(filePath);
        for (int i = 0; i < helloBytes.length; i++) {
            helloBytes[i] = (byte) ((byte) 255 - helloBytes[i]);
        }
        return defineClass(name, helloBytes, 0, helloBytes.length);
    }

    public byte[] getFileByte(String filePath) {
        if (filePath == null) {
            return null;
        }
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
