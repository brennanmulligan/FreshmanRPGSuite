package edu.ship.engr.shipsim.restfulcommunication;

import edu.ship.engr.shipsim.datasource.LoggerManager;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.logging.Logger;

public class IPAddressInterceptor implements HandlerInterceptor
{

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception
    {
        Logger logger = LoggerManager.getSingleton().getLogger();
        String ip = request.getRemoteAddr();
        String method = request.getMethod();
        String uri = request.getRequestURI();

        logger.info("Received HttpServletRequest from " + ip + ": " + method + " " + uri);
		return true;
	}

}
