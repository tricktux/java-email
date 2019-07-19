package com.patientdocconnect.model;

import java.io.File;
import org.ini4j.*;

import java.util.*;
import javax.mail.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** Reads properties from configuration file to build Email class
 * */
class EmailBuilder {
  final static Log logger = LogFactory.getLog(EmailBuilder.class);

  private String from; /// Email address from which emails will be sent
  private String host; /// Host SMTP server
  private String port; /// Port for server
  private String username; /// Login username
  private String password; /// Login Password
  private int ssl; /// Use ssl to login to the server
  private int tls; /// Use tls to login to the server

  /**
   * @brief Reads config file to build Email class
   *
   * @param configFile Path to the configuration file
   * @return Email instance if all parameters are valid, Null otherwise
   */
  public Email build(String configFile) {
    if (configFile.isEmpty()) {
      logger.error("Invalid function input");
      return null;
    }

    logger.debug(String.format("Config file = '%s'", configFile));

    File file = new File(configFile);
    if(!file.exists() || file.isDirectory()) {
      logger.error("Provided file does not exists");
      return null;
    }

    try {
      Wini ini = new Wini(file);

      from = ini.get("email", "from");
      logger.debug(String.format("from = '%s'", from));
      host = ini.get("email", "host");
      logger.debug(String.format("host = '%s'", host));
      port = ini.get("email", "port");
      logger.debug(String.format("port = '%s'", port));
      ssl = ini.get("email", "ssl", int.class);
      logger.debug(String.format("ssl = '%d'", ssl));
      tls = ini.get("email", "tls", int.class);
      logger.debug(String.format("tls = '%d'", tls));

      username = ini.get("user", "username");
      logger.debug(String.format("username = '%s'", username));
      password = ini.get("user", "password");
      logger.debug(String.format("password = '%s'", password));

      Properties props = createProps();
      if (props == null) {
        logger.error("Failed to create properties");
        return null;
      }

      Session session = createSession(props);
      if (session == null) {
        logger.error("Failed to create session");
        return null;
      }

      Email email = new Email();
      email.setFrom(from);
      email.setSession(session);

      return email;
    } catch(Exception e) {
      logger.error("Exception accessing config file", e);
      return null;
    }
  }

  /**
   * @brief Create login session using provided username and password
   *
   * @return Session instance, Null if failure
   */
  private Session createSession(Properties properties) {
    if (properties == null) {
      logger.error("Invalid properties");
      return null;
    }

    try {
      return Session.getDefaultInstance(properties,
      new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(
                   username, password);// Specify the Username and the PassWord
        }
      });
    } catch(Exception e) {
      logger.error("Exception creating session", e);
      return null;
    }
  }

  /**
   * @brief Create properties that will be used later for login session
   *
   * @return Properties instance
   */
  private Properties createProps() {
    try {
      Properties props = System.getProperties();

      // Setup mail server
      props.setProperty("mail.smtp.auth", "true");
      if (tls > 0) {
        props.setProperty("mail.smtp.starttls.enable", "true");
      }

      props.setProperty("mail.smtp.host", host);
      props.setProperty("mail.smtp.port", port);

      if (ssl > 0) {
        props.setProperty("mail.smtp.socketFactory.port", port);
        props.setProperty("mail.smtp.socketFactory.class",
                          "javax.net.ssl.SSLSocketFactory");
      }

      return props;
    } catch(Exception e) {
      logger.error("Exception creating properties", e);
      return null;
    }
  }

}
