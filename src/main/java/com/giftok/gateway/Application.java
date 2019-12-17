package com.giftok.gateway;

import com.giftok.gateway.topic.CertificateCreationPublisher;
import spark.Request;

import static spark.Spark.port;
import static spark.Spark.post;

public class Application {
    public static void main(String[] args) {
        CertificateCreationPublisher publisher = new CertificateCreationPublisher();

        port(8080);
        post("/createCertificate", (req, res) -> {
            publisher.publishMessage(createCertificate(req).toByteString());
            return "Success";
        });
    }


    private static CertificateProto.Certificate createCertificate(Request request) {
        long amount = Long.parseLong(request.queryParams("amount"));
        CertificateProto.Certificate.Currency currency = CertificateProto.Certificate.Currency.valueOf(request.queryParams("currency"));
        String text = request.queryParams("text");
        return CertificateProto.Certificate.newBuilder()
                .setCurrency(currency)
                .setAmount(amount)
                .setText(text)
                .build();
    }
}
