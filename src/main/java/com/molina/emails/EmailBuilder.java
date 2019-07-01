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

	public Email build(String configFile) {
		if (configFile.isEmpty()) {
			logger.error("Invalid function input");
			return null;
		}

		logger.debug(String.format("Config file = '%s'", configFile));
		try {
			Wini ini = new Wini(new File(configFile));

			String from = ini.get("email", "from");
			String host = ini.get("email", "host");
			String port = ini.get("email", "port");
			int ssl = ini.get("email", "ssl", int.class);
			int tls = ini.get("email", "tls", int.class);

			String username = ini.get("user", "username");
			String password = ini.get("user", "password");

			Email email = new Email();
			email.setFrom(from);
			email.setHost(host);
			email.setPort(port);
			email.setSsl(ssl);
			email.setTls(tls);
			email.setUsername(username);
			email.setPassword(password);

			return email;
		} catch(Exception e) {
			logger.trace("Exception accessing config file");
			return null;
		}
	}



}
