package edu.brown.cs.student.Barmej.handlers;

import java.util.Set;

import javax.naming.AuthenticationException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import edu.brown.cs.student.Barmej.course.Course;
import edu.brown.cs.student.Barmej.exceptions.CourseNotFoundException;
import edu.brown.cs.student.Barmej.user.Student;
import edu.brown.cs.student.Barmej.user.User;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * A class for the handler that deals with the making of a json object
 * consisting of information on the courses a student is enrolled in, so those
 * courses can be displayed on the students dashboard.
 */
class MyCoursesDatahandler implements Route {
  @Override
  public String handle(Request req, Response res)
      throws AuthenticationException, CourseNotFoundException {
    JsonObject json = new JsonObject();
    String cookie = req.cookie("hash");
    Student student = (Student) User.getCookieMap().get(cookie);
    if (student == null) {
      json.addProperty("status", "failed");
    } else {
      if (ParentHandler.authenticate(req, "student")) {
        Set<Course> courses = student.getEnrolledIn();
        JsonArray array = new JsonArray();
        for (Course course : courses) {
          JsonObject item = new JsonObject();
          item.addProperty("cID", course.getId());
          item.addProperty("cImgUrl", course.getThumbnailUrl());
          item.addProperty("cTitle", course.getName());
          array.add(item);
        }
        json.add("data", array);
        json.addProperty("status", "success");
      } else {
        json.addProperty("status", "failed");
      }
    }
    return ParentHandler.getGson().toJson(json);
  }
}
