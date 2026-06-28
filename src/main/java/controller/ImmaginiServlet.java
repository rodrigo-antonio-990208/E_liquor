package controller;


import java.io.*;
import java.sql.SQLException;
import java.util.UUID;

import dao.ProdottoDaoImpl;
import dao.ProdottoDao;
import model.Prodotto;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import javax.sql.DataSource;

/**
 * Servlet implementation class ImmaginiServlet
 */
@WebServlet("/Immagini")

@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
	    maxFileSize = 1024 * 1024 * 10,       // 10MB
	    maxRequestSize = 1024 * 1024 * 50     // 50MB
	)

public class ImmaginiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIR = "uploads";
	private ProdottoDao dao;
    
	public void init (ServletConfig config) throws ServletException {
		super.init(config);
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		if (ds == null) {
			throw new ServletException ("datasource non trovato");
		}
		dao = new ProdottoDaoImpl(ds);
		String uploadPath = getServletContext().getRealPath(File.separator +UPLOAD_DIR);
		File uploadDir = new File (uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
		String action = request.getParameter("action");
		if (action.equalsIgnoreCase("show")) {
			int codiceProd = Integer.parseInt(request.getParameter("codice"));
			try {
			Prodotto prod = dao.doRetrieveByKey(codiceProd);
			String mimeType = prod.getMime();
			String urlPath = prod.getImmagineUrl();
			response.setContentType(mimeType);
			try(InputStream in = new FileInputStream (urlPath)){
				OutputStream out = response.getOutputStream ();
				in.transferTo(out);
			}catch (IOException se) {
				System.err.println(" error: "+ se.getMessage());
			}
			}catch (SQLException e) {
				System.err.println("error: "+ e.getMessage());
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if ("upload".equalsIgnoreCase(action)) {
			int codiceProd = Integer.parseInt(request.getParameter("codiceProd"));
			Part part = request.getPart("immagine");
			if (part != null) {
				String fileNameOriginale = part.getSubmittedFileName();
				if (fileNameOriginale != null && !fileNameOriginale.isEmpty() && part.getSize() > 0) {
					String mimeType = part.getContentType();
					String fileNameUnico = buildUniqueFileName(part);
					String uploadPath =  getServletContext().getRealPath(File.separator+UPLOAD_DIR+File.separator+fileNameUnico);
					Prodotto prod = new Prodotto();
					prod.setIdProdotto(codiceProd);
					prod.setMimeType(mimeType);
					prod.setImmagineUrl(uploadPath);
					try{
						part.write (uploadPath);
						dao.doUpdateImage(prod);
						System.out.println (uploadPath);
					}catch (SQLException e) {
						System.err.println ("error :" + e.getMessage());
					}
				}
			}
		}
		response.sendRedirect ("GestioneProdottoServlet");
	}

	private String buildUniqueFileName(Part part) {
		String originalName = part.getSubmittedFileName();
		String ext;
		if (originalName.contains(".")) {
			ext = originalName.substring(originalName.lastIndexOf("."));}
			else {
				ext = "";
			}
		return UUID.randomUUID()+ext;
		}
	}

