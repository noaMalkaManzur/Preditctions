package utils;

import constants.Constants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtils {

    public static String getPathFile(HttpServletRequest request) {
        String usernameFromParameter = request.getParameter(Constants.FILEPATH);
        HttpSession session = request.getSession(false);
        Object sessionAttribute = session != null ? session.getAttribute(Constants.FILEPATH) : null;
        return sessionAttribute != null ? sessionAttribute.toString() : null;
    }

}
