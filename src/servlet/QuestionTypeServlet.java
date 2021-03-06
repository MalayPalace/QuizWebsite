package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class QuestionTypeServlet
 */
@WebServlet("/QuestionTypeServlet")
public class QuestionTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuestionTypeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String questionType = request.getParameter("newQuestionType");
		
		if(questionType.equals("")){
			RequestDispatcher dispatch = request.getRequestDispatcher("createQuiz/chooseQuestionType.jsp");
			dispatch.forward(request, response);
		}else{
			HttpSession session = request.getSession();
			session.setAttribute("QuestionType", questionType);
			String pageString = "new_quiz_question";
			
			if(questionType.equals("Question-Response")){
				pageString = "createQuiz/new_quiz_question.jsp";
			}else if(questionType.equals("Fill in the Blank")){
				pageString = "createQuiz/new_quiz_question_fillInBlank.jsp";
			}else if(questionType.equals("Multiple Choice")){
				pageString = "createQuiz/new_quiz_question_multiChoice.jsp";
			}else if(questionType.equals("Picture-Response Questions")){
				pageString = "createQuiz/new_quiz_question_picture.jsp";
			}else if(questionType.equals("Multiple-Answer Questions")){
				pageString = "createQuiz/new_quiz_question_multiAnswer.jsp";
			}else if(questionType.equals("Multiple Choice with Multiple Answers")){
				pageString = "createQuiz/new_quiz_question_multiChoiceAndAnswer.jsp";
			}else if(questionType.equals("Matching")){
				pageString = "createQuiz/new_quiz_question_matching.jsp";
			}
			
			RequestDispatcher dispatch = request.getRequestDispatcher(pageString);
			dispatch.forward(request, response);
		}
	}
}
