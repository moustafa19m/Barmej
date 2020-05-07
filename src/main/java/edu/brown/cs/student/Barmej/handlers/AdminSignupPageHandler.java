package edu.brown.cs.student.Barmej.handlers;

import java.util.HashMap;
import java.util.Map;

import edu.brown.cs.student.Barmej.user.User;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * A class for handling cookies on the admin signup page.
 */
class AdminSignupPageHandler implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request req, Response res) {
    String cookie = req.params(":cookie");
    Map<String, String> variables = new HashMap<String, String>();
    System.out.println("Sending " + cookie);
    if (User.getEmailMap().containsKey(cookie)) {
      System.out.println(cookie);
      variables.put("cookie", cookie);
      return new ModelAndView(variables, "admin-signup.html");
    }
    return null;
  }
}
