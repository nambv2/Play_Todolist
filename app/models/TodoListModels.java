package models;

import java.net.UnknownHostException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;

/**
 * @author NamBV2
 *
 */
public class TodoListModels {
	BasicDBObject document = new BasicDBObject();
	
	//Create a new task
	public static String newTask(String todo,int id){
		BasicDBObject document = new BasicDBObject();
		document.put("id", id);
		document.put("value", todo);
		try {
			ConfigDB.Database().insert(document);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return Integer.toString(id);
	}
	
	//refresh task
	public static String task(){
		
		String doc="";
		try {
			DBCursor dbCursor = ConfigDB.Database().find();
			while(dbCursor.hasNext()){
				doc+=dbCursor.next()+",";
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return doc;
	}
}
