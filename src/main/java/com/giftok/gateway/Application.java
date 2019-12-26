package com.giftok.gateway;

import com.giftok.certificate.message.CertificateMessageOuterClass.CertificateMessage;
import com.giftok.certificate.message.CertificateMessageOuterClass.CertificateMessage.Currency;
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

    private static CertificateMessage createCertificate(Request request) {
        int amount = Integer.parseInt(request.queryParams("amount"));
        Currency currency = Currency.valueOf(request.queryParams("currency"));
        var text = request.queryParams("text");
        return CertificateMessage.newBuilder()
                .setCurrency(currency)
                .setAmount(amount)
                .setText(text)
                .build();
    }
}
