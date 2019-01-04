import org.json.simple* 
//imports JSONArray, JSONObject, JSONParser, ParseException

import java.util.HashMap;
import java.util.Map;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class JSONReader {
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
    
    public int colorCountDet(long speed) {
        if (speed >= 0.0 && speed < 10.0) {
            return 0;
        } else if (speed >= 10.0 && speed < 20.0) {
            return 1;
        } else if (speed >= 20.0 && speed < 30.0) {
            return 2;
        } else if (speed >= 30.0 && speed < 40.0) {
            return 3;
        } else if (speed >= 40.0 && speed < 50.0) {
            return 4;
        } else if (speed >= 50.0 && speed < 60.0) {
            return 5;
        } else if (speed >= 60.0) { //not sure if this is exhaustive but I assume it is
            return 6;
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
            Object o = parser.parse(new FileReader("must be the name of a file")); //check if it's file name or path to file
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
            Iterator<String> iter = arr.iterator();
            Map<(Long, Long), int[]> coordColors = new HashMap<>();
            
            //tuple of longs = coordinates; string = hex value of color
            while (iter.hasNext()) { //MAY FIRST TEST IF ONE ITERATION WORKS BECAUSE OF HOW LONG ONE JSON FILE IS
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
                JSONObject info = (JSONObject) iter.next();
                (Long, Long) coord = info.get("lat"), info.get("lng");
                if (coordColors.containsKey(coord)) {
                    //get the value from the key (coord)
                    //add one to respective index
                } else {
                    int[] colors = new int[7];
                    long speed = info.get("speed");
                    colors[colorCountDet(speed)] += 1;
                    coordColors.add(coord, colors); //add the new key,value pair
                }
            }
            
            //writing to the geoJSON file
            //at this point, all coords have their 
            geoFile.write(JSON_object.toJSONstring());
            geoFile.flush(); //what da heck does this method do
            
        } catch (FileNoteFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
