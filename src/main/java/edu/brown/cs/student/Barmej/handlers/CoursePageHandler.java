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
 * end of a json object consisting of information on the course ID so the
 * course-page for the appropriate course can be displayed.
 */
class CoursePageHandler implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request req, Response res) {
    System.out.println("inside course page handler");
    String cookie = req.cookie("hash");
    Map<String, String> variables = new HashMap<>();

    if (User.getCookieMap().containsKey(cookie)) {
      System.out.println(req.params(":cIDandWeek"));
      String[] data = req.params(":cIDandWeek").split(":");
      variables.put("cID", data[0]);
      variables.put("weekNum", data[1]);
      return new ModelAndView(variables, "course-page.html");
    } else {
      return new ModelAndView(variables, "index.html");
    }
  }
}
