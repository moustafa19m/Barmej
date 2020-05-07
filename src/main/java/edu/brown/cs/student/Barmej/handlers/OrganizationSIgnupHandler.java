package edu.brown.cs.student.Barmej.handlers;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.brown.cs.student.Barmej.user.Institution;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * A class for the handler that deals with the making of the json object
 * consisting of the sign up information for an organization/institution.
 */
class OrganizationSignupHandler implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request req, Response res)
      throws NoSuchProviderException, NoSuchAlgorithmException {
    QueryParamsMap params = req.queryMap();
    String orgType = params.value("oType");
    String orgName = params.value("oName");
    String orgSize = params.value("oSize");
    String orgAddress = params.value("oAddress");
    String orgCountry = params.value("oCountry");
    String orgState = params.value("oState");
    String orgZip = params.value("oZipCode");
    String orgCity = params.value("oCity");
    List<String> institutionInput = new ArrayList<>();
    institutionInput.add(orgName);
    institutionInput.add(orgType);
    institutionInput.add(orgSize);
    StringBuilder builder = new StringBuilder(orgAddress);
    builder.append(", ");
    builder.append(orgCity);
    builder.append(", ");
    builder.append(orgState);
    builder.append(", ");
    builder.append(orgCountry);
    builder.append(", ");
    builder.append(orgZip);
    institutionInput.add(builder.toString());
    String firstName = params.value("aFirstName");
    String lastName = params.value("aLastName");
    String emailAddress = params.value("aEmail");
    String password = params.value("aPassword");
    List<String> adminInput = new ArrayList<>();
    adminInput.add(emailAddress);
    adminInput.add(firstName);
    adminInput.add(lastName);
    adminInput.add(password);
    Institution.institutionSignUp(institutionInput, adminInput);
    Map<String, String> variables = new HashMap<>();
    variables.put("Status", "Success");
    return ParentHandler.handleGetRequest(req, "index.html", "index.html");
  }
}
