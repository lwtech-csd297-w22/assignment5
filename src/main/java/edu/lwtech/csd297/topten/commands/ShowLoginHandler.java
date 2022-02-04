package edu.lwtech.csd297.topten.commands;

import java.util.*;
import javax.servlet.http.*;

import edu.lwtech.csd297.topten.TopTenListsServlet;

// Handle the "showLogin" command
public class ShowLoginHandler implements CommandHandler<TopTenListsServlet> {

    @Override
    public String handle(HttpServletRequest request, TopTenListsServlet servlet) {
        String template = "login.ftl";
        Map<String, Object> templateFields = new HashMap<>();
        CommandUtils.getSessionVariables(request, templateFields);
        return CommandUtils.mergeTemplate(template, templateFields, servlet.getFreeMarkerConfig());
    }

}
