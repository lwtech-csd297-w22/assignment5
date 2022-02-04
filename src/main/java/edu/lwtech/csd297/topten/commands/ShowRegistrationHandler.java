package edu.lwtech.csd297.topten.commands;

import java.util.*;
import javax.servlet.http.*;

import edu.lwtech.csd297.topten.TopTenListsServlet;

// Handle the "showRegistration" command
public class ShowRegistrationHandler implements CommandHandler<TopTenListsServlet> {

    @Override
    public String handle(HttpServletRequest request, TopTenListsServlet servlet) {
        String template = "register.ftl";
        Map<String, Object> templateFields = new HashMap<>();
        CommandUtils.getSessionVariables(request, templateFields);
        HttpSession session = request.getSession(false);            // false == don't create a new session if one doesn't exist

        // Ensure the user is logged out before registering
        if (session != null) {
            session.invalidate();
        }
        return CommandUtils.mergeTemplate(template, templateFields, servlet.getFreeMarkerConfig());
    }

}
