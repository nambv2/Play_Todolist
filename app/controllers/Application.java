package controllers;

import java.util.Map;
import com.google.gson.Gson;
import models.TodoListModels;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {
    public static Result index(){
    	return ok(views.html.index.render());    	
    }
    
    //load all task
    public static Result task(){
    	String lstTodo = new Gson().toJson(TodoListModels.task().values());
    	return ok(lstTodo);
    }
    
    //create new todo items
    public static Result newTask(){
    	int id = 0;
    	Map<String, String[]> params = request().body().asFormUrlEncoded();
    	String name = params.get("param")[0];
    	id++;
    	return ok(TodoListModels.newTask(name));
    }
    
    //Delete items
    public static Result deleteTask(){
    	Map<String, String[]> params = request().body().asFormUrlEncoded();
    	String itemChoose = params.get("itemChoose")[0];
    	System.out.println("Value choose is: "+itemChoose);
    	return ok(TodoListModels.deleteTask(Integer.parseInt(itemChoose)));
    }
    
    //Delete items was chosen
    public static Result deleteItems(){
    	Map<String, String[]> params = request().body().asFormUrlEncoded();
    	String itemChosen[] = params.get("deleteItem[]");
    	return ok(TodoListModels.deleteItems(itemChosen));
    }
    
    //edit item
    public static Result getValue(){
    	Map<String, String[]> params = request().body().asFormUrlEncoded();
    	String id = params.get("idEdit")[0];
		return ok(TodoListModels.getValueById(Integer.parseInt(id)));
	}
    public static Result editItem(){
    	Map<String, String[]> params = request().body().asFormUrlEncoded();
    	String id = params.get("idEdit")[0];
    	String newValue = params.get("newValue")[0];
    	return ok(TodoListModels.editItem(Integer.parseInt(id), newValue));
    }
}
