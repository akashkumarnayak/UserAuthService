package org.example.userauthservice.producers;

public interface IProducer<T>{

    public void sendMessage(String topic,T message);
}
