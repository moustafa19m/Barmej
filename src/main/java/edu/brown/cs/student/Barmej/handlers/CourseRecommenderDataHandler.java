package edu.brown.cs.student.Barmej.handlers;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import edu.brown.cs.student.Barmej.course.Course;
import edu.brown.cs.student.Barmej.exceptions.UserPermissionException;
import edu.brown.cs.student.Barmej.user.Administrator;
import edu.brown.cs.student.Barmej.user.Institution;
import edu.brown.cs.student.Barmej.user.User;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * A class for the handler that deals with the making and sending to the front
 * end of a json object consisting of information for all the courses that are
 * to be displayed as recommendations to an institution.
 */
class CourseRecommenderDataHandler implements Route {
  @Override
  public String handle(Request req, Response res) throws UserPermissionException {

    JsonObject json = new JsonObject();
    String cookie = req.cookie("hash");
    QueryParamsMap params = req.queryMap();
    Administrator admin = (Administrator) User.getCookieMap().get(cookie);
    if (admin == null) {
      System.out.println("admin is null in BrowseCoursesDataHandler");
      json.addProperty("status", "failed");
    } else {
      if (ParentHandler.authenticate(req, "administrator")) {
        Institution institute = admin.getInstitution();
        List<Course> courses = institute.getRecommendedCourses(params);
        System.out.println("coursessssss");
        System.out.println(courses);
        if (courses == null) {
          System.out.println("courses are null in BrowseCoursesDataHandler");
          json.addProperty("status", "failed");
        } else {
          JsonArray array = new JsonArray();
          for (Course course : courses) {
            JsonObject item = new JsonObject();
            item.addProperty("cID", course.getId());
            item.addProperty("cTitle", course.getName());
            item.addProperty("cLevel", course.getDifficulty());
            item.addProperty("cImgUrl", course.getThumbnailUrl());
            array.add(item);
          }
          json.add("data", array);
          json.addProperty("oID", institute.getId());
          json.addProperty("status", "success");
        }
      } else {
        System.out.println("user not authenticated in BrowseCoursesDataHandler");
        json.addProperty("status", "failed");
      }
    }
    return ParentHandler.getGson().toJson(json);
  }
}
