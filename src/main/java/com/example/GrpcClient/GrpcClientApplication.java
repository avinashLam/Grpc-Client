package com.example.GrpcClient;

import com.example.GrpcClient.service.ClientServiceGrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GrpcClientApplication implements CommandLineRunner {
private	ClientServiceGrpcClient clientServiceGrpcClient;

	public GrpcClientApplication(ClientServiceGrpcClient clientServiceGrpcClient) {
		this.clientServiceGrpcClient = clientServiceGrpcClient;
	}

	public static void main(String[] args) {
		SpringApplication.run(GrpcClientApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		 clientServiceGrpcClient.getStreamStockprice("AAPL");
	}
}
