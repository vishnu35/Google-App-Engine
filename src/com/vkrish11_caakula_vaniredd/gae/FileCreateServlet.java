package com.vkrish11_caakula_vaniredd.gae;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.Channels;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;
import com.google.appengine.api.files.GSFileOptions.GSFileOptionsBuilder;
import com.google.appengine.api.search.query.ExpressionParser.str_return;

public class FileCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String BUCKET_NAME = "vkrish11-caakula-vaniredd.appspot.com";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Creating file..");
		
		String filename = req.getParameter("createfilename");
		String filedescription = req.getParameter("createfiledescription");
		//resp.getWriter().println(filename);
		//resp.getWriter().println(filedescription);
		
		FileService fileService = FileServiceFactory.getFileService();

		GSFileOptionsBuilder optionsBuilder = new GSFileOptionsBuilder()
		  .setBucket(BUCKET_NAME)
		  .setKey(filename)
		  .setAcl("public-read");
		AppEngineFile writableFile = fileService.createNewGSFile(optionsBuilder.build());
		FileWriteChannel writeChannel = fileService.openWriteChannel(writableFile, true);
		PrintWriter out = new PrintWriter(Channels.newWriter(writeChannel, "UTF8"));
		out.println(filedescription);
		out.close();
		writeChannel.closeFinally();
		
		resp.getWriter().println("<br><br>file '"+filename+"' created");
	}
}
