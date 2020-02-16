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

@WebServlet("/bookAdd")
public class AddBookServlet extends HttpServlet {

	private static final long serialVersionUID = -7060758261496829905L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String name = request.getParameter("name");
		String country = request.getParameter("country");
		
		System.out.println("Here is AddBookServlet.doPost()");

		String requestURI=request.getRequestURI();
		String contextPath=request.getContextPath();
		String command=requestURI.substring(contextPath.length());

		System.out.println("\tcontrollerPath ="+requestURI);
		System.out.println("\tcontextPath ="+contextPath);
		System.out.println("\tcommand ="+command);
		
		if ((name == null || name.equals(""))
				|| (country == null || country.equals(""))) {
			
			System.out.println("Here is AddBookServlet.doPost()\tname, country == null");

			request.setAttribute("error", "Mandatory Parameters Missing");
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/books.jsp");
			rd.forward(request, response);
		} else {
			
			System.out.println("Here is AddBookServlet.doPost()\tname, country != null");

			Book b = new Book();
			b.setCountry(country);
			b.setName(name);
			MongoClient mongo = (MongoClient) request.getServletContext()
					.getAttribute("MONGO_CLIENT");
			MongoBookDAO bookDAO = new MongoBookDAO(mongo);
			bookDAO.createBook(b);
			System.out.println("book Added Successfully with id="+b.getId());
			request.setAttribute("success", "book Added Successfully");
			List<Book> books = bookDAO.readAllBook();
			request.setAttribute("persons", books);

			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/booklist.jsp");
			rd.forward(request, response);
		}
	}

}
