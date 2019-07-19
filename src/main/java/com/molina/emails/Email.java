/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.patientdocconnect.model;

import javax.mail.*;
import javax.mail.internet.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** @brief Class responsible for sending emails
 *
 * @see EmailBuilder to properly instantiate this class
 * */
public class Email {
  private Session session = null;       /// Contains login information
  private String from = null;           /// Sender of the email

  final static Log logger = LogFactory.getLog(Email.class);

  /**
   * @brief Validate email address
   *
   * @param email Address to be validated
   * @return True if address is valid, False otherwise
   */
  private boolean isValidEmailAddress(String email) {
    boolean result = true;
    try {
      InternetAddress emailAddr = new InternetAddress(email);
      emailAddr.validate();
    } catch (AddressException ex) {
      result = false;
    }
    return result;
  }

  /**
   * @brief Send Email
   *
   * @param to Recipient email address
   * @param subject Email subject
   * @param content Email content
   *
   * @return True if able to send email successfully, False otherwise
   */
  public boolean sendEmail(String to, String subject, String content) {
    try {
      if (!isValidEmailAddress(to)) {
        logger.error(String.format("Invalid to '%s' email address provided",
                                   to));
        return false;
      }

      if ((to == null) || (to.isEmpty())) {
        logger.error("Invalid from email address");
        return false;
      }

      if (session == null) {
        logger.error("Failed to set email session");
        return false;
      }

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
      logger.trace("Exception sending email");
      mex.printStackTrace();
      return false;
    }
  }


  /**
   * Get session.
   *
   * @return session as Session.
   */
  public Session getSession() {
    return session;
  }

  /**
   * Set session.
   *
   * @param session the value to set.
   */
  public void setSession(Session session) {
    this.session = session;
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
}
