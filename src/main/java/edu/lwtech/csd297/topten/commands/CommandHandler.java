package edu.lwtech.csd297.topten.commands;

import javax.servlet.http.*;

public interface CommandHandler<T> {
    String handle(HttpServletRequest request, T servlet);
}
