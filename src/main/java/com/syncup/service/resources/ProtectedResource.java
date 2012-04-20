package com.syncup.service.resources;

/**
 * Created with IntelliJ IDEA.
 * User: aditya
 * Date: 4/20/12
 * Time: 6:02 AM
 * To change this template use File | Settings | File Templates.
 */
import com.syncup.service.core.User;
import com.yammer.dropwizard.auth.Auth;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/protected")
@Produces(MediaType.TEXT_PLAIN)
public class ProtectedResource {
    @GET
    public String showSecret(@Auth User user) {
        return String.format("Hey there, %s. You know the secret!", user.getName());
    }
}
