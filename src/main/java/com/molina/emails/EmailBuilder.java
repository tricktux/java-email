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

	public Email build() {
		if (props == null) {
			logger.error("Invalid properties");
			return null;
		}

		if (session == null) {
			logger.error("Invalid session");
			return null;
		}

		Email email = new Email();
		email.setFrom(from);
		email.setProperties(props);
		email.setSession(session);

		return email;
	}

	public boolean config(String configFile) {
		if (configFile.isEmpty()) {
			logger.error("Invalid function input");
			return false;
		}

		logger.debug(String.format("Config file = '%s'", configFile));
		try {
			Wini ini = new Wini(new FileReader(configFile));

			from = ini.get("email", "from");
			host = ini.get("email", "host");
			port = ini.get("email", "port");
			ssl = ini.get("email", "ssl", int.class);
			tls = ini.get("email", "tls", int.class);

			props = createProps();

			username = ini.get("user", "username");
			password = ini.get("user", "password");

			session = createSession();
		} catch(Exception e) {
			logger.trace("Exception accessing config file");
			return false;
		}
		return true;
	}

	private Session createSession() {
		return Session.getDefaultInstance(properties,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								user, password);// Specify the Username and the PassWord
					}
				});
	}

	private Properties createProps() {
    Properties properties = System.getProperties();

    // Setup mail server
    properties.setProperty("mail.smtp.auth", "true");
		if (tls)
			properties.setProperty("mail.smtp.starttls.enable", "true");
    properties.setProperty("mail.smtp.host", host);
    properties.setProperty("mail.smtp.port", port);
		if (ssl) {
			properties.setProperty("mail.smtp.socketFactory.port", port);
			properties.setProperty("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
		}

		return properties;
	}

}
