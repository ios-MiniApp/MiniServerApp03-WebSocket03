package com.example.miniserverapp03websocket03;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import javax.ws.rs.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@ServerEndpoint(value = "/WebSocketServer")
public class WebSocketController {
    // https://qiita.com/opengl-8080/items/7ca8484c8579d264e239#%E3%82%B5%E3%83%BC%E3%83%90%E3%83%BC%E3%81%8B%E3%82%89%E8%87%AA%E7%99%BA%E7%9A%84%E3%81%AB%E3%83%A1%E3%83%83%E3%82%BB%E3%83%BC%E3%82%B8%E3%82%92%E9%80%81%E4%BF%A1%E3%81%99%E3%82%8B
    // ここまでの実装方法だと、サーバー側から自発的にクライアントにメッセージを送ることができない。
    //サーバー側から自発的にメッセージを送信するには、
    // ServerEndpoint の static フィールドに Session を保持しておき、 static メソッドを介して Session にアクセスする。
    private static final Queue<Session> sessions = new ConcurrentLinkedQueue<>();

    static {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(WebSocketController::broadcast, 5, 5, TimeUnit.SECONDS);
    }

    @OnMessage
    public String onMessage(String message) {
        // メッセージを受信した際の処理を実装
        // ネクション上に新しいメッセージが到達したときに呼び出される。
        // メソッドの戻り値は void ならびに特定の型を指定できます。
        // メソッドに戻り値がある場合には、その戻り値がクライアントに送信されます。
        // Javaプリミティブは文字列に変換されてクライアントに送信されます。
        // エンコーダが定義してある任意のクラスはエンコーダを介してクライアントに送信
        System.out.println("kota: WebSocketで受信したメッセージ/ " + message);
        return "WebSocketでメッセージを正常に受信しました！";
    }

    @OnError
    public void onError(Throwable th) {
        // WebSocketエラーが発生した際の処理を実装
        System.out.println("kota: WebSocketエラーが発生/ " + th.getMessage());
    }

    @OnOpen
    public void onOpen(Session session) {
        // セッションが確立した際の処理を実装
        // WebSocket エンドポイントに新たな接続がなされる場合に呼び出される。
        // メソッドは引数なしでも、引数ありでも可能。
        System.out.println("kota: WebSocketセッション確立");
        sessions.add(session);
    }

    // Web ソケットセッションが閉じているときに呼び出される Java メソッドを修飾する
    @OnClose
    public void onClose(Session session) {
        // セッションを終了する際の処理を実装
        System.out.println("kota: WebSocketセッション終了");
        sessions.remove(session);
    }

    public static void broadcast() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        sessions.forEach(session -> {
            session.getAsyncRemote().sendText("Broadcast : " + formatter.format(now));
        });
    }
}