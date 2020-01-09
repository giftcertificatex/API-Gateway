package com.giftok.gateway;

import static spark.Spark.options;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.get;

import com.giftok.certificate.message.CertificateMessageOuterClass.CertificateMessage;
import com.giftok.certificate.message.CertificateMessageOuterClass.CertificateMessage.Currency;
import com.giftok.certificate.message.CertificateMessageOuterClass.CertificateMessage.User;
import com.giftok.gateway.payload.CertCreationInfo;
import com.giftok.gateway.response.StandardResponse;
import com.giftok.gateway.response.StatusResponse;
import com.giftok.gateway.topic.CertificateCreationPublisher;
import com.google.gson.Gson;

import spark.Request;
import spark.Response;

public class Application {
	public static void main(String[] args) {
		CertificateCreationPublisher publisher = new CertificateCreationPublisher();

		int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8081"));
		port(port);
		get("/", (req, res) -> {
			res.status(200);
			setupResponseHeaders(res);
			return "OK";
		});
		options("/createCertificate", (req, res) -> {
			res.status(200);
			setupResponseHeaders(res);
			return "Success";
		});
		post("/createCertificate", (req, res) -> {
			setupResponseHeaders(res);
			res.type("application/json");
			try {
				CertificateMessage cerCreationMessage = createCertificate(req);
				publisher.publishMessage(cerCreationMessage.toByteString());
				res.status(200);
				return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
			} catch (Exception e) {
				res.status(500);
				e.printStackTrace();
				return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, e.getMessage()));
			}
		});
	}

	private static void setupResponseHeaders(Response res) {
		res.header("Access-Control-Allow-Headers", "x-requested-with, x-requested-by, Content-Type");
		res.header("Access-Control-Allow-Origin", "*");// "http://localhost:8080"
	}

	private static CertificateMessage createCertificate(Request request) {
		CertCreationInfo certInfo = new Gson().fromJson(request.body(), CertCreationInfo.class);
		Currency currency = Currency.forNumber(certInfo.getCurrencyType());
		User user = CertificateMessage.User.newBuilder().setEmail(certInfo.getEmail()).build();
		return CertificateMessage.newBuilder().setCurrency(currency).setAmount(certInfo.getAmount()).setOwner(user)
				.setId(certInfo.getCardHash()).setCardHash(certInfo.getCardHash()).build();
	}
}
