package com.walgi.chatHub.controller;

import com.walgi.chatHub.model.Message;
import com.walgi.chatHub.repository.MessageRepository;
import com.walgi.chatHub.zookeeper.ZooKeeperClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class WebSocketChatController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ZooKeeperClient zooKeeperClient;

    private AtomicInteger currentLoad = new AtomicInteger(0);
    private String serverNodePath;

    public WebSocketChatController() throws UnknownHostException {
        String serverIp = InetAddress.getLocalHost().getHostAddress();
        int serverPort = 8081; // Porta do servidor
        serverNodePath = "/servers/server_" + serverIp + ":" + serverPort;
    }

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public Message send(Message message) throws Exception {
        message.setTimestamp(LocalDateTime.now());
        messageRepository.save(message);
        int newLoad = currentLoad.incrementAndGet(); // Incrementa a carga
        updateLoad(newLoad);
        return message;
    }

    @MessageMapping("/disconnect")
    public void disconnect(Message message) {
        int newLoad = currentLoad.decrementAndGet(); // Decrementa a carga
        updateLoad(newLoad);
    }

    @Scheduled(fixedDelay = 60000) // Atualiza a carga a cada 60 segundos
    public void updateLoadPeriodically() {
        measureSystemLoad();
    }

    private void measureSystemLoad() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        double systemLoad = osBean.getSystemLoadAverage();
        int adjustedLoad = Math.min(100, (int) (systemLoad * 100.0)); // Ajusta para um valor entre 0 e 100
        updateLoad(adjustedLoad);
    }

    public void updateLoad(int newLoad) {
        currentLoad.set(newLoad);
        try {
            String serverIp = InetAddress.getLocalHost().getHostAddress();
            zooKeeperClient.updateServerLoad(serverNodePath, serverIp,newLoad);
        } catch (Exception e) {
            e.printStackTrace(); // Lida com a exceção (pode ser melhorado com logs)
        }
    }
}
