package edu.brown.cs.student.Barmej.handlers;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.brown.cs.student.Barmej.exceptions.UserPermissionException;
import edu.brown.cs.student.Barmej.user.Administrator;
import edu.brown.cs.student.Barmej.user.Institution;
import edu.brown.cs.student.Barmej.user.User;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * A class for a handler that allows users to sign in.
 *
 */
class SigninHandler implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request req, Response res)
      throws NoSuchProviderException, NoSuchAlgorithmException, UserPermissionException {
    QueryParamsMap params = req.queryMap();
    String username = params.value("username");
    String password = params.value("password");
    List<String> userInput = new ArrayList<>();
    userInput.add(username);
    userInput.add(password);
    String landingPage = "";
    User user = User.logIn(userInput);
    System.out.println("hit signin");
    Map<String, String> variables = new HashMap<>();
    if (user != null) {
      String type = user.getType();
      if (type.equals("key-administrator") || type.equals("administrator")) {
        landingPage = "admin-index.html";
        String id = User.findIdFromUsername(username);
        Administrator admin = new Administrator(id);
        Institution institution = admin.getInstitution();
        variables.put("oName", institution.getName());
        variables.put("oSize", institution.getSize(admin).toString());
        variables.put("oEnrolled", Integer.toString(institution.getNumEnrolled(admin)));
        StringBuilder name = new StringBuilder(admin.getFirstName());
        name.append(" ");
        name.append(admin.getLastName());
        variables.put("oAdmin", name.toString());
        variables.put("oType", institution.getType());
      } else if (type.equals("student")) {
        landingPage = "student-index.html";
        new StudentPageHandler();
      }
      if (!landingPage.equals("")) {
        variables.put("status", "success");
        res.cookie("hash", Integer.toString(username.hashCode()));
        return new ModelAndView(variables, landingPage);
      }
    }
    variables.put("status", "failed");
    return new ModelAndView(variables, "index.html");
  }
}
