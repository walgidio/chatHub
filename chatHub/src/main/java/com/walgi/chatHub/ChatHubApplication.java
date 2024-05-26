package com.walgi.chatHub;

import com.walgi.chatHub.zookeeper.ZooKeeperClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;

@SpringBootApplication
public class ChatHubApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(ChatHubApplication.class, args);
		ZooKeeperClient zookeeperClient = new ZooKeeperClient(); // Certifique-se de ter adicionado a biblioteca do ZooKeeper
		String serverIp = InetAddress.getLocalHost().getHostAddress(); // Obtém o IP do servidor
		int serverPort = 8081; // Porta do servidor
		String serverPath = "/servers/server_" + serverIp + ":" + serverPort;
		zookeeperClient.registerServer(serverPath, serverIp, 0);
	}
}
