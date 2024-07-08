package com.expense.tracker.user.utility;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Email;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SendEmail {

	@Value("${mailjetPublicKey}")
	private String mailjetPublicKey;

	@Value("${mailjetPrivateKey}")
	private String mailjetPrivateKey;

	@SuppressWarnings("deprecation")
	public void sendSimpleEmail(String fromEmail, String fromName, String toEmail, String subject, String body)
			throws MailjetException {

		MailjetClient client;
		MailjetRequest request;
		MailjetResponse response;
		// client = new MailjetClient("a2ee6a9262b90456d0d1bca5f6a8e936",
		// "b98854ed107194bc54eff09404454727");
		client = new MailjetClient(mailjetPublicKey, mailjetPrivateKey);

		request = new MailjetRequest(Email.resource).property(Email.FROMEMAIL, fromEmail)
				.property(Email.FROMNAME, fromName).property(Email.SUBJECT, subject).property(Email.TEXTPART, body)
				.property(Email.HTMLPART, "<h3>" + body)

				.property(Email.RECIPIENTS, new JSONArray().put(new JSONObject().put("Email", toEmail)));

		response = client.post(request);

		System.out.println(response.getStatus());
		System.out.println(response.getData());

	}

}
