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

@WebServlet("/bookDelete")
public class DeleteBookServlet extends HttpServlet {

	private static final long serialVersionUID = 6798036766148281767L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		System.out.println("Here is DeleteBookServlet.doGet()");

		String requestURI=request.getRequestURI();
		String contextPath=request.getContextPath();
		String command=requestURI.substring(contextPath.length());

		System.out.println("\tcontrollerPath ="+requestURI);
		System.out.println("\tcontextPath ="+contextPath);
		System.out.println("\tcommand ="+command);
		
		String id = request.getParameter("id");
		if (id == null || "".equals(id)) {
			throw new ServletException("id missing for delete operation");
		}
		MongoClient mongo = (MongoClient) request.getServletContext()
				.getAttribute("MONGO_CLIENT");
		MongoBookDAO bookDAO = new MongoBookDAO(mongo);
		Book p = new Book();
		p.setId(id);
		bookDAO.deleteBook(p);
		System.out.println("Book deleted successfully with id=" + id);
		request.setAttribute("success", "Book deleted successfully");
		List<Book> books = bookDAO.readAllBook();
		request.setAttribute("persons", books);

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/booklist.jsp");
		rd.forward(request, response);
	}

}
