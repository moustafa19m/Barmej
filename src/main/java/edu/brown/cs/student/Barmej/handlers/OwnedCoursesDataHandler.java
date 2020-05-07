package edu.brown.cs.student.Barmej.handlers;

import java.util.Set;

import javax.naming.AuthenticationException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import edu.brown.cs.student.Barmej.course.Course;
import edu.brown.cs.student.Barmej.user.Administrator;
import edu.brown.cs.student.Barmej.user.User;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * A class for the handler that deals with the making of a json object
 * consisting of information on the courses the institution of an admin has
 * purchased, so they can be viewed on the admins dashboards.
 */
class OwnedCoursesDatahandler implements Route {
  @Override
  public String handle(Request req, Response res) throws AuthenticationException {
    JsonObject json = new JsonObject();
    String cookie = req.cookie("hash");
    Administrator admin = (Administrator) User.getCookieMap().get(cookie);
    if (admin == null) {
      json.addProperty("status", "failed");
    } else {
      if (ParentHandler.authenticate(req, "administrator")) {
        Set<Course> courses = admin.getCourses();
        if (courses == null) {
          json.addProperty("status", "failed");
        } else {
          JsonArray array = new JsonArray();
          for (Course course : courses) {

            // TODO Name, Institution Type, Enrolled Students, Max Number of Students & Date
            // Purchased missing
            JsonObject item = new JsonObject();
            item.addProperty("cID", course.getId());
            item.addProperty("cImgUrl", course.getThumbnailUrl());
            item.addProperty("cTitle", course.getName());
            item.addProperty("cLevel", course.getDifficulty());
            item.addProperty("cEnrolled", course.getStudentsEnrolled());
            item.addProperty("cMaxStudents", course.getMaxStudents());
            array.add(item);

          }
          json.add("data", array);
          json.addProperty("status", "success");
        }
      } else {
        json.addProperty("status", "failed");
      }
    }
    return ParentHandler.getGson().toJson(json);
  }
}
