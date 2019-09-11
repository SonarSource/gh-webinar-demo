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
  public void doPost(HttpServletRequest taintedRequest, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");

    String taintedString = "";
    if (taintedRequest.getHeader("HeaderName") != null) {
      taintedString = taintedRequest.getHeader("HeaderName");
    }

    taintedString = taintedRequest.getParameter(FIELD_NAME);
    taintedString = java.net.URLDecoder.decode(taintedString, "UTF-8");

    try {
      new SQLInjectionVulnerability(taintedString);
      new CommandInjectionVulnerability(taintedString);
      new RegexInjectionVulnerability(taintedString);

      String[] array = BusinessThingsUtils.doComplexBusinessThings(taintedString);
      new SQLInjectionVulnerabilityArray3Files(array[0]); // Safe
      new SQLInjectionVulnerabilityArray3Files(array[1]); // Noncompliant: array[1] has been tainted in doComplexBusinessThings

    } catch (SQLException|IOException e) {
    }
  }

}
