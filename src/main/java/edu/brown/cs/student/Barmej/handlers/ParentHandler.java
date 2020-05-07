package edu.brown.cs.student.Barmej.handlers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import edu.brown.cs.student.Barmej.user.User;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * This class contains all the handlers and spark routes to set up connections
 * with the front end.
 */
public class ParentHandler {

  /**
   * Fields for this class.
   */
  private static final Gson GSON = new Gson();
  private static final int ONE_TWO_THREE_FOUR = 1234;

  /**
   * An empty constructor for this class to instantiate objects of its type.
   */
  public ParentHandler() {

  }

  /**
   * A method to handle get requests.
   *
   * @param req          - a Request object
   * @param url          - a String representing a url
   * @param alternateURL - a String representing a url
   * @return - a ModelAndView
   */
  public static ModelAndView handleGetRequest(Request req, String url, String alternateURL) {
    String cookie = req.cookie("hash");
    Map<String, String> variables = new HashMap<>();

    if (User.getCookieMap().containsKey(cookie)) {
      return new ModelAndView(variables, url);
    } else {
      return new ModelAndView(variables, alternateURL);
    }
  }

  /**
   * A method to authenticate whether a user is an admin or key-admin, or a
   * student, using cookies.
   *
   * @param req  - a Request object
   * @param type - a String representing the type of a user (administrtor,
   *             key-administrator, or student)
   * @return - a boolean evaluating to true if the user can be authenticated as an
   *         administrator or a key-administrator, otherwise false
   */
  public static Boolean authenticate(Request req, String type) {
    String cookie = req.cookie("hash");
    if (type.equals("administrator")) {
      boolean output = (User.getCookieMap().get(cookie).getType().equals(type)
          || User.getCookieMap().get(cookie).getType().equals("key-administrator"));
      return output;
    } else {
      boolean output = (User.getCookieMap().get(cookie).getType().equals(type));
      return output;
    }
  }

  private static int getHerokuAssignedPort() {
    ProcessBuilder processBuilder = new ProcessBuilder();
    if (processBuilder.environment().get("PORT") != null) {
      return Integer.parseInt(processBuilder.environment().get("PORT"));
    }
    return ONE_TWO_THREE_FOUR;
  }

  /**
   * This is provided by the TAs.
   *
   * @return a new FreeMarkerEngine
   */
  public static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
    File templates = new File("src/main/resources/spark/template/freemarker/html");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n", templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  /**
   * A method that sets up all the get and post requests.
   *
   */

  public static void runSparkServer() {
    Spark.port(getHerokuAssignedPort());
    Spark.externalStaticFileLocation("src/main/resources/static");
    FreeMarkerEngine freeMarker = createEngine();
    // Setup Spark Routes
    // redirects to index
    Spark.get("/", new MainHandler(), freeMarker);
    // redirects to index, refer to signup.html for names of input fields
    Spark.post("/signup", new OrganizationSignupHandler(), freeMarker);
    // redirects to admin-index or student-index.
    Spark.post("/login", new SigninHandler(), freeMarker);
    // redirects to admin-index
    Spark.get("/admin", new AdminPageHandler(), freeMarker);
    // redirects to signup
    Spark.get("/join", new JoinPageHandler(), freeMarker);
    // redirects to student-list
    Spark.get("/studentList", new StudentListPageHandler(), freeMarker);
    // redirects to student-list and returns the organization id in the variables
    Spark.get("/enrolledStudents/:cID", new EnrolledStudentsPageHandler(), freeMarker);
    // redirects to admin-list and returns the organization id in the variables
    Spark.get("/adminList", new AdminListPageHandler(), freeMarker);
    // redirects to browse-courses and returns the organization id in the variables
    Spark.get("/browseCourses", new BrowseCoursesPageHandler(), freeMarker);
    // redirects to admin-signup, refer to admin-signup.html
    Spark.get("/adminSignup/:cookie", new AdminSignupPageHandler(), freeMarker);
    // redirects to student-signup, refer to student- signup.html
    Spark.get("/studentSignup/:cookie", new StudentSignupPageHandler(), freeMarker);
    // redirects to student-index
    Spark.get("/student", new StudentPageHandler(), freeMarker);
    // redirects to course-page
    Spark.get("/student/:cIDandWeek", new CoursePageHandler(), freeMarker);
    // redirects to course-page
    // Spark.get("/student/:cID/:moduleID", new CourseModulePageHandler(),
    // freeMarker);
    // redirects to course-page
    // Spark.get("/student/:cID/:moduleID/:weekID", new
    // CourseModuleWeekPageHandler(), freeMarker);
    // redirects to course-info
    Spark.get("/admin/:cID", new CourseInfoPageHandler(), freeMarker);
    // redirects to buy-course
    Spark.get("/course/:cID", new CourseLearnMorePageHandler(), freeMarker);
    // redirects to ide
    Spark.get("/ide/:cIDandWeek", new IDEPageHandler(), freeMarker);
    // returns a json object that contains a list of courses
    Spark.get("/ownedCourses", new OwnedCoursesDatahandler());
    // returns a json object that contains a list of courses
    Spark.get("/myCourses", new MyCoursesDatahandler());
    // returns a json object that contains a list of admins
    Spark.get("/adminListData", new AdminListDataHandler());
    // returns a json object that contains a list of students
    Spark.get("/studentListData", new StudentListDataHandler());
    // returns a json object that contains a list of students
    Spark.get("/enrolledStudentsData/:cID", new EnrolledStudentsDataHandler());
    // returns a json object that contains a list of courses to display on the
    // browse course page
    Spark.post("/recommendedCourses", new CourseRecommenderDataHandler());
    // logs the user out and returns to the index page
    Spark.get("/logOut", new LogOutHandler(), freeMarker);
    // invites an admin
    Spark.post("/invite/admin", new InviteAdminHandler(), freeMarker);
    // enroll student, given sID and cID, enrolls student with sID in course with
    Spark.post("/enroll/student", new EnrollStudentHandler());
    // gets students at institution not enrolled in cID
    Spark.get("/toEnroll/student/:cID", new StudentsToEnrollDataHandler());
    // invite student
    Spark.post("/invite/student", new InviteStudentHandler(), freeMarker);
    // purchase course with cID to oID
    Spark.post("/purchase", new BuyCourseHandler());
    // redirects to admin-signup, refer to admin-signup.html for names of input
    // fields
    Spark.post("/signup/admin", new AdminSignupHandler(), freeMarker);
    // redirects to student-signup, refer to student- signup.html for names of input
    // fields
    Spark.post("/signup/student", new StudentSignupHandler(), freeMarker);
    // returns the json object for the course page
    Spark.get("/student/json/:cID", new StudentCourseJsonDataHandler());
    // returns the json object for the buy course and course info
    Spark.get("/admin/json/:cID", new AdminCourseJsonDataHandler());
    Spark.get("/problem/json/:problemID", new IdeCourseJsonDataHandler());
  }

