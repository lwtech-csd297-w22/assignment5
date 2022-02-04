package edu.lwtech.csd297.topten.commands;

import java.util.*;
import javax.servlet.http.*;

import edu.lwtech.csd297.topten.TopTenListsServlet;

// Handle the "home" command
public class HomeHandler implements CommandHandler<TopTenListsServlet> {

    @Override
    public String handle(HttpServletRequest request, TopTenListsServlet servlet) {
        String template = "home.ftl";
        Map<String, Object> templateFields = new HashMap<>();
        CommandUtils.getSessionVariables(request, templateFields);
        templateFields.put("topTenLists", servlet.getListsDAO().retrieveAll());
        return CommandUtils.mergeTemplate(template, templateFields, servlet.getFreeMarkerConfig());
    }

}
