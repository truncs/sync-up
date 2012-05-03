package com.syncup.service.resources;

/**
 * Copyright (c) 2012, aditya
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following
 * disclaimer.Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
 * following disclaimer in the documentation and/or other materials provided with the distribution.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import com.google.common.collect.ImmutableList;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.syncup.service.core.*;
import com.syncup.service.db.AccessDAO;
import com.syncup.service.db.PresentationDAO;
import com.syncup.service.db.UserDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;
import com.sun.jersey.multipart.FormDataParam;

import com.google.common.cache.Cache;
import com.yammer.dropwizard.logging.Log;
import org.apache.commons.io.IOUtils;

@Path("/presentation")
public class PresentationResource {

    private final UserDAO userDAO;
    private final PresentationDAO presentationDAO;
    private final AccessDAO accessDAO;
    private final Cache<String, String> cache;
    final Log log = Log.forClass(PresentationResource.class);

    public PresentationResource (UserDAO userDAO, PresentationDAO presentationDAO, AccessDAO accessDAO, Cache<String, String> cache) {
        this.userDAO = userDAO;
        this.presentationDAO = presentationDAO;
        this.accessDAO = accessDAO;
        this.cache = cache;
    }

    @Path("{presentation_id}/{slide_id}")
    //@Produces({"application/zip"})
    @GET
    public Response getPresentation(@PathParam("presentation_id") long id,
                                           @HeaderParam("login-id") String loginId,
                                           @HeaderParam("session-key") String sessionKey,
                                           @PathParam("slide_id") long slide_id) {
        // Get the loginid and the session id from the request headers
        // and check if they are correct
        // Also check if the user has access to the presentation
        if (sessionKey.isEmpty() || sessionKey == null)
            throw new WebApplicationException(401);
        if (loginId.isEmpty() || loginId == null)
            throw new WebApplicationException(401);
        if (!sessionKey.equals(cache.asMap().get(loginId)))
            throw new WebApplicationException(401);

        if(accessDAO.findByLoginIdPresentationId(loginId, id) == null)
            throw new WebApplicationException(401);

        Presentation presentation = presentationDAO.findById(id);
        // Filesystem code to fetch the file
        // should change to amazon s3 or dropbox
        // Shouldn't do this
        String fileName = presentation.getFolderName()
                + presentation.getName() + '-' + slide_id + ".jpg";
        log.info(presentation.getFolderName());
        try {
//            final InputStream inputStream = new FileInputStream(presentation.getFolderName());
//
//            return new StreamingOutput() {
//                public void write(OutputStream output) throws IOException, WebApplicationException {
//                    try {
//                        output.write(IOUtils.toByteArray(inputStream));
//                    } catch (Exception e) {
//                        throw new WebApplicationException(e);
//                    }
//                }
//            };

            System.out.println(fileName);
            final InputStream inputStream = new FileInputStream(fileName);
            byte[] zipStream = IOUtils.toByteArray(inputStream);
            return Response
                    .ok(zipStream, MediaType.APPLICATION_OCTET_STREAM_TYPE)
                    .header("content-disposition","attachment; filename = " + fileName + ".jpg")
                    .build();

        }
        catch (Exception e) {
            System.out.println(e.getStackTrace());
            throw new WebApplicationException(e);
        }

    }

    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @POST
    public Response postPresentation(@FormDataParam("file") InputStream inputStream, @FormDataParam("file") FormDataContentDisposition fcdsFile) {
        //TODO    Write generic code for authentication and don't hardcode this value
        String fileLocation = "/home/aditya/" + fcdsFile.getFileName();
        try {
            File destFile = new File(fileLocation);
            OutputStream outputStream = new FileOutputStream(destFile);
            outputStream.write(IOUtils.toByteArray(inputStream));
        }
        catch (Exception e){
            throw new WebApplicationException(e);
        }

        return Response.ok().build();
    }

}
