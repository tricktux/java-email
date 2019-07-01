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
    File f = new File(configFile);
    if(!f.exists() || f.isDirectory()) {
			logger.error("Provided file does not exists");
			return null;
    }
    try {
      Wini ini = new Wini(f);

      String from = ini.get("email", "from");
      logger.debug(String.format("from = '%s'", from));
      String host = ini.get("email", "host");
      logger.debug(String.format("host = '%s'", host));
      String port = ini.get("email", "port");
      logger.debug(String.format("port = '%s'", port));
      int ssl = ini.get("email", "ssl", int.class);
      logger.debug(String.format("ssl = '%d'", ssl));
      int tls = ini.get("email", "tls", int.class);
      logger.debug(String.format("tls = '%d'", tls));

      String username = ini.get("user", "username");
      logger.debug(String.format("username = '%s'", username));
      String password = ini.get("user", "password");
      logger.debug(String.format("password = '%s'", password));

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
