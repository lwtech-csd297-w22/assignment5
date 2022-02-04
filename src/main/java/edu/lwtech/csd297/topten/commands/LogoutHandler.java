package edu.lwtech.csd297.topten.commands;

import java.util.*;
import javax.servlet.http.*;

import edu.lwtech.csd297.topten.TopTenListsServlet;

// Handle the "logout" command
public class LogoutHandler implements CommandHandler<TopTenListsServlet> {

    @Override
    public String handle(HttpServletRequest request, TopTenListsServlet servlet) {
        String template = "confirm.ftl";
        Map<String, Object> templateFields = new HashMap<>();
        CommandUtils.getSessionVariables(request, templateFields);
        HttpSession session = request.getSession(false);            // false == don't create a new session if one doesn't exist

        // Logout the user by killing their session
        if (session != null) {
            session.invalidate();
        }
        templateFields.put("message", "You have been successfully logged out.<br /><a href='?cmd=home'>Home</a>");
        return CommandUtils.mergeTemplate(template, templateFields, servlet.getFreeMarkerConfig());
    }

}
