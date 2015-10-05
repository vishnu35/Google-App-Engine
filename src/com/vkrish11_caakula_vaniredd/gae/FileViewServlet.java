package com.vkrish11_caakula_vaniredd.gae;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.Channels;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.appidentity.AppIdentityService;
import com.google.appengine.api.appidentity.AppIdentityServiceFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

public class FileViewServlet extends HttpServlet {

	private static final long serialVersionUID = 1659135525119122992L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter pw = resp.getWriter();
		String filename = req.getParameter("fname");
		String from = req.getParameter("from");

		if (from.equals("bucket")) {
			pw.print("File read from bucket");
			pw.print("<br>");
			pw.print("<br>");
			GcsService gcsService = GcsServiceFactory
					.createGcsService(RetryParams.getDefaultInstance());
			AppIdentityService appIdentity = AppIdentityServiceFactory
					.getAppIdentityService();

			GcsFilename asdf = new GcsFilename(
					appIdentity.getDefaultGcsBucketName(), filename);

			GcsInputChannel readChannel = null;
			BufferedReader reader = null;
			try {
				// We can now read the file through the API:
				readChannel = gcsService.openReadChannel(asdf, 0);
				// Again, different standard Java ways of reading from the
				// channel.
				reader = new BufferedReader(Channels.newReader(readChannel,
						"UTF8"));
				String line;
				while ((line = reader.readLine()) != null) {
					pw.print(line);
					pw.print("<br>");
				}
			} finally {
				if (reader != null) {
					reader.close();
				}
			}
		}else if (from.equals("memcache")){
			pw.print("File read from memCache");
			pw.print("<br>");
			pw.print("<br>");
			MemcacheService mservice;
			mservice = MemcacheServiceFactory.getMemcacheService();
			String val = (String) mservice.get(filename);
			pw.print(val);
		}else{
			pw.print("<h4>no File</h4>");
		}
	}
}
