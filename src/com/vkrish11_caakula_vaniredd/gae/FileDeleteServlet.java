package com.vkrish11_caakula_vaniredd.gae;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.appidentity.AppIdentityService;
import com.google.appengine.api.appidentity.AppIdentityServiceFactory;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

public class FileDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 3979024099827093686L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String filename = req.getParameter("fname");
		
		GcsService gcsService = GcsServiceFactory.createGcsService(RetryParams.getDefaultInstance());
		AppIdentityService appIdentity = AppIdentityServiceFactory.getAppIdentityService();
		
		GcsFilename asdf  = new GcsFilename(appIdentity.getDefaultGcsBucketName(),filename);
		gcsService.delete(asdf);
		resp.getWriter().println("file '"+filename+"' deleted!");
		
	}
}
