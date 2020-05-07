package edu.brown.cs.student.Barmej.handlers;

import java.util.HashMap;
import java.util.Map;

import edu.brown.cs.student.Barmej.user.User;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * A class for the handler that deals with the log out button for a user and
 * redirects to the main/home page.
 */
class LogOutHandler implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request req, Response res) {
    Map<String, String> variables = new HashMap<>();
    String encryption = req.cookie("hash");
    variables.put("username", encryption);
    User.logOut(encryption);
    variables.put("status", "success");
    return new ModelAndView(variables, "index.html");
  }
}
