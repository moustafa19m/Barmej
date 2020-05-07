package edu.brown.cs.student.Barmej.handlers;

import java.util.HashMap;
import java.util.Map;

import edu.brown.cs.student.Barmej.user.User;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * A class for the handler that deals with the making of a json object
 * consisting of information on a codeProblemID so that the IDE
 * problem/assignment for the right week of the relevant course can be
 * displayed.
 */
class IDEPageHandler implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request req, Response res) {
    System.out.println("inside course page handler");
    String cookie = req.cookie("hash");
    Map<String, String> variables = new HashMap<>();

    if (User.getCookieMap().containsKey(cookie)) {
      variables.put("problemID", req.params(":cIDandWeek"));
      return new ModelAndView(variables, "ide-page.html");
    } else {
      return new ModelAndView(variables, "index.html");
    }
  }
}
