package edu.brown.cs.student.Barmej.handlers;

import java.util.Set;

import javax.naming.AuthenticationException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import edu.brown.cs.student.Barmej.user.Administrator;
import edu.brown.cs.student.Barmej.user.Institution;
import edu.brown.cs.student.Barmej.user.User;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * A class that deals with the making and sending to the front end of a json
 * object consisting of a list of all the administrators of an institution.
 *
 */
class AdminListDataHandler implements Route {
  @Override
  public String handle(Request req, Response res) throws AuthenticationException {
    JsonObject json = new JsonObject();
    String cookie = req.cookie("hash");
    Administrator admin = (Administrator) User.getCookieMap().get(cookie);
    if (admin == null) {
      json.addProperty("status", "failed");
    } else {
      if (ParentHandler.authenticate(req, "administrator")) {
        Institution institute = admin.getInstitution();
        Set<Administrator> admins = institute.getAdmins();
        if (admins == null) {
          json.addProperty("status", "failed");
        } else {
          JsonArray array = new JsonArray();
          for (Administrator a : admins) {
            JsonObject item = new JsonObject();
            item.addProperty("aID", a.getId());
            item.addProperty("aFirstName", a.getFirstName());
            item.addProperty("aLastName", a.getLastName());
            item.addProperty("aEmail", a.getEmail());
            array.add(item);
          }
          json.add("data", array);
          json.addProperty("oID", institute.getId());
          json.addProperty("status", "success");
        }
      } else {
        json.addProperty("status", "failed");
      }
    }
    return ParentHandler.getGson().toJson(json);
  }
}
