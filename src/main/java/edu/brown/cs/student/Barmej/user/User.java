package edu.brown.cs.student.Barmej.user;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.Cookie;

import edu.brown.cs.student.Barmej.databases.Database;

/**
 * A class representing User objects (extended by the Student and Administrator
 * classes).
 */
public abstract class User {

  /**
   * The fields for this class.
   */
  private String id;
  private String type;
  private static Connection conn = Database.getConn();
  private static Map<String, User> cookieMap = new HashMap<>();
  private static Map<String, String> emailMap = new HashMap<>();
  private static Set<String> usedIDs = new HashSet<>();

  /**
   * A constructor for this class.
   *
   * @param id - a String representing the id of the User being created
   */
  public User(String id) {
    this.setId(id);
  }

  /**
   * An abstract method to get the user name of the User.
   *
   * @return - a String representing the user name
   */
  public abstract String getUserName();

  /**
   * An abstract method to get the full name of the User.
   *
   * @return - a String representing the full name
   */
  public abstract String getFullName();

  /**
   * An abstract method to get the Institution the User belongs to.
   *
   * @return - an Institution object representing the institution the user belongs
   *         to
   */
  public abstract Institution getInstitution();

  /**
   * A getter for the emailMap field.
   *
   * @return - a Map representing the emailMap field
   */
  public static Map<String, String> getEmailMap() {
    return emailMap;
  }

  /**
   * A setter for the emailMap field.
   *
   * @param cookieMapp - the Map to be set as the emailMap
   */
  public static void setEmailMap(Map<String, String> cookieMapp) {
    User.emailMap = cookieMapp;
  }

  /**
   * A getter for the cookieMap field.
   *
   * @return - a Map representing the cookieMap
   */
  public static Map<String, User> getCookieMap() {
    return cookieMap;
  }

  /**
   * A setter for the cookieMap field.
   *
   * @param cookieMap - a Map to be set as the cookieMap
   */
  public static void setCookieMap(Map<String, User> cookieMap) {
    User.cookieMap = cookieMap;
  }

  /**
   * A getter for the type field of the User.
   *
   * @return - a String representing the type of the user
   */
  public String getType() {
    return this.type;
  }

  /**
   * A setter for the type field of the User.
   *
   * @param type - a String representing the type to be set
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * A getter for the id field of the User.
   *
   * @return - a String representing the id of the user
   */
  public String getId() {
    return this.id;
  }

  /**
   * Setter for the id field of the User.
   *
   * @param id - a String representing the id to be set as the id field
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * A method to get all the IDs for which a User already exists in the database,
   * to populate the usedIDs field.
   */
  public static void getIDs() {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement("SELECT id from user");
      prep.executeUpdate();
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        String id = rs.getString(1);
        usedIDs.add(id);
      }
      rs.close();
      prep.close();
    } catch (SQLException e) {
      System.err.println("get ids in user");
    }
  }

  /**
   * A method to get the ID of a User given their user name.
   *
   * @param username - a String representing the user name of the User
   * @return - a String representing the ID that is found
   */
  public static String findIdFromUsername(String username) {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement("SELECT id from user WHERE username = ?");
      prep.setString(1, username);
      ResultSet rs = prep.executeQuery();
      String value = rs.getString(1);
      rs.close();
      prep.close();
      return value;
    } catch (SQLException e) {
      System.err.println("get id from username in user SQL malfunction");
    }
    return null;
  }

  /**
   * A method that deals with the logging in of a User.
   *
   * @param inputs - a list of strings representing the inputs entered by the user
   *               on the front end, into the login prompt
   * @return - a User corresponding to the entered login information
   * @throws NoSuchProviderException  - when the provider does not exist
   * @throws NoSuchAlgorithmException - when the algorithm does not exist
   */
  public static User logIn(List<String> inputs)
      throws NoSuchProviderException, NoSuchAlgorithmException {
    String username = inputs.get(0);
    String password = Password.getSecurePassword(inputs.get(1));
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement(
          "SELECT id, user_type FROM user WHERE username = ? AND password_hash = ?");
      prep.setString(1, username);
      prep.setString(2, password);
      ResultSet rs = prep.executeQuery();
      if (rs.next()) {
        String userId = rs.getString(1);
        String type = rs.getString(2);
        Cookie cookie = new Cookie("hash", Integer.toString(username.hashCode()));
        if (type.equals("student")) {
          User student = new Student(userId);
          student.setType(type);
          cookieMap.put(cookie.getValue(), student);
          return student;
        } else if (type.equals("key-administrator") || (type.equals("administrator"))) {
          User admin = new Administrator(userId);
          admin.setType(type);
          cookieMap.put(cookie.getValue(), admin);
          return admin;
        }
      }
      rs.close();
    } catch (Exception e) {
      System.err.println("LOG IN Exception caused");
      e.printStackTrace();
    }
    return null;
  }

  /**
   * A method to remove cookies appropriately when a user logs out.
   *
   * @param encryption - a String representing the encryption
   */
  public static void logOut(String encryption) {
    cookieMap.remove(encryption);
  }

  /**
   * A method that deals with the sign up of a User and inserts their information
   * into the database.
   *
   * @param formInputs - a list of strings representing the information provided
   *                   on the sign up page at the front end
   * @param type       - a String representing the type of the User
   *                   (administrator, key-administrator or student)
   * @throws NoSuchProviderException  - when the provider does not exist
   * @throws NoSuchAlgorithmException - when the algorithm does not exist
   */
  public static void signUp(List<String> formInputs, String type)
      throws NoSuchProviderException, NoSuchAlgorithmException {
    try {
      PreparedStatement prep;
      prep = conn.prepareStatement("INSERT INTO user (id, username, first_name, "
          + "last_name, password_hash, user_type) VALUES (?, ?, ?, ?, ?, ?)");
      Random rand = new Random();
      String userId = Integer.toString(((Long) rand.nextLong()).hashCode());
      getIDs();
      while (usedIDs.contains(userId)) {
        userId = Integer.toString(((Long) rand.nextLong()).hashCode());
      }
      prep.setString(1, userId);
      prep.setString(2, formInputs.get(0));
      prep.setString(3, formInputs.get(1));
      prep.setString(4, formInputs.get(2));
      prep.setString(5, Password.getSecurePassword(formInputs.get(3))); // password
      prep.setString(6, type);
      prep.executeUpdate();

      if (type.equals("administrator")) {
        prep = conn.prepareStatement("INSERT INTO institute_admins VALUES (?, ?, ?)");
        prep.setString(1, formInputs.get(4));
        prep.setString(2, userId);
        prep.setInt(3, 0);
        prep.executeUpdate();
      }
      if (type.equals("key-administrator")) {
        prep = conn.prepareStatement("INSERT INTO institute_admins VALUES (?, ?, ?)");
        prep.setString(1, formInputs.get(4));
        prep.setString(2, userId);
        prep.setInt(3, 1);
        prep.executeUpdate();
      }
      if (type.equals("student")) {
        prep = conn.prepareStatement("INSERT INTO institute_students VALUES (?, ?)");
        prep.setString(1, formInputs.get(4));
        prep.setString(2, userId);
        prep.executeUpdate();
      }
      prep.close();
    } catch (SQLException e) {
      System.err.println("Error connecting to User database while signing up");
      e.printStackTrace();
    }
  }
}
