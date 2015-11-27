package com.kehu.Controler;

import com.kehu.Model.Answer;
import com.kehu.Model.Question;
import com.kehu.Model.User;
import org.apache.velocity.Template;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.context.Context;
import org.apache.velocity.tools.view.VelocityViewServlet;
import org.omg.CORBA.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by huyiming on 15/11/14.
 */
@WebServlet(
        name = "Home",
        urlPatterns = "",
        initParams = {
                @WebInitParam(name="org.apache.velocity.properties", value = "/WEB-INF/velocity.properties")
        }

)
public class Home extends VelocityViewServlet {


    @Override
      protected void setContentType(HttpServletRequest request,
                                    HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
    }

    @Override
    protected Template handleRequest(HttpServletRequest request,
                                     HttpServletResponse response, Context ctx) {

        Current.getCurrentUser(request, response, ctx);
        Vector<Question>  questions = Question.query(20);
        HashMap<Question, Vector<Answer>> questionVectorHashMap = new HashMap<>();
        for(int i = 0 ; i< questions.size(); ++i){
            Vector<Answer> answerVector = Answer.queryByQuestion(questions.get(i).get("id"), 1);
            questionVectorHashMap.put(questions.get(i), answerVector);
        }
        ctx.put("questionList", questions);
        ctx.put("answersMap", questionVectorHashMap);
        return getTemplate("index.html");
    }
}

