package foo.security.injection;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/VulnerableServlet")
public class Servlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private static final String FIELD_NAME = "FIELD_KEY";

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setHeader("Content-Type", "text/plain; charset=utf-8");
    resp.setHeader("Access-Control-Allow-Origin", "*"); // Questionable
    resp.getWriter().write("Sensitive Information");
  }

  @Override
  public void doPost(HttpServletRequest taintedRequest, HttpServletResponse response) throws ServletException, IOException {
    response.setHeader("Content-Type", "text/html; charset=UTF-8");   

    String taintedString = "";
    if (taintedRequest.getHeader("HeaderName") != null) {
      taintedString = taintedRequest.getHeader("HeaderName");
    }

    taintedString = taintedRequest.getParameter(FIELD_NAME);
    try {
      taintedString = java.net.URLDecoder.decode(taintedString, "UTF-8");

      new CommandInjectionVulnerability(taintedString);
      new RegexInjectionVulnerability(taintedString);

      String[] array = BusinessThingsUtils.doComplexBusinessThings(taintedString);
      new SQLInjectionVulnerabilityCollectionMultipleFiles(array[0]); // Safe
      new SQLInjectionVulnerabilityCollectionMultipleFiles(array[1]); // Noncompliant: array[1] has been tainted in doComplexBusinessThings

    } catch (SQLException|IOException e) {
    }
  }

}
