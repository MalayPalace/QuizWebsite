package quizweb.question;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import quizweb.Quiz;
import quizweb.XMLElement;
import quizweb.database.DBConnection;


public class MultiAnswerQuestion extends Question {
//	Cast Type Summary:
//	String 				question;
//	ArrayList<String>	answer;
//	ArrayList<String>	userAnswer;
	static final String DBTable = "multiple_answer_question";
	public int answerNumber;
	public boolean isOrdered;
	
	public MultiAnswerQuestion(int quizID, int position, Object question, Object answer, double score, int answerNumber, boolean isOrdered) {
		super(quizID, position, question, answer, score);
		this.answerNumber = answerNumber;
		this.isOrdered = isOrdered;
	}
	
	@SuppressWarnings("unchecked")
	public void addQustionToDB() {
		String questionStr = (String) question;
		String answerStr = getConcatedString((ArrayList<String>) answer);
		// add to database
		try {
			String statement = new String("INSERT INTO " + DBTable 
					+ " (quizid, position, question, answer, score, answernumber, isordered) VALUES (?, ?, ?, ?, ?, ?, ?)");
			PreparedStatement stmt = DBConnection.con.prepareStatement(statement, new String[] {"questionid"});
			stmt.setInt(1, quizID);
			stmt.setInt(2, position);
			stmt.setString(3, questionStr);
			stmt.setString(4, answerStr);
			stmt.setDouble(5, score);
			stmt.setInt(6, answerNumber);
			stmt.setBoolean(7, isOrdered);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			questionID = rs.getInt("GENERATED_KEY");
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public MultiAnswerQuestion(int questionID, int quizID, int position, Object question, Object answer, double score, int answerNumber, boolean isOrdered) {
		super(quizID, position, question, answer, score);
		this.questionID = questionID;
		this.answerNumber = answerNumber;
		this.isOrdered = isOrdered;
	}	

	public static ArrayList<Question> getQuestionsByQuizID(int quizID) {
		ArrayList<Question> questionList = new ArrayList<Question>();
		try {
			String statement = new String("SELECT * FROM " + DBTable + " WHERE quizid = ?");
			PreparedStatement stmt = DBConnection.con.prepareStatement(statement);
			stmt.setInt(1, quizID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {		
				String questionString = rs.getString("question");
				ArrayList<String> answerStringList = getParsedStrings(rs.getString("answer"));					
				MultiAnswerQuestion q = new MultiAnswerQuestion(
						rs.getInt("questionid"), rs.getInt("quizid"), rs.getInt("position"), 
						questionString, answerStringList, rs.getDouble("score"), rs.getInt("answernumber"), rs.getBoolean("isordered"));
				questionList.add(q);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return questionList;
	}
	
	public static MultiAnswerQuestion getQuestionByQuestionID(int questionID) {
		try {
			String statement = new String("SELECT * FROM " + DBTable + " WHERE questionid = ?");
			PreparedStatement stmt = DBConnection.con.prepareStatement(statement);
			stmt.setInt(1, questionID);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			
			String questionString = rs.getString("question");
			ArrayList<String> answerStringList = getParsedStrings(rs.getString("answer"));
			MultiAnswerQuestion q = new MultiAnswerQuestion(
					rs.getInt("questionid"), rs.getInt("quizid"), rs.getInt("position"), 
					questionString, answerStringList, rs.getDouble("score"), rs.getInt("answernumber"), rs.getBoolean("isordered"));
			rs.close();
			return q;
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return null;	
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public double getScore(Object userAnswer) {
		if (userAnswer == null)
			return 0;
		ArrayList<String> ans = (ArrayList<String>) userAnswer;
		ArrayList<String> trueAns = (ArrayList<String>) answer;
		int matches = 0;		
		if (isOrdered) {
			for (int i = 0; i < Math.min(ans.size(), trueAns.size()); i++) {
				if (ans.get(i).equals(trueAns.get(i)))
					matches++;
			}
		} else {
			for (int i = 0; i < Math.min(ans.size(), trueAns.size()); i++) {
				for (int j = 0; j < trueAns.size(); j++) {
					if (ans.get(i).equals(trueAns.get(j))) {
						matches++;
						break;
					}
				}
			}
		}
		return score * matches / answerNumber;		
	}

	@Override
	public String displayQuestion(int position) {
		String questionStr = (String) question;
		StringBuilder sb = new StringBuilder();
		sb.append("<span class=\"dominant_text\">" + position + ".</span>\n");
		sb.append("<span class=\"quiz_title\">\n");
		sb.append("<span class=\"dominant_text\">Multi-Answer Question (" + score + " points):</span><br /><br />\n");
		sb.append(questionStr + "\n");
		sb.append("</span><br /><br />\n");
		sb.append("<p>Please answer below: </p>\n");
		sb.append("<div>");
		for (int i = 0; i < answerNumber; i++) {
			sb.append("<input id=\"Field1\" name=\"user_answer" + position + "_" + (i+1) + "\" type=\"text\" class=\"field text large\" value=\"\" maxlength=\"50\" tabindex=\"1\" onkeyup=\"validateRange(2, 'character');\" />");
		}
		sb.append("</div>");
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String displayQuestionWithAnswer(int position, Object userAnswer) {
		String questionStr = (String) question;
		ArrayList<String> answerList = (ArrayList<String>) answer;
		ArrayList<String> userAnswerList = (ArrayList<String>) userAnswer;
		boolean correct = false;
		if (getScore(userAnswer) == score)
			correct = true;
		StringBuilder sb = new StringBuilder();
		sb.append("<span class=\"dominant_text\">Feedback for Question " + position + " (Score: " + Math.round(getScore(userAnswer)*100)/100.0 + "/" + Math.round(score*100)/100.0 + ")" + ":</span><br /><br />\n");
		sb.append("<span class=\"quiz_title\">\n");
		sb.append(questionStr + "\n");
		sb.append("</span><br /><br />\n");
		sb.append("<p class=\"answer\">Your answer is :<br /><br />\n");
		if (correct) {
			sb.append("<span class=\"correct answer\">\n");
			for (int i = 0; i < userAnswerList.size(); i++) {
				sb.append(userAnswerList.get(i) + "<br /><br />");
			}
			sb.append("&#160;&#160;</span>\n");
			sb.append("<img class=\"small\" src=\"images/right.png\"></p><br /><br />");
		} else {
			sb.append("<span class=\"wrong answer\">\n");
			for (int i = 0; i < userAnswerList.size(); i++) {
				sb.append(userAnswerList.get(i) + "<br /><br />");
			}
			sb.append("&#160;&#160;</span>\n");
			sb.append("<img class=\"small\" src=\"images/wrong.png\"><span class=\"wrong\">incorrect</span></p><br />\n");
			sb.append("<p class=\"answer\">Correct answer :  <br /><br/><span class=\"correct answer\">");
			for (int i = 0; i < answerList.size(); i++) {
				sb.append(answerList.get(i) + "<br /><br />");
			}
			sb.append("</span></p>\n");
		}
		return sb.toString();
	}	
	
	
	public static MultiAnswerQuestion getMultiAnswerQuestionByXMLElem(XMLElement root, Quiz quiz, int pos) {
		int quizID = quiz.quizID;
		int position = pos;
		Object question = null;
		Object answer = null;
		double score = 10;
		int answerNumber = 0;
		boolean isOrdered = false;
		if (root.attributeMap.containsKey("isordered") && root.attributeMap.get("isordered").equals("true"))
			isOrdered = true;
		for (int i = 0; i < root.childList.size(); i++) {
			XMLElement elem = root.childList.get(i);
			if (elem.name.equals("query")) {
				question = (String) elem.content;
			} else if (elem.name.equals("answer-list")) {
				answer = Question.getAnswerListByXMLElem(elem);
			} else if (elem.name.equals("score")) {
				score = Double.parseDouble(elem.content);
			} else if (elem.name.equals("answer-number")){
				answerNumber = Integer.parseInt(elem.content);
			} else {
				System.out.println("Unexpected field in response question : " + elem.name);
			}
		}
		return new MultiAnswerQuestion(quizID, position, question, answer, score, answerNumber, isOrdered);
	}
}
