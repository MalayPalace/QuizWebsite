package quizweb.question;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import quizweb.Quiz;
import quizweb.XMLElement;
import quizweb.database.DBConnection;


public class MultipleChoiceQuestion extends Question {
//	Cast Type Summary:
//	ArrayList<String>	question;
//	String				answer;
//	String				userAnswer;
	static final String DBTable = "multiple_choice_question";
	
	public MultipleChoiceQuestion(int quizID, int position, Object question, Object answer, double score) {
		super(quizID, position, question, answer, score);
	}
	
	@SuppressWarnings("unchecked")
	public void addQustionToDB() {	
		String questionStr = getConcatedString((ArrayList<String>) question);
		String answerStr = (String) answer;
		// add to database
		try {
			String statement = new String("INSERT INTO " + DBTable 
					+ " (quizid, position, question, answer, score) VALUES (?, ?, ?, ?, ?)");
			PreparedStatement stmt = DBConnection.con.prepareStatement(statement, new String[] {"questionid"});
			stmt.setInt(1, quizID);
			stmt.setInt(2, position);
			stmt.setString(3, questionStr);
			stmt.setString(4, answerStr);
			stmt.setDouble(5, score);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			questionID = rs.getInt("GENERATED_KEY");
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}				
	}
	
	public MultipleChoiceQuestion(int questionID, int quizID, int position, Object question, Object answer, double score) {
		super(quizID, position, question, answer, score);
		this.questionID = questionID;
	}
	
	public static ArrayList<Question> getQuestionsByQuizID(int quizID) {
		ArrayList<Question> questionList = new ArrayList<Question>();
		try {
			String statement = new String("SELECT * FROM " + DBTable + " WHERE quizid = ?");
			PreparedStatement stmt = DBConnection.con.prepareStatement(statement);
			stmt.setInt(1, quizID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				ArrayList<String> questionStringList = getParsedStrings(rs.getString("question"));
				String answerString = rs.getString("answer");				
				MultipleChoiceQuestion q = new MultipleChoiceQuestion(
						rs.getInt("questionid"), rs.getInt("quizid"), rs.getInt("position"), 
						questionStringList, answerString, rs.getDouble("score"));
				questionList.add(q);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return questionList;
	}
	
	public static MultipleChoiceQuestion getQuestionByQuestionID(int questionID) {
		try {
			String statement = new String("SELECT * FROM " + DBTable + " WHERE questionid = ?");
			PreparedStatement stmt = DBConnection.con.prepareStatement(statement);
			stmt.setInt(1, questionID);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			
			ArrayList<String> questionStringList = getParsedStrings(rs.getString("question"));
			String answerString = rs.getString("answer");
			MultipleChoiceQuestion q = new MultipleChoiceQuestion(
					rs.getInt("questionid"), rs.getInt("quizid"), rs.getInt("position"), 
					questionStringList, answerString, rs.getDouble("score"));
			rs.close();
			return q;
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return null;	
	}	
	
	@SuppressWarnings("unchecked")
	public int getCorrectChoiceIndex() {
		ArrayList<String> questionList = (ArrayList<String>) question;
		for (int i = 0; i < questionList.size(); i++) {
			if (questionList.get(i).equals((String)answer))
				return i-1;
		}
		System.out.println("Cannot find correct choice index");
		return -1;
	}
	
	@Override
	public double getScore(Object userAnswer) {
		if (userAnswer == null)
			return 0;
		String ans = (String) userAnswer;
		String trueAns = (String) answer;
		if (ans.equals(trueAns)) 
			return score;
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String displayQuestion(int position) {
		ArrayList<String> questionList = (ArrayList<String>) question;
		String questionStr = questionList.get(0);
		StringBuilder sb = new StringBuilder();
		sb.append("<span class=\"dominant_text\">" + position + ".</span>\n");
		sb.append("<span class=\"quiz_title\">\n");
		sb.append("<span class=\"dominant_text\">Multiple Choice Question (" + score + " points):</span><br /><br />\n");
		sb.append(questionStr + "\n");
		sb.append("</span><br /><br />\n");
		sb.append("<div><span>\n");
		for (int i = 1; i < questionList.size(); i++) {
			sb.append("<input id=\"Field" + position + "_" + i + "\" name=\"user_answer" + position + "\" type=\"radio\" class=\"field radio\" value=\"");
			sb.append(questionList.get(i) + "\" tabindex=\"3\" />");
			sb.append(i + ". " + questionList.get(i));
			sb.append("<br /><br />\n");
		}
		sb.append("</span></div>\n");
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String displayQuestionWithAnswer(int position, Object userAnswer) {
		ArrayList<String> questionList = (ArrayList<String>) question;
		String questionStr = questionList.get(0);
		String answerStr = (String) answer;
		String userAnswerStr = (String) userAnswer;
		boolean correct = false;
		if (getScore(userAnswer) == score)
			correct = true;
		StringBuilder sb = new StringBuilder();
		sb.append("<span class=\"dominant_text\">Feedback for Question " + position + " (Score: " + Math.round(getScore(userAnswer)*100)/100.0 + "/" + Math.round(score*100)/100.0 + ")" + ":</span><br /><br />\n");
		sb.append("<span class=\"quiz_title\">\n");
		sb.append(questionStr + "\n");
		sb.append("</span><br /><br />\n");
		sb.append("<div><span>\n");
		for (int i = 1; i < questionList.size(); i++) {
			sb.append("<input id=\"Field" + position + "_" + i + "\" name=\"user_answer" + position + "\" type=\"radio\" class=\"field radio\" value=\"");
			sb.append("No\" tabindex=\"3\" />");
			sb.append(i + ". " + questionList.get(i));
			sb.append("<br /><br />\n");
		}
		sb.append("<p class=\"answer\">Your answer is :\n");
		if (correct) {
			sb.append("<span class=\"correct answer\">" + userAnswerStr + "&#160;&#160;</span>");
			sb.append("<img class=\"small\" src=\"images/right.png\"></p><br /><br />");
		} else {
			sb.append("<span class=\"wrong answer\">" + userAnswerStr + "&#160;&#160;</span>");
			sb.append("<img class=\"small\" src=\"images/wrong.png\"><span class=\"wrong\">incorrect</span></p><br />\n");
			sb.append("<p class=\"answer\">Correct answer :  <span class=\"correct answer\">");
			sb.append(answerStr);
			sb.append("</span></p>\n");
		}
		return sb.toString();
	}	
	
	
	public static MultipleChoiceQuestion getMultipleChoiceQuestionByXMLElem(XMLElement root, Quiz quiz, int pos) {
		int quizID = quiz.quizID;
		int position = pos;
		Object question = null;
		Object answer = null;
		double score = 10;
		ArrayList<String> questionList = new ArrayList<String>();		
		for (int i = 0; i < root.childList.size(); i++) {
			XMLElement elem = root.childList.get(i);
			if (elem.name.equals("query")) {
				questionList.add(elem.content);
			} else if (elem.name.equals("option")) {
				questionList.add(elem.content);
				if (elem.attributeMap.containsKey("answer")) 
					answer = elem.content;
			} else if (elem.name.equals("score")) {
				score = Double.parseDouble(elem.content);
			} else {
				System.out.println("Unexpected field in multiple choice question : " + elem.name);
			}
		}
		question = questionList;
		return new MultipleChoiceQuestion(quizID, position, question, answer, score);
	}

}
