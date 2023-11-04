package uk.ac.ed.inf;import org.junit.Before;import org.junit.Test;import uk.ac.ed.inf.controller.RestClientController;import uk.ac.ed.inf.ilp.data.Restaurant;import java.io.IOException;public class RestTest {    private RestClientController restClientController;    @Before    public void setUp() throws IOException {        restClientController = new RestClientController("https://ilp-rest.azurewebsites.net");    }    @Test    public void testGetRestaurants(){        Restaurant[] restaurants = restClientController.getRestaurants();        for (Restaurant restaurant : restaurants) {            System.out.println(restaurant);        }    }    @Test    public void testGetCentralArea(){        System.out.println(restClientController.getCentralArea());    }    @Test    public void testGetNoFlyZones(){        System.out.println(restClientController.getNoFlyZones());    }    @Test    public void testGetOrders(){        System.out.println(restClientController.getOrders());    }    @Test    public void testGetOrdersForDate(){        System.out.println(restClientController.getOrders("2023-09-01"));    }}