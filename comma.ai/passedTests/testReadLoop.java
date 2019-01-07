import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class testReadLoop {
    
    public static void main(String[] args) {
    	
    	JSONParser parser = new JSONParser();

    	try {
    		Object o = parser.parse(new FileReader("data\\2016-07-02--11-56-24.json"));
    		JSONObject jo = (JSONObject) o;
    		
    		JSONArray arr = (JSONArray) jo.get("coords");
    		Iterator<JSONObject> iter = arr.iterator();

            while (iter.hasNext()) {
            JSONObject info = iter.next();
            Double lng = (Double) info.get("lng"); 
            Double lat = (Double) info.get("lat");
            Double speed = (Double) info.get("speed");

            System.out.print("coords: " + lng + ", " + lat);
            System.out.println(" - speed: " + speed);
            }
    	
    	} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}