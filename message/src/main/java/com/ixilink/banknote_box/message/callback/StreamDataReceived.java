package com.ixilink.banknote_box.message.callback;

import com.ha.facecamera.configserver.events.StreamDataReceivedEventHandler;
import com.ixilink.banknote_box.common.spring.SpringUtils;
import com.ixilink.banknote_box.message.common.FacePublicData;
import com.ixilink.banknote_box.message.websocket.VideoSocket;
import org.springframework.context.ApplicationContext;

import java.nio.ByteBuffer;

/**
 * @description: 视频流数据
 * @author: 张皓峰
 * @date: 2019-12-11 15:22
 */
public class StreamDataReceived implements StreamDataReceivedEventHandler {


    private static ApplicationContext applicationContext = SpringUtils.getApplicationContext();
    private static VideoSocket videoSocket = applicationContext.getBean(VideoSocket.class);
//    private static Map<String, LimitQueue<byte[]>> h264s = new ConcurrentHashMap<>();
    private static StreamDataReceived streamDataReceived;
    private StreamDataReceived(){
//        Thread sendThread = new SendThread();
//        sendThread.setDaemon(true);
//        sendThread.start();
    }
    public static StreamDataReceived getInstance(){
        if (streamDataReceived == null ){
            synchronized (StreamDataReceived.class){
                if (streamDataReceived == null){
                    streamDataReceived = new StreamDataReceived();
                }
            }
        }
        return streamDataReceived;
    }

//    private static class SendThread extends Thread {
//        @Override
//        public void run() {
//            while(true) {
//                h264s.entrySet().parallelStream().forEach(kv -> {
//                    byte[] h264 = kv.getValue().poll();
//                    if(h264 != null) {
//                        videoSocket.sendMessage(kv.getKey(),null,ByteBuffer.wrap(h264));
//                    }
//                });
//                try {
//                    Thread.sleep(1);
//                } catch (InterruptedException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    @Override
    public synchronized void onStreamDataReceived(String deviceID, byte[] bytes) {
//        //队列收集1mm发送一次
//        h264s.putIfAbsent(deviceID, new LimitQueue<byte[]>(30)).offer(bytes);

//        if (!FacePublicData.getDataServerState()){
//            FacePublicData.stopStream(deviceID);
//        }
        //每次收到数据直接发送
        videoSocket.sendMessage(deviceID, null, ByteBuffer.wrap(bytes));
    }

}
