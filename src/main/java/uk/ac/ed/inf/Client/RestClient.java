package uk.ac.ed.inf.Client;import com.fasterxml.jackson.core.type.TypeReference;import com.fasterxml.jackson.databind.ObjectMapper;import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;import uk.ac.ed.inf.ilp.data.NamedRegion;import uk.ac.ed.inf.ilp.data.Order;import uk.ac.ed.inf.ilp.data.Restaurant;import java.io.IOException;import java.net.MalformedURLException;import java.net.URL;import java.util.ArrayList;import java.util.List;public class RestClient {    private String BASE_URL;    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());    public RestClient(String BASE_URL) {        this.BASE_URL = BASE_URL.endsWith("/") ? BASE_URL : BASE_URL + "/";    }    public Restaurant[] getRestaurants() {        try {            URL restaurantsUrl = new URL(BASE_URL + "/restaurants");            return mapper.readValue(restaurantsUrl, Restaurant[].class);        } catch (IOException e) {            System.err.println("Error in URL for getRestaurants: " + e.getMessage());            System.exit(1);        }        return new Restaurant[]{};    }    public List<NamedRegion> getNoFlyZones() {        try {            URL noFlyZonesUrl = new URL(BASE_URL + "/noFlyZones");            return mapper.readValue(noFlyZonesUrl, new TypeReference<List<NamedRegion>>(){});        } catch (IOException e) {            System.err.println("Error in URL for getNoFlyZones: " + e.getMessage());            System.exit(1);        }        return new ArrayList<>();    }    public NamedRegion getCentralArea() {        try {            URL centralAreaUrl = new URL(BASE_URL + "/centralArea");            return mapper.readValue(centralAreaUrl, new TypeReference<NamedRegion>(){});        } catch (IOException e) {            System.err.println("Error in URL for getCentralArea: " + e.getMessage());            System.exit(1);        }        return null;    }    public List<Order> getOrders() {        try {            URL ordersUrl = new URL(BASE_URL + "/orders");            return mapper.readValue(ordersUrl, new TypeReference<List<Order>>(){});        } catch (IOException e) {            System.err.println("Error in URL for getOrders: " + e.getMessage());            System.exit(1);        }        return new ArrayList<>();    }    public List<Order> getOrders(String date) {        try {            URL ordersUrl = new URL(BASE_URL + "/orders/" + date);            List<Order> orders = mapper.readValue(ordersUrl, new TypeReference<List<Order>>(){});            if (orders.isEmpty()) {                System.err.println("No orders found for date: " + date);                System.exit(1);            }            return orders;        } catch (IOException e) {            System.err.println("IO Exception in URL for getOrders(date): " + e.getMessage());            System.exit(1);        }        return new ArrayList<>();    }}