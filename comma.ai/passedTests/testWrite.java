import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class testWrite {
    //I need the speed for all coordinates for each trip
    //one trip = one JSON file
    //the JSON file contains an ARRAY of coordinates and speed sampled at once per second
    //Idea: parse through original JSON files and transfer data into a NEW geoJSON file
    
    //code based on:
    //https://www.mkyong.com/java/json-simple-example-read-and-write-json/
    //JSON file writing references: 
    //https://leafletjs.com/examples/geojson/
    //https://stackoverflow.com/questions/31728446/write-a-json-file-in-java
    
    //https://www.openstreetmap.org/#map=5/38.007/-95.844
    //https://maptimeboston.github.io/leaflet-intro/
    
    static String colorDet(Double speed) {
        //color relates to speed
            /* HTML HEX COLOR CODES
             * RED: 0xFF0000 --> [0, 10)
             * ORANGE: 0xFFA233 --> [10, 20)
             * YELLOW: 0xFFFF00 --> [20, 30)
             * GREEN: 0x008000 --> [30, 40)
             * BLUE: 0x0074FF --> [40, 50)
             * PURPLE: 0x800080 --> [50, 60)
             * VIOLET: 0x3E0099 --> 60+
             */
        if (speed >= 0.0 && speed < 10.0) {
            return "#ff0000";
        } else if (speed >= 10.0 && speed < 20.0) {
            return "#ffa233";
        } else if (speed >= 20.0 && speed < 30.0) {
            return "ffff00";
        } else if (speed >= 30.0 && speed < 40.0) {
            return "#008000";
        } else if (speed >= 40.0 && speed < 50.0) {
            return "#0074ff";
        } else if (speed >= 50.0 && speed < 60.0) {
            return "#800080";
        } else { 
            return "#3e0099";
        }
    }
    
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        
        try {
            FileWriter geoFile = new FileWriter("data.geojson"); //might need to specify location on computer w/ path
            
            /* WILL FIRST TEST ON ONE FILE, THEN LOOP THROUGH DIRECTORY
            //reference: https://stackoverflow.com/questions/1844688/how-to-read-all-files-in-a-folder-from-java
            File folder = new File("data path to folder");
            File[] listOfJSONFiles = folder.listFiles();
            for (File JSONfile : listOfJSONFiles) {
                String fileName = JSONfile.getName();
                System.out.println(fileName);
                //use this String to for parsing purposes
                //checking w/ print statement
            }*/ 
            
            //reading the JSON file
            Object o = parser.parse(new FileReader("data\\2016-07-02--11-56-24.json")); 
            /* JSON file format
             * 3 objects: start time, coords, end time
             * "start_time": String
             * "coords": ObjectArray --> one element in array = "lat": Long, "speed": Long, "lng": Long, "dist": Long, "index": int
             * "end_time": String
             */
            JSONObject jo = (JSONObject) o;
            //JSONObjects = (name, value) pairs, noted by curly brackets {}
            //obj has get method --> returns an object I think; need to cast it 
            JSONArray arr = (JSONArray) jo.get("coords");
            //JSONArrays = lists, noted by brackets []
            //assuming file has an array
            //need to store parsed information in some way that can be later used in data 
            Iterator<JSONObject> iter = arr.iterator();
                
            JSONObject info = iter.next(); 
            Double lng = (Double) info.get("lng"); Double lat = (Double) info.get("lat");
            Double speed = (Double) info.get("speed");
            String color = colorDet(speed);
            System.out.println(color);
            
            //writing to the geoJSON file 
            JSONObject result = new JSONObject();
            
            JSONArray feats = new JSONArray();
            
                JSONObject point = new JSONObject();
                
                point.put("type", "Feature");

                JSONObject props = new JSONObject();
                props.put("color", color);
                point.put("properties", props);

                JSONObject geo = new JSONObject();
                JSONArray coords = new JSONArray();
                coords.add(lng); coords.add(lat);
                geo.put("coordinates", coords);
                geo.put("type", "Point");
                point.put("geometry", geo);

                feats.add(point);
            
            result.put("features", feats);
            result.put("type", "FeatureCollection");
      
            geoFile.write(result.toJSONString());
            geoFile.flush(); //flushes the stream ?
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
