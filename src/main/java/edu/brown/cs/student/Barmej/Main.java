package edu.brown.cs.student.Barmej;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import edu.brown.cs.student.Barmej.databases.Database;
import edu.brown.cs.student.Barmej.handlers.ParentHandler;
import edu.brown.cs.student.Barmej.helper.PopulateCourseTable;
import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * The Main class of our project. This is where execution begins.
 *
 */
public final class Main {

  private String[] args;
  private static final int ONE_TWO_THREE_FOUR = 1234;

  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments.
   * @throws IOException  will throw exception if this happens
   * @throws SQLException
   */
  public static void main(String[] args) throws IOException, SQLException {
    new Main(args).run();
  }

  private Main(String[] args) {
    this.args = args;
  }

  private void run() throws IOException, SQLException {
    OptionParser parser = new OptionParser();
    parser.accepts("populate");
    OptionSet options = parser.parse(args);
    Database.makeTables();
    if (options.has("populate")) {
      PopulateCourseTable p = new PopulateCourseTable();
      p.addCoursesToDB();
    }
    ParentHandler gui = new ParentHandler();
    gui.runSparkServer();

  }

  /**
   * A method to get the port number assigned by Heroku.
   *
   * @return - an interger representing the port number
   */
  private static int getHerokuAssignedPort() {
    ProcessBuilder processBuilder = new ProcessBuilder();
    if (processBuilder.environment().get("PORT") != null) {
      return Integer.parseInt(processBuilder.environment().get("PORT"));
    }
    return ONE_TWO_THREE_FOUR;
  }

  private void runSparkServer() {

    FreeMarkerEngine freeMarker = createEngine();
    Spark.port(getHerokuAssignedPort());
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
}
