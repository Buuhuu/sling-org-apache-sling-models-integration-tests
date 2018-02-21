package org.apache.sling.models.it.rtpickerrequest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;

@SlingServlet(paths = "/apps/rtpickerrequest")
public class TestServlet extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        Query q = request.adaptTo(Query.class);

        if (q == null || q.getParameterFoo() == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            response.sendError(HttpServletResponse.SC_OK);
        }
    }
}
