package foo.security.hotspots;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

public class WritingCookieServlet {
    
    void aServiceMethodSettingCookie(HttpServletRequest request, HttpServletResponse response, String acctID) {
        Cookie cookie = new Cookie("userAccountID", acctID); // Sensitive
        response.addCookie(cookie); // Sensitive
    }
}