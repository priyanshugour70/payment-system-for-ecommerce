package com.cpt.payments.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpt.payments.adapter.TrustlyNotificationAdapter;
import com.cpt.payments.constants.Endpoints;
import com.cpt.payments.pojo.TrustlyNotificationRequest;
import com.cpt.payments.util.LogMessage;

@RestController
@RequestMapping(Endpoints.TRUSTLY)
public class TrustlyNotificationController {

	private static final Logger LOGGER = LogManager.getLogger(TrustlyNotificationController.class);

	@Autowired
	private TrustlyNotificationAdapter trustlyNotificationAdapter;

	@PostMapping(value = Endpoints.NOTIFICATION)
	public ResponseEntity<Void> processNotification(@RequestBody TrustlyNotificationRequest request) {
		LogMessage.setLogMessagePrefix("/TRUSTLY_NOTIFICATION");
		LogMessage.log(LOGGER, " trustly notification request is -> " + request);

		trustlyNotificationAdapter.processNotification(request);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
