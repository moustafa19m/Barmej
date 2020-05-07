package edu.brown.cs.student.Barmej.handlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.AuthenticationException;

import edu.brown.cs.student.Barmej.user.Administrator;
import edu.brown.cs.student.Barmej.user.User;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * A class for the handler that deals with the making of a json object
 * consisting of information on the credentials on a new student that an
 * existing admin is inviting to an institution through email.
 */
class InviteStudentHandler implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request req, Response res)
      throws AuthenticationException, IOException {
    Map<String, String> variables = new HashMap<>();
    QueryParamsMap params = req.queryMap();
    String cookie = req.cookie("hash");
    User administrator = User.getCookieMap().get(cookie);
    Administrator admin = (Administrator) administrator;
    if (User.getCookieMap().containsKey(cookie)) {
      String email = params.value("sEmail");
      System.out.println(email);
      if (User.findIdFromUsername(email) == null) {
        System.out.println("no repeat");
        System.out.println("Sending invite");
        admin.inviteStudent(administrator, email);
        return new ModelAndView(variables, "students-list.html");
      }
    }
    return new ModelAndView(variables, "students-list.html");
  }
}
