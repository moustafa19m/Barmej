package edu.brown.cs.student.Barmej.handlers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * A class for the student-index (students dashboard) handler.
 */
class StudentPageHandler implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request req, Response res) {
    return ParentHandler.handleGetRequest(req, "student-index.html", "index.html");
  }
}
