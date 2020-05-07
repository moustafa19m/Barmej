package edu.brown.cs.student.Barmej.handlers;

import java.util.HashMap;
import java.util.Map;

import edu.brown.cs.student.Barmej.exceptions.UserPermissionException;
import edu.brown.cs.student.Barmej.user.Administrator;
import edu.brown.cs.student.Barmej.user.Institution;
import edu.brown.cs.student.Barmej.user.User;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * A class for the handler that deals with the making and sending to the front
 * end of a json object consisting of information for an institution and the
 * currently logged in admin to display on the admins dashboard.
 */
class AdminPageHandler implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request req, Response res) throws UserPermissionException {
    String cookie = req.cookie("hash");

    Map<String, String> variables = new HashMap<>();
    Administrator admin = (Administrator) User.getCookieMap().get(cookie);
    if (admin == null) {
      return new ModelAndView(variables, "index.html");
    }
    Institution institution = admin.getInstitution();

    variables.put("oName", institution.getName());
    variables.put("oSize", institution.getSize(admin).toString());
    variables.put("oEnrolled", institution.getNumEnrolled(admin).toString());
    StringBuilder name = new StringBuilder(admin.getFirstName());
    name.append(" ");
    name.append(admin.getLastName());
    variables.put("oAdmin", name.toString());
    variables.put("oUserName", admin.getUserName());
    variables.put("oType", institution.getType());
    return new ModelAndView(variables, "admin-index.html");
  }
}
