/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.molina.emails;

import java.io.File;
import org.ini4j.*;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Email {
  private String from = null;
  private String host;
  private String port;
  private String username;
  private String password;
  private int ssl;
  private int tls;

  public boolean sendEmail(String to, String subject, String content) {
    try {
      // Create a default MimeMessage object.
      MimeMessage message = new MimeMessage(session);

      // Set From: header field of the header.
      message.setFrom(new InternetAddress(from));

      // Set To: header field of the header.
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

      // Set Subject: header field
      message.setSubject(subject);

      // Now set the actual message
      message.setText(content);

      // Send message
      Transport.send(message);
      return true;
    } catch (MessagingException mex) {
      mex.printStackTrace();
      return false;
    }
  }

  /**
   * Get from.
   *
   * @return from as String.
   */
  public String getFrom() {
    return from;
  }

  /**
   * Set from.
   *
   * @param from the value to set.
   */
  public void setFrom(String from) {
    this.from = from;
  }

  /**
   * Get host.
   *
   * @return host as String.
   */
  public String getHost() {
    return host;
  }

  /**
   * Set host.
   *
   * @param host the value to set.
   */
  public void setHost(String host) {
    this.host = host;
  }

  /**
   * Get port.
   *
   * @return port as String.
   */
  public String getPort() {
    return port;
  }

  /**
   * Set port.
   *
   * @param port the value to set.
   */
  public void setPort(String port) {
    this.port = port;
  }

  /**
   * Get username.
   *
   * @return username as String.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Set username.
   *
   * @param username the value to set.
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Get password.
   *
   * @return password as String.
   */
  public String getPassword() {
    return password;
  }

  /**
   * Set password.
   *
   * @param password the value to set.
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Get ssl.
   *
   * @return ssl as int.
   */
  public int getSsl() {
    return ssl;
  }

  /**
   * Set ssl.
   *
   * @param ssl the value to set.
   */
  public void setSsl(int ssl) {
    this.ssl = ssl;
  }

  /**
   * Get tls.
   *
   * @return tls as int.
   */
  public int getTls() {
    return tls;
  }

  /**
   * Set tls.
   *
   * @param tls the value to set.
   */
  public void setTls(int tls) {
    this.tls = tls;
  }
}
