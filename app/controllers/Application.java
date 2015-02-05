package controllers;

import java.util.Map;
import models.TodoListModels;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {
	static int id = 0;
    public static Result task(){
    	response().setContentType("application/json");
		return ok(TodoListModels.task());
    	
    }
    
    public static Result newTask(){
    	
    	Map<String, String[]> params = request().body().asFormUrlEncoded();
    	String name = params.get("param")[0];
    	id= id + 1;
    	return ok(TodoListModels.newTask(name,id));
    }
}
