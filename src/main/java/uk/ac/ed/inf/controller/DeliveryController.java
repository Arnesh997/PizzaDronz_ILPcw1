package uk.ac.ed.inf.controller;//import uk.ac.ed.inf.AStarPathControl;import uk.ac.ed.inf.*;import uk.ac.ed.inf.ilp.constant.OrderStatus;import uk.ac.ed.inf.ilp.data.*;import java.time.LocalDate;import java.util.ArrayList;import java.util.HashMap;import java.util.List;public class DeliveryController {    private  Restaurant[] restaurants;    private   List<NamedRegion> noFlyZones;    private  NamedRegion centralArea;    private  List<Order> orders;    private  String BASE_URL;    private  LocalDate date;    private HashMap<String,LngLat> pizzaLocationMap= new HashMap<>();    private LngLat appletonTower = new LngLat(-3.1869, 55.9445);    public DeliveryController(LocalDate date, String BASE_URL){        this.date = date;        this.BASE_URL = BASE_URL;        RestClientController restClientController = new RestClientController(BASE_URL);        this.restaurants = restClientController.getRestaurants();        this.noFlyZones = restClientController.getNoFlyZones();        this.centralArea = restClientController.getCentralArea();        this.orders = restClientController.getOrders();        for(Restaurant restaurant: restaurants){            for(Pizza pizza: restaurant.menu()){                pizzaLocationMap.put(pizza.name(),restaurant.location());            }        }    }//    public LngLat[] getRestaurantLocation(Order)    public void run(){        // Order controlling code        // Updating this.orders with orders on a given date        RestClientController restClientController = new RestClientController(BASE_URL);        orders = restClientController.getOrders(date.toString());        List<Order> validOrders = checkOrders(this.orders);        System.out.println("Valid orders today: "+validOrders.size());//      Resetting status code of all valid orders        for(Order order: validOrders){            order.setOrderStatus(OrderStatus.DELIVERED);        }        DroneDeliveryRecorder droneDeliveryRecorder = new DroneDeliveryRecorder(LocalDate.now());        droneDeliveryRecorder.createDeliveriesFile(validOrders);        // Path-finding code//        AStarPathController pathController = new AStarPathController(this.restaurants, this.noFlyZones, this.centralArea);//        for(Order order : validOrders){////          Sending the restaurant coordinates as the restaurantLocation node//            System.out.println("1st Pizza in order: "+order.getPizzasInOrder()[0].name());//            System.out.println("Restaurant Coordinates: "+pizzaLocationMap.get(order.getPizzasInOrder()[0].name()));////            LngLat restaurantLocation = pizzaLocationMap.get(order.getPizzasInOrder()[0].name());////            List<Node> pathAppletonRestaurant = pathController.findPath(appletonTower, restaurantLocation);//            List<Node> pathRestaurantAppleton = pathController.findPath(restaurantLocation, appletonTower);////            String geoJsonStringPath1 = GeoJsonConverter.convertNodesToGeoJson(pathAppletonRestaurant);//            String geoJsonStringPath2 = GeoJsonConverter.convertNodesToGeoJson(pathRestaurantAppleton);////            System.out.println("Path size (AT -> Restaurant): "+ pathAppletonRestaurant.size());//            System.out.println("Path from Appleton Tower to Restaurant: " + geoJsonStringPath1);//            System.out.println("Path size (Restaurant -> AT): "+ pathRestaurantAppleton.size());//            System.out.println("Path from Restaurant to Appleton Tower: " + geoJsonStringPath2);//        }    }    private List<Order> checkOrders(List<Order> orders) {        List<Order> validOrders = new ArrayList<>();        MainOrderValidation orderValidator = new MainOrderValidation();        for (Order order : orders) {            Order checkedOrder = orderValidator.validateOrder(order, this.restaurants);            if (checkedOrder.getOrderStatus() == OrderStatus.VALID_BUT_NOT_DELIVERED) {                validOrders.add(order); // Only add valid orders to the new list            }        }        return validOrders;    }}