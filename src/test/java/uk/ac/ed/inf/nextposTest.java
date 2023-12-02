package uk.ac.ed.inf;import com.fasterxml.jackson.databind.ObjectMapper;import org.junit.Test;import uk.ac.ed.inf.Handler.MainLngLatHandle;import uk.ac.ed.inf.Model.DroneMove;import uk.ac.ed.inf.ilp.constant.OrderStatus;import uk.ac.ed.inf.ilp.data.LngLat;import java.nio.file.Files;import java.nio.file.Paths;import java.time.LocalDate;import java.util.HashSet;import static org.junit.Assert.assertTrue;public class nextposTest {    @Test    public void testIfNextMoveIsCorrect(){        String dateStr = "2023-12-06";        DroneMove[] flightpaths;        try {            // getting the flightpath data from the json file            String json = new String(Files.readAllBytes(Paths.get("resultfiles/flightpath-" +dateStr +".json")));            ObjectMapper mapper = new ObjectMapper();            // returning the selected data (we get the first value as the api returns an array of length 1)            flightpaths = mapper.readValue(json, DroneMove[].class);        } catch (Exception e) {            throw new IllegalArgumentException("The api request was not successful. Error code: " + e.getMessage());        }        for(DroneMove move : flightpaths){            MainLngLatHandle handler = new MainLngLatHandle();            LngLat next = handler.nextPosition(new LngLat(move.getFromLongitude(), move.getFromLatitude()), move.getAngle());//          System.out.println("Current coordinates: "+ (new LngLat(move.getFromLongitude(), move.getFromLatitude())));            if(next.equals(new LngLat(move.getToLongitude(), move.getToLatitude()))) {                assertTrue(true);            }else{                System.out.println(move.getOrderNo());                System.out.println("Angle: "+move.getAngle());                System.out.println("Expected: "+(handler.nextPosition(new LngLat(move.getFromLongitude(), move.getFromLatitude()), move.getAngle())));                System.out.println("Actual: "+(new LngLat(move.getToLongitude(), move.getToLatitude())));                System.out.println("Error " + move.getOrderNo());            }        }        System.out.println("Next position test passed!");    }}