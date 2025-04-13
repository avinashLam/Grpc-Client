package com.example.GrpcClient.service;

import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.InvalidProtocolBufferException;
import com.java.grpc.StockRequest;
import com.java.grpc.StockResponse;
import com.java.grpc.StockTradingServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import com.google.protobuf.util.JsonFormat;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    StockTradingServiceGrpc.StockTradingServiceStub stockTradingServiceStub;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @GetMapping(value = "/stock/{Symbol}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter getStockList(@PathVariable String Symbol) {
        SseEmitter sseEmitter = new SseEmitter();
        executorService.execute(() -> {
            StockRequest stockRequest = StockRequest.newBuilder().setStockSymbol(Symbol).build();
            stockTradingServiceStub.getStreamStockprice(stockRequest, new StreamObserver<StockResponse>() {
                @Override
                public void onNext(StockResponse stockResponse) {
                    try {
                        String jsonResponse = JsonFormat.printer().print(stockResponse);
                        sseEmitter.send(jsonResponse);
                    } catch (InvalidProtocolBufferException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                @Override
                public void onError(Throwable throwable) {
                    sseEmitter.completeWithError(throwable);
                }

                @Override
                public void onCompleted() {
                    sseEmitter.complete();
                }
            });



        });
        return sseEmitter;
    }

}

