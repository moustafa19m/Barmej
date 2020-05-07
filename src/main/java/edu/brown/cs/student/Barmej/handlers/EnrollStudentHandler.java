package edu.brown.cs.student.Barmej.handlers;

import com.google.gson.JsonObject;

import edu.brown.cs.student.Barmej.course.Course;
import edu.brown.cs.student.Barmej.exceptions.UserPermissionException;
import edu.brown.cs.student.Barmej.user.Administrator;
import edu.brown.cs.student.Barmej.user.Student;
import edu.brown.cs.student.Barmej.user.User;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * A class for the handler that deals with the making of a json object
 * consisting of information relating to a student and the course that student
 * is to be enrolled in, which allows admins to register or assign students to
 * different courses.
 */
class EnrollStudentHandler implements Route {
  @Override
  public String handle(Request req, Response res) throws UserPermissionException {
    // Map<String, String> variables = new HashMap<>();
    QueryParamsMap params = req.queryMap();
    String cookie = req.cookie("hash");
    Administrator admin = (Administrator) User.getCookieMap().get(cookie);

    JsonObject json = new JsonObject();
    if (ParentHandler.authenticate(req, "administrator")) {
      String sID = params.value("sID");
      String cID = params.value("cID");
      admin.addStudentToCourse(new Student(sID), new Course(cID));
      json.addProperty("status", "success");
    } else {
      json.addProperty("status", "failed");
    }
    return ParentHandler.getGson().toJson(json);
  }
}
