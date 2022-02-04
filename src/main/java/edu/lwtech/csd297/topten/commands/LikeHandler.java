package edu.lwtech.csd297.topten.commands;

import javax.servlet.http.*;

import edu.lwtech.csd297.topten.pojos.*;
import edu.lwtech.csd297.topten.TopTenListsServlet;

// Handle the "like" command
public class LikeHandler implements CommandHandler<TopTenListsServlet> {

    @Override
    public String handle(HttpServletRequest request, TopTenListsServlet servlet) {

        int id = CommandUtils.parseInt(request.getParameter("id"));
        if (id < 0)
            return "";

        TopTenList list = servlet.getListsDAO().retrieveByID(id);
        if (list == null)
            return "";

        list.incrementNumLikes();
        servlet.getListsDAO().update(list);

        return new ShowListHandler().handle(request, servlet);
    }

}
