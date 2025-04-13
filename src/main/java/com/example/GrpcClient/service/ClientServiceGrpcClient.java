package com.example.GrpcClient.service;


import com.java.grpc.StockRequest;
import com.java.grpc.StockResponse;
import com.java.grpc.StockTradingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;


@Service
public class ClientServiceGrpcClient{
    //Unary Stream Processing
//    @GrpcClient("stockService")
//    private StockTradingServiceGrpc.StockTradingServiceBlockingStub stockTradingServiceBlockingStub;
//
//
//    public StockResponse getStockPrice(String stockSymbol){
//     StockRequest stockRequest= StockRequest.newBuilder().setStockSymbol(stockSymbol).build();
//     return   stockTradingServiceBlockingStub.getStockPrice(stockRequest);
//
//    }

    //Server Stream

    @GrpcClient("stockService")
    private StockTradingServiceGrpc.StockTradingServiceStub stockTradingServiceStub;

    public void getStreamStockprice(String stockSymbol){
             StockRequest stockRequest= StockRequest.newBuilder().setStockSymbol(stockSymbol).build();
stockTradingServiceStub.getStreamStockprice(stockRequest, new StreamObserver<StockResponse>() {
    @Override
    public void onNext(StockResponse stockResponse) {
        System.out.println("ClientServiceGrpcClient.onNext"+stockResponse.getStockSymbol()+stockResponse.getPrice()+stockResponse.getTimestamp());
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("ClientServiceGrpcClient.onError");
    }

    @Override
    public void onCompleted() {
        System.out.println("ClientServiceGrpcClient.onCompleted");

    }
});



    }
}
