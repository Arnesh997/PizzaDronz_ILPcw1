package uk.ac.ed.inf.Client;import com.fasterxml.jackson.core.type.TypeReference;import com.fasterxml.jackson.databind.ObjectMapper;import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;import uk.ac.ed.inf.ilp.data.NamedRegion;import uk.ac.ed.inf.ilp.data.Order;import uk.ac.ed.inf.ilp.data.Restaurant;import uk.ac.ed.inf.Logging.Logger;import java.io.IOException;import java.net.URL;import java.time.format.DateTimeParseException;import java.util.ArrayList;import java.util.List;public class RestClient {    private String BASE_URL;    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());    /**     * Constructor, sets the base URL for the server     * @param BASE_URL String containing the baseURL for the REST server     */    public RestClient(String BASE_URL) {        if(BASE_URL.endsWith("/") == false){            BASE_URL += "/";        }        this.BASE_URL = BASE_URL;    }    /**     * Retrieve List of Restaurants from REST Server     * @return Array of Restaurants     */    public Restaurant[] getRestaurants() {        Logger log = Logger.getInstance();        Restaurant[] restaurants = null;        try {            URL restaurantsUrl = new URL(BASE_URL + "/restaurants");            restaurants = mapper.readValue(restaurantsUrl, Restaurant[].class);            log.logAction("RestClient.getRestaurants()", LogStatus.GET_RESTAURANTS_SUCCESS);            return restaurants;        } catch (IOException e){            System.err.println(e);            log.logAction("RestClient.getRestaurants()", LogStatus.IOEXCEPTION);        }        return new Restaurant[]{};    }    /**     * Retrieve no-fly zones from REST Server     * @return List of NamedRegions representing a no-fly zone     */    public List<NamedRegion> getNoFlyZones() {        Logger log = Logger.getInstance();        List<NamedRegion> noFlyZones = null;        try {            URL noFlyZonesUrl = new URL(BASE_URL + "/noFlyZones");            noFlyZones = mapper.readValue(noFlyZonesUrl, new TypeReference<List<NamedRegion>>(){});            log.logAction("RestClient.getNoFlyZones()", LogStatus.GET_NO_FLY_ZONES_SUCCESS);            return noFlyZones;        } catch (IOException e){        System.err.println(e);        log.logAction("RestClient.getNoFlyZones()", LogStatus.IOEXCEPTION);        }        return new ArrayList<>(){};    }    /**     * Retrieve central area NamedRegion from REST Server     * @return NamedRegion containing central area     */    public NamedRegion getCentralArea(){        Logger log = Logger.getInstance();        NamedRegion centralArea = null;        try {            URL centralAreaUrl = new URL(BASE_URL + "/centralArea");            centralArea = mapper.readValue(centralAreaUrl, new TypeReference<NamedRegion>(){});            log.logAction("RestClient.getCentralArea()", LogStatus.GET_CENTRAL_AREA_SUCCESS);            return centralArea;        } catch (IOException e){            System.err.println(e);            log.logAction("RestClient.getCentralArea()", LogStatus.IOEXCEPTION);        }        return null;    }    /**     * Retrieves the list of all orders from the server     * @return List of Orders     */    public List<Order> getOrders() {        Logger log = Logger.getInstance();        List<Order> orders = null;        try {            URL ordersUrl = new URL(BASE_URL + "/orders");            orders = mapper.readValue(ordersUrl, new TypeReference<List<Order>>(){});            log.logAction("RestClient.getOrders()", LogStatus.GET_ALL_ORDERS_SUCCESS);            return orders;        } catch (IOException e) {            System.err.println("Error fetching orders: " + e.getMessage());            System.exit(3);        }        return new ArrayList<>(){};    }    /**     * Retrieves the list of all orders for a given date from the server     * @param date date to retrieve orders for     * @return List of Orders for that day     */    public List<Order> getOrders(String date) {        Logger log = Logger.getInstance();        List<Order> orders = null;        try {            URL ordersUrl = new URL(BASE_URL + "/orders/" + date);            orders = mapper.readValue(ordersUrl, new TypeReference<List<Order>>(){});            if (orders.size() > 0){                log.logAction("RestClient.getOrders(date)", LogStatus.GET_ORDERS_BY_DATE_SUCCESS);            } else {                log.logAction("RestClient.getOrders(date)", LogStatus.GET_ORDERS_BY_DATE_NO_ORDERS_FOR_DATE);            }            return orders;        } catch (IOException e) {            log.logAction("RestClient.getOrders(date)", LogStatus.IOEXCEPTION);            System.err.println(e);        } catch (DateTimeParseException e) {            log.logAction("RestClient.getOrders(date)", LogStatus.GET_ORDERS_BY_DATE_BAD_DATE);            System.err.println(e);        }        return new ArrayList<>(){};    }    private enum LogStatus {        GET_ALL_ORDERS_SUCCESS,        GET_ORDERS_BY_DATE_NO_ORDERS_FOR_DATE,        GET_ORDERS_BY_DATE_BAD_DATE,        GET_ORDERS_BY_DATE_SUCCESS,        GET_RESTAURANTS_SUCCESS,        IOEXCEPTION,        GET_CENTRAL_AREA_SUCCESS,        GET_NO_FLY_ZONES_SUCCESS    }}