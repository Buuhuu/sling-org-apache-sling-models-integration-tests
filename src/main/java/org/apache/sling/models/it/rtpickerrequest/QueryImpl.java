package org.apache.sling.models.it.rtpickerrequest;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

@Model(adaptables = SlingHttpServletRequest.class, adapters = { QueryImpl.class, Query.class })
public class QueryImpl implements Query {

    @Self
    private SlingHttpServletRequest req;

    @Override
    public String getParameterFoo() {
        RequestParameter param = req.getRequestParameterMap().getValue("foo");
        if (param != null) {
            return param.getString();
        } else {
            return null;
        }
    }
}
