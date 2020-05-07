package edu.brown.cs.student.Barmej.handlers;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.brown.cs.student.Barmej.user.User;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * A class for the handler that deals with the making of the json object
 * consisting of the sign up information for an admin.
 */
class AdminSignupHandler implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request req, Response res)
      throws NoSuchProviderException, NoSuchAlgorithmException {
    QueryParamsMap params = req.queryMap();
    String cookie = params.value("cookie");
    String adminOrgID = params.value("oID");
    String firstName = params.value("aFirstName");
    String lastName = params.value("aLastName");
    String emailAddress = params.value("aEmail");
    String password = params.value("aPassword");
    System.out.println(cookie);
    System.out.println(firstName);
    System.out.println("Priniting map");
    System.out.println(User.getEmailMap().size());
    if (User.getEmailMap().get(cookie).equals(emailAddress)) {
      System.out.println("no repeat hit");
      List<String> adminInput = new ArrayList<>();
      adminInput.add(emailAddress);
      adminInput.add(firstName);
      adminInput.add(lastName);
      adminInput.add(password);
      adminInput.add(adminOrgID);
      User.signUp(adminInput, "administrator");
      Map<String, String> variables = new HashMap<>();
      variables.put("Status", "Success");
    }
    return ParentHandler.handleGetRequest(req, "index.html", "index.html");
  }
}
