package com.example.GrpcClient.service;

import com.javatechie.grpc.StockRequest;
import com.javatechie.grpc.StockResponse;
import com.javatechie.grpc.StockTradingServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceGrpcClient{
    @GrpcClient("stockService")
    private StockTradingServiceGrpc.StockTradingServiceBlockingStub stockTradingServiceBlockingStub;

    public StockResponse getStockPrice(String stockSymbol){
     StockRequest stockRequest= StockRequest.newBuilder().setStockSymbol(stockSymbol).build();
     return   stockTradingServiceBlockingStub.getStockPrice(stockRequest);

    }
}
