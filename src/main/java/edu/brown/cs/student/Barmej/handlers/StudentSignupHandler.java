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
 * consisting of the sign up information for a student.
 */
class StudentSignupHandler implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request req, Response res)
      throws NoSuchProviderException, NoSuchAlgorithmException {
    QueryParamsMap params = req.queryMap();
    String cookie = params.value("cookie");
    String orgID = params.value("oID");
    String firstName = params.value("sFirstName");
    String lastName = params.value("sLastName");
    String emailAddress = params.value("sEmail");
    String password = params.value("sPassword");
    if (User.getEmailMap().get(cookie).equals(emailAddress)) {
      System.out.println("no repeat hit");
      List<String> studentInput = new ArrayList<>();
      studentInput.add(emailAddress);
      studentInput.add(firstName);
      studentInput.add(lastName);
      studentInput.add(password);
      studentInput.add(orgID);
      User.signUp(studentInput, "student");
      Map<String, String> variables = new HashMap<>();
      variables.put("Status", "Success");
    }
    return ParentHandler.handleGetRequest(req, "index.html", "index.html");
  }
}
