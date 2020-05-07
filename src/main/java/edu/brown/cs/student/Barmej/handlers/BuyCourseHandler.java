package edu.brown.cs.student.Barmej.handlers;

import com.google.gson.JsonObject;

import edu.brown.cs.student.Barmej.user.Administrator;
import edu.brown.cs.student.Barmej.user.User;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * A class for the handler that deals with the making and sending to the front
 * end of a json object consisting of a course ID, used in the implementation of
 * the buying course functionality.
 */
class BuyCourseHandler implements Route {
  @Override
  public String handle(Request req, Response res) {
    QueryParamsMap params = req.queryMap();
    String cID = params.value("cID");
    JsonObject json = new JsonObject();
    Administrator admin = (Administrator) User.getCookieMap().get(params.value("hash"));
    if (admin.getType().equals("administrator") || admin.getType().equals("key-administrator")) {
      System.out.println("authenticated in buy course handler");
      boolean bought = admin.buyCourse(cID);
      if (bought) {
        json.addProperty("status", "success");
      } else {
        json.addProperty("status", "bought");
      }

    } else {
      json.addProperty("status", "failed");
    }
    return ParentHandler.getGson().toJson(json);
  }
}
