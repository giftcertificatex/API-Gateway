package com.giftok.gateway;

import com.giftok.gateway.topic.CertificateCreationPublisher;

import static spark.Spark.get;
import static spark.Spark.port;

public class Application {
    public static void main(String[] args) {
        CertificateCreationPublisher publisher = new CertificateCreationPublisher();

        port(8080);
        get("/publishMessage", (req, res) -> {
            publisher.publishMessage(createCertificate().toByteString());
            return "Success";
        });
    }


    private static CertificateProto.Certificate createCertificate() {
        return CertificateProto.Certificate.newBuilder()
                .setCurrency(CertificateProto.Certificate.Currency.EUR)
                .setAmount(100)
                .setText("Hello")
                .build();
    }
}
