package models;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder.In;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;

/**
 * @author NamBV2
 *
 */
public class TodoListModels {
	static Map<Integer, Object> lstTodo = new HashMap<Integer, Object>();

	// Create a new task
	public static String newTask(String todo) {
		int id = 0;
		int size = 0;
		try {
			BasicDBObject document = new BasicDBObject();
			DBCursor dbCursor = ConfigDB.Database().find()
					.skip((int) (ConfigDB.Database().count() - 1));
			if ((int) ConfigDB.Database().count() == 0) {
				id++;
				document.put("_id", id);
				document.put("value", todo);
				size = 1;
				ConfigDB.Database().insert(document);
			} else {
				while (dbCursor.hasNext()) {
					id = Integer
							.parseInt(dbCursor.next().get("_id").toString());
				}
				id++;
				document.put("_id", id);
				document.put("value", todo);
				ConfigDB.Database().insert(document);
				size = (int)ConfigDB.Database().count();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return Integer.toString(size);
	}

	// refresh task
	public static Map<Integer, Object> task() {
		lstTodo = new HashMap<Integer, Object>();
		int key = 1;
		try {
			DBCursor dbCursor = ConfigDB.Database().find();
			while (dbCursor.hasNext()) {
				lstTodo.put(key, dbCursor.next());
				key++;
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return lstTodo;
	}

	// delete task
	public static String deleteTask(int id) {
		BasicDBObject document = new BasicDBObject();
		int size = 0;
		document.put("_id", id);
		try {
			ConfigDB.Database().remove(document);
			size = (int) ConfigDB.Database().count();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return Integer.toString(size);
	}

	// delete items chosen
	public static String deleteItems(String itemChosen[]) {
		int size = 0;
		int item = 0;
		try {
			if (Integer.parseInt(itemChosen[0]) == -1) {
				ConfigDB.Database().remove(new BasicDBObject());
			} else {
				for (String items : itemChosen) {
					item = Integer.parseInt(items);
					System.out.println("Item is:" + item);
					BasicDBObject object = new BasicDBObject();
					object.put("_id", item);
					ConfigDB.Database().remove(object);
					size = (int) ConfigDB.Database().count();
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return Integer.toString(size);
	}

	// edit items
	public static String getValueById(int id) {
		String value = "";
		try {
			BasicDBObject query = new BasicDBObject();
			query.put("_id", id);
			DBCursor dbCursor = ConfigDB.Database().find(query);
			while (dbCursor.hasNext()) {
				value = dbCursor.next().get("value").toString();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		System.out.println("Value is: "+value);
		return value;
	}

	public static String editItem(int id, String newValue) {
		BasicDBObject newDoc = new BasicDBObject();
		newDoc.put("value", newValue);
		BasicDBObject query = new BasicDBObject().append("_id", id);
		try {
			ConfigDB.Database().update(query, newDoc);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return "";
	}
}
