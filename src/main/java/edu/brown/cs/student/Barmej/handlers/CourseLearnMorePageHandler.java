package edu.brown.cs.student.Barmej.handlers;

import java.util.HashMap;
import java.util.Map;

import edu.brown.cs.student.Barmej.user.User;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * A class for the handler that deals with the making and sending to the front
 * end of a json object consisting of a course ID so the buy-course page for the
 * appropriate ID can be displayed.
 */
class CourseLearnMorePageHandler implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request req, Response res) {
    String cID = req.params(":cID");
    String cookie = req.cookie("hash");
    Map<String, String> variables = new HashMap<>();
    if (User.getCookieMap().containsKey(cookie)) {
      variables.put("cID", cID);
      return new ModelAndView(variables, "buy-course.html");
    } else {
      return new ModelAndView(variables, "index.html");
    }
  }
}
