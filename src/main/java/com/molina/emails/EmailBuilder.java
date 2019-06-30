package com.molina.emails;

import java.io.File;
import org.ini4j.*;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class EmailBuilder {
	final static Log logger = LogFactory.getLog(EmailBuilder.class);

	private String from;
	private String host;
	private String port;
	private String username;
	private String password;
	private int ssl;
	private int tls;

	private Properties props;
	private Session session;

	public Email build(String configFile) {
		if (configFile.isEmpty()) {
			logger.error("Invalid function input");
			return null;
		}

		logger.debug(String.format("Config file = '%s'", configFile));
		try {
			Wini ini = new Wini(new File(configFile));

			from = ini.get("email", "from");
			host = ini.get("email", "host");
			port = ini.get("email", "port");
			ssl = ini.get("email", "ssl", int.class);
			tls = ini.get("email", "tls", int.class);

			createProps();
			if (props == null) {
				logger.error("Failed to create properties");
				return null;
			}


			username = ini.get("user", "username");
			password = ini.get("user", "password");

			session = createSession();
			if (session == null) {
				logger.error("Failed to create session");
				return null;
			}

			Email email = new Email();
			email.setFrom(from);
			email.setProperties(props);
			email.setSession(session);

			return email;
		} catch(Exception e) {
			logger.trace("Exception accessing config file");
			return null;
		}
	}

	private Session createSession() {
		if (props == null)
			return null;
		return Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								username, password);// Specify the Username and the PassWord
					}
				});
	}

	private void createProps() {
    props = new Properties(System.getProperties());

    // Setup mail server
    props.setProperty("mail.smtp.auth", "true");
		if (tls > 0)
			props.setProperty("mail.smtp.starttls.enable", "true");
    props.setProperty("mail.smtp.host", host);
    props.setProperty("mail.smtp.port", port);
		if (ssl > 0) {
			props.setProperty("mail.smtp.socketFactory.port", port);
			props.setProperty("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
		}
	}

}
