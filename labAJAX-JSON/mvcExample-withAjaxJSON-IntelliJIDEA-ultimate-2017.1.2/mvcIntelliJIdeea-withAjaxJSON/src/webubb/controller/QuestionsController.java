package webubb.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import webubb.domain.Question;
import webubb.domain.User;
import webubb.model.DBManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


/**
 * Created by forest on 5/17/2018.
 */

public class QuestionsController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ((action != null) && action.equals("update")) {
            // We update an asset
            Question quest = new Question(Integer.parseInt(request.getParameter("id")),
                    Integer.parseInt(request.getParameter("userid")),
                    request.getParameter("description"),
                    request.getParameter("answer1"),
                    request.getParameter("answer2"),
                    request.getParameter("answer3"),
                    request.getParameter("answer4"),
                    request.getParameter("correctAnswer"));
            DBManager dbmanager = new DBManager();
            Boolean result = dbmanager.updateQuestion(quest);
            PrintWriter out = new PrintWriter(response.getOutputStream());
            if (result == true) {
                out.println("Update quest succesfully.");
            } else {
                out.println("Error updating quest!");
            }
            out.flush();
        }
        else if ((action != null) && action.equals("getAll")) {
            int userid = Integer.parseInt(request.getParameter("userid"));

            response.setContentType("application/json");
            DBManager dbmanager = new DBManager();
            ArrayList<Question> questions = dbmanager.getUserQuestions(userid);
            ArrayList<String> answers=new ArrayList<>();
            JSONArray jsonAssets = new JSONArray();
            for (int i = 0; i < questions.size(); i++) {
                JSONObject jObj = new JSONObject();
                int id=questions.get(i).getId();
                jObj.put("id", id);
                jObj.put("userid", questions.get(i).getUserid());
                jObj.put("description", questions.get(i).getDescription());
                jObj.put("answer1", questions.get(i).getAnswer1());
                jObj.put("answer2", questions.get(i).getAnswer2());
                jObj.put("answer3", questions.get(i).getAnswer3());
                jObj.put("answer4", questions.get(i).getAnswer4());
                jObj.put("correctAnswer", questions.get(i).getCorrectAnswer());
                jsonAssets.add(jObj);
            }
            PrintWriter out = new PrintWriter(response.getOutputStream());
            out.println(jsonAssets.toJSONString());
            out.flush();
        }
        else if ((action != null) && action.equals("getScore")) {
            int userid = Integer.parseInt(request.getParameter("userid"));

            response.setContentType("application/json");
            DBManager dbmanager = new DBManager();
            int score = Integer.parseInt(request.getParameter("score"));
            boolean ok=false;
            if(dbmanager.getUserAllTimeScore(userid)<score) ok=dbmanager.setUserAllTimeScore(userid,score);
//            ArrayList<Question> questions = dbmanager.getUserQuestions(userid);
//            ArrayList<String> answers=new ArrayList<>();
//            JSONArray jsonAssets = new JSONArray();
//            for (int i = 0; i < questions.size(); i++) {
//                int id = questions.get(i).getId();
//                String correctAnswer = questions.get(i).getCorrectAnswer();
//                if (request.getParameter("answer" + id) == correctAnswer) score += 1;
//                answers.add(request.getParameter("answer" + id));
//            }
//            System.out.println(score);
//            System.out.println(answers);
//            System.out.println(request.getParameterNames().toString());
//            System.out.println(Collections.list(request.getParameterNames()));
            int allTimeScore=dbmanager.getUserAllTimeScore(userid);
            System.out.println(score);
            System.out.println(allTimeScore);
            PrintWriter out1 = new PrintWriter(response.getOutputStream());
            out1.println("Your all time score: "+allTimeScore);
            out1.flush();
        }
        else if ((action != null) && action.equals("getUserAllTimeScore")) {
            int userid = Integer.parseInt(request.getParameter("userid"));

            response.setContentType("application/json");
            DBManager dbmanager = new DBManager();
            int score=dbmanager.getUserAllTimeScore(userid);

            JSONArray jsonAssets = new JSONArray();
            JSONObject jObj = new JSONObject();
            jObj.put("allTimeScore", score);
            jsonAssets.add(jObj);

            PrintWriter out = new PrintWriter(response.getOutputStream());
            out.println(jsonAssets.toJSONString());
            out.flush();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