  /**
   * Getter for the GSON field of this class.
   *
   * @return - the GSON field
   */
  public static Gson getGson() {
    return GSON;
  }

  /**
   * A class for the main page or index.html page handler.
   */
  private static class MainHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, String> variables = null;
      return new ModelAndView(variables, "index.html");
    }
  }

  /**
   * A class for the signup.html page handler.
   */
  private static class JoinPageHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, String> variables = new HashMap<>();
      return new ModelAndView(variables, "signup.html");
    }
  }

  /**
   * A class for the students-list.html page handler.
   */
  private static class StudentListPageHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      return ParentHandler.handleGetRequest(req, "students-list.html", "index.html");
    }
  }

  /**
   * A class for the enrolled-students.html page handler.
   */
  private static class EnrolledStudentsPageHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      System.out.println("inside enrolled students page handler");
      String cookie = req.cookie("hash");
      Map<String, String> variables = new HashMap<>();
      variables.put("cID", req.params(":cID"));
      if (User.getCookieMap().containsKey(cookie)) {
        return new ModelAndView(variables, "enrolled-students.html");
      } else {
        return new ModelAndView(variables, "index.html");
      }
    }
  }

  /**
   * A class for the admins-list.html page handler.
   */
  private static class AdminListPageHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      return ParentHandler.handleGetRequest(req, "admins-list.html", "index.html");
    }
  }

  /**
   * A class for the browse-courses.html page handler.
   */
  private static class BrowseCoursesPageHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      return ParentHandler.handleGetRequest(req, "browse-courses.html", "index.html");
    }
  }

//  private static class RecommendCoursesHandler implements Route {
//    @Override
//    public String handle(Request req, Response res) throws UserPermissionException {
//      JsonObject json = new JsonObject();
//      String cookie = req.cookie("hash");
//      Administrator admin = (Administrator) User.getCookieMap().get(cookie);
//      if (admin == null) {
//        json.addProperty("status", "failed");
//      } else {
//        if (authenticate(req, "administrator")) {
//          Institution institute = admin.getInstitution();
//          QueryParamsMap params = req.queryMap();
//          Map<String, String> values = new HashMap<String, String>();
//          values.put("skill", params.value("skill"));
//          values.put("difficulity", params.value("difficulity"));
//          values.put("difficulity", params.value("difficulity"));
//          Set<Course> courses = institute.getRecommendedCourses(values);
//          if (courses == null) {
//            json.addProperty("status", "failed");
//          } else {
//            JsonArray array = new JsonArray();
//            for (Course course : courses) {
//              JsonObject item = new JsonObject();
//              item.addProperty("cID", course.getId());
//              item.addProperty("cTitle", course.getName());
//              item.addProperty("cLevel", course.getDifficulty());
//              item.addProperty("cImgUrl", course.getThumbnailUrl());
//              array.add(item);
//            }
//            json.add("data", array);
//            json.addProperty("oID", institute.getId());
//            json.addProperty("status", "success");
//          }
//        } else {
//          json.addProperty("status", "failed");
//        }
//      }
//      return GSON.toJson(json);
//    }
//  }
}
