HttpServer01:

```java
public class HttpServer01 {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8801);
        while (true) {
            Socket socket = serverSocket.accept();
            service(socket);
        }

    }

    private static void service(Socket socket) {
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            String body = "hello, nio1";
            printWriter.println("Content-Length:" + body.getBytes().length);
            printWriter.println();
            printWriter.write(body);
            printWriter.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
```

OkHttp客户端

```java
public class OkHttpTest {

    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().build();
        String url = "http://localhost:8801";
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        String result = call.execute().body().string();
        System.out.println(result);
    }
}
```

运行结果：

![image-20210926180747316](https://myblog-imgs.oss-cn-hangzhou.aliyuncs.com/blog/imgs/image-20210926180747316.png)









