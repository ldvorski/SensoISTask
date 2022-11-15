
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Servlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/Servlet" })
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Servlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><body><div>Zapocinje se obrada</div>");
		out.println("<div>Zahtjev je zaprimio thread = " + Thread.currentThread().getName() + "</div>");
		out.println("<progress id='progress' max='100'></progress>");
		AsyncContext asyncContext = request.startAsync();
		if (request.getParameter("zatrazi") != null) {
			asyncContext.start(() -> {
				out.println("Zahtjev obraduje thread = " + Thread.currentThread().getName() + "<br>");
				for (int i = 0; i <= 100; i++) {
					out.println("<script>document.getElementById('progress').value =\"" + i + "\";</script>");
					out.flush();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
				}
				out.println("<script>document.getElementById('progress').style.display='none';<br>");
				out.println("alert('Obrada je zavrsena');</script>");
				out.println("</body></html>");
			});
		}
		out.println("<div>Thread = " + Thread.currentThread().getName() + " je spreman za novi zahtjev</div>");
	}

}
