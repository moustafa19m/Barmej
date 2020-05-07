package edu.brown.cs.student.Barmej.handlers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.naming.AuthenticationException;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import edu.brown.cs.student.Barmej.exceptions.CourseNotFoundException;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * A class for the handler that deals with the making of a json object
 * consisting of information on a course an admin tries to access.
 */
class IdeCourseJsonDataHandler implements Route {
  @Override
  public String handle(Request req, Response res) throws AuthenticationException,
      CourseNotFoundException, JsonIOException, JsonSyntaxException, IOException {
    String cID = req.params(":problemID");
    System.out.println(cID);
    JsonObject json = new JsonObject();
    if (ParentHandler.authenticate(req, "student")) {
      try {
        BufferedReader br = new BufferedReader(
            new FileReader("src/main/resources/static/json/student/" + cID + ".json"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
          sb.append(line).append("\n");
        }
        String j = sb.toString();
        System.out.println(j);
        System.out.println(j.length());
        if (j.length() == 0) {
          return ParentHandler.getGson().toJson(json);
        }
        return ParentHandler.getGson().toJson(j);
      } catch (IOException e) {
        return ParentHandler.getGson().toJson(json);
      }

    }
    return ParentHandler.getGson().toJson(json);
  }
}