package com.walgi.chatHub;

import com.walgi.chatHub.zookeeper.ZooKeeperClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.InetAddress;

@EnableDiscoveryClient
@EnableMongoRepositories(basePackages = "com.walgi.chatHub.repository")
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
