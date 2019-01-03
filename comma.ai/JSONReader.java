import org.json.simple* 
//imports JSONArray, JSONObject, JSONParser, ParseException

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
    
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        
        try {
            FileWriter geoFile = new FileWriter();
            Object o = parser.parse(new FileReader("data path to file folder"));
            JSONObject jo = (JSONObject) o;
            //JSONObjects = (name, value) pairs, noted by curly brackets {}
            //obj has get method --> returns an object I think; need to cast it 
            JSONArray arr = (JSONArray) jo.get("name that holds the array");
            //JSONArrays = lists, noted by brackets []
            //assuming file has an array
            //need to store parsed information in some way that can be later used in data visualization
            
        } catch (FileNoteFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
