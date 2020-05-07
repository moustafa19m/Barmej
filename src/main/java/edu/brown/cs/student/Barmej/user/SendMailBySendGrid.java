package edu.brown.cs.student.Barmej.user;

import java.io.IOException;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

/**
 * A class that deals with sending out email invites.
 */
public class SendMailBySendGrid {

  /**
   * Constructor for this class.
   */
  public SendMailBySendGrid() {
  };

  /**
   * The fields for this class.
   */
  private static String to = null;
  private static String subject = null;
  private static String textMessage = null;

  /**
   * A getter for the subject field.
   *
   * @return - a String representing the subject field
   */
  public static String getSubject() {
    return subject;
  }

  /**
   * A setter for the subject field.
   *
   * @param subject - a String representing the subject to be set
   */
  public static void setSubject(String subject) {
    SendMailBySendGrid.subject = subject;
  }

  /**
   * A getter for the textMessage field.
   *
   * @return - a String representing the textMessage
   */
  public static String getTextMessage() {
    return textMessage;
  }

  /**
   * A setter for the textMessage field.
   *
   * @param textMessage - a String to be set as the textMessage
   */
  public static void setTextMessage(String textMessage) {
    SendMailBySendGrid.textMessage = textMessage;
  }

  /**
   * A setter for the to field.
   *
   * @return - a String representing the to field
   */
  public static String getTo() {
    return to;
  }

  /**
   * A setter for the to field.
   *
   * @param to - a String to be set as the to field
   */
  public static void setTo(String to) {
    SendMailBySendGrid.to = to;
  }

  /**
   * A main method to send the email invite.
   *
   * @param args - an array of strings representing the arguments to the main
   *             method
   */

  public static void main(String[] args) throws IOException {
    Email from = new Email("muhammad_haider_asif@brown.edu");
    String subjects = subject;
    Email tos = new Email(to);
    Content content = new Content("text/plain", textMessage);
    Mail mail = new Mail(from, subjects, tos, content);

    SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
    Request request = new Request();
    try {
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());
      Response response = sg.api(request);
      System.out.println(response.getStatusCode());
      System.out.println(response.getBody());
      System.out.println(response.getHeaders());
    } catch (IOException ex) {
      throw ex;
    }
  }

}
