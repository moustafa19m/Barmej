package edu.brown.cs.student.Barmej.handlers;

import java.util.List;

import javax.naming.AuthenticationException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import edu.brown.cs.student.Barmej.exceptions.CourseNotFoundException;
import edu.brown.cs.student.Barmej.user.Administrator;
import edu.brown.cs.student.Barmej.user.Student;
import edu.brown.cs.student.Barmej.user.User;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * A class for the handler that deals with the making of the json object
 * consisting of a course ID and student information to enroll students in
 * courses.
 */
class StudentsToEnrollDataHandler implements Route {
  @Override
  public String handle(Request req, Response res)
      throws AuthenticationException, CourseNotFoundException {

    String cID = req.params(":cID");
    JsonObject json = new JsonObject();
    System.out.println(req.cookie("hash"));
    if (ParentHandler.authenticate(req, "administrator")) {
      System.out.println("inside StudentsToEnrollDataHandler cID: " + cID);
      Administrator admin = (Administrator) User.getCookieMap().get(req.cookie("hash"));
      List<Student> students = admin.getStudentsNotIn(cID);

      JsonArray array = new JsonArray();
      for (Student student : students) {

        JsonObject item = new JsonObject();
        item.addProperty("sID", student.getId());
        item.addProperty("sName", student.getFullName());
        array.add(item);
      }
      json.add("data", array);
      json.addProperty("status", "success");
    } else {
      json.addProperty("status", "failed");
    }
    return ParentHandler.getGson().toJson(json);
  }
}
