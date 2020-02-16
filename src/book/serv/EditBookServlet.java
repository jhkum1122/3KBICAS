package book.serv;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.MongoClient;

import book.dao.MongoBookDAO;
import book.model.Book;

@WebServlet("/bookEdit")
public class EditBookServlet extends HttpServlet {

	private static final long serialVersionUID = -6554920927964049383L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String id = request.getParameter("id");
		
		System.out.println("Here is EditBookServlet.doGet()");

		String requestURI=request.getRequestURI();
		String contextPath=request.getContextPath();
		String command=requestURI.substring(contextPath.length());

		System.out.println("\tcontrollerPath ="+requestURI);
		System.out.println("\tcontextPath ="+contextPath);
		System.out.println("\tcommand ="+command);
		
		if (id == null || "".equals(id)) {
			throw new ServletException("id missing for edit operation");
		}
		System.out.println("Book edit requested with id=" + id);
		MongoClient mongo = (MongoClient) request.getServletContext()
				.getAttribute("MONGO_CLIENT");
		MongoBookDAO bookDAO = new MongoBookDAO(mongo);
		Book p = new Book();
		p.setId(id);
		p = bookDAO.readBook(p);
		bookDAO.updateBook(p);

		request.setAttribute("person", p);
		List<Book> books = bookDAO.readAllBook();
		request.setAttribute("persons", books);

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/books.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String id = request.getParameter("id"); // keep it non-editable in UI
		
		System.out.println("Here is EditBookServlet.doPost()");
		if (id == null || "".equals(id)) {
			throw new ServletException("id missing for edit operation");
		}

		String name = request.getParameter("name");
		String country = request.getParameter("country");

		if ((name == null || name.equals(""))
				|| (country == null || country.equals(""))) {
			request.setAttribute("error", "Name and Country Can't be empty");
			MongoClient mongo = (MongoClient) request.getServletContext()
					.getAttribute("MONGO_CLIENT");
			MongoBookDAO bookDAO = new MongoBookDAO(mongo);
			Book p = new Book();
			p.setId(id);
			p.setName(name);
			p.setCountry(country);
			request.setAttribute("person", p);
			List<Book> books = bookDAO.readAllBook();
			request.setAttribute("persons", books);

			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/books.jsp");
			rd.forward(request, response);
		} else {
			MongoClient mongo = (MongoClient) request.getServletContext()
					.getAttribute("MONGO_CLIENT");
			MongoBookDAO bookDAO = new MongoBookDAO(mongo);
			Book p = new Book();
			p.setId(id);
			p.setName(name);
			p.setCountry(country);
			bookDAO.updateBook(p);
			System.out.println("Book edited successfully with id=" + id);
			request.setAttribute("success", "Book edited successfully");
			List<Book> books = bookDAO.readAllBook();
			request.setAttribute("persons", books);

			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/booklist.jsp");
			rd.forward(request, response);
		}
	}

}
