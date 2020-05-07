package edu.brown.cs.student.Barmej.handlers;

import java.util.Set;

import javax.naming.AuthenticationException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import edu.brown.cs.student.Barmej.user.Administrator;
import edu.brown.cs.student.Barmej.user.Institution;
import edu.brown.cs.student.Barmej.user.Student;
import edu.brown.cs.student.Barmej.user.User;
import spark.Request;
import spark.Response;
import spark.Route;

public class EnrolledStudentsDataHandler implements Route {

  @Override
  public String handle(Request req, Response res) throws AuthenticationException {
    JsonObject json = new JsonObject();
    String cID = req.params(":cID");
    System.out.println(cID + " from EnrolledSTudentsDataHandler");
    String cookie = req.cookie("hash");
    Administrator admin = (Administrator) User.getCookieMap().get(cookie);
    if (admin == null) {
      json.addProperty("status", "failed");
    } else {
      if (ParentHandler.authenticate(req, "administrator")) {
        Institution institute = admin.getInstitution();
        Set<Student> students = institute.getEnrolledStudents(cID);
        if (students == null) {
          json.addProperty("status", "failed");
        } else {
          JsonArray array = new JsonArray();
          for (Student student : students) {
            JsonObject item = new JsonObject();
            item.addProperty("sID", student.getId());
            item.addProperty("sFirstName", student.getFirstName());
            item.addProperty("sLastName", student.getLastName());
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
