package uk.ac.ed.inf;import static org.junit.Assert.assertFalse;import static org.junit.Assert.assertTrue;import org.junit.BeforeClass;import org.junit.Test;import uk.ac.ed.inf.Handler.MainOrderValidation;import uk.ac.ed.inf.ilp.data.*;import uk.ac.ed.inf.ilp.constant.*;import java.time.DayOfWeek;import java.time.LocalDate;public class OrderValidationTest {    static Order order1;    static Order order2;    static Order order3;    static Order order4;    static Order order5;    static Order order6;    private static MainOrderValidation obj = new MainOrderValidation();    @BeforeClass    public static void setUp() {//      Restaurant 1: Civerinos Slice        LngLat civerinosSliceLngLat = new LngLat(-3.1912869215011597,  55.945535152517735);        DayOfWeek[] civerinosSliceOpenDay = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY};        Pizza[] civerinosMenu = {new Pizza("Margarita", 1000), new Pizza("Calzone", 1400)};//  Restaurant 2: Sora Lella Vegan Restaurant        LngLat soraLellaLngLat = new LngLat(-3.202541470527649, 55.943284737579376);        DayOfWeek[] soraLellaOpenDay = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY};        Pizza[] soraLellaMenu = {new Pizza("Meat Lover=", 1400), new Pizza("Vegan Delight", 1100)};//  Restaurant 3: Domino's Pizza - Edinburgh - Southside        LngLat dominosLngLat = new LngLat(-3.1838572025299072, 55.94449876875712);        DayOfWeek[] dominosOpenDay = {DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY};        Pizza[] dominosMenu = {new Pizza("Super Cheese", 1400), new Pizza("All Shrooms", 900)};//  Restaurant 4: Sodeberg Pavillion        LngLat sodebergLngLat = new LngLat(-3.1940174102783203, 55.94390696616939);        DayOfWeek[] sodebergOpenDay = {DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY};        Pizza[] sodebergMenu = {new Pizza("Proper Pizza", 1400), new Pizza("Pineapple & Ham & Cheese", 900)};        Restaurant[] restaurants = new Restaurant[4];        restaurants[0] = new Restaurant("Civerinos Slice", civerinosSliceLngLat, civerinosSliceOpenDay, civerinosMenu);        restaurants[1] = new Restaurant("Sora Lella Vegan Restaurant", soraLellaLngLat, soraLellaOpenDay, soraLellaMenu);        restaurants[2] = new Restaurant("Domino's Pizza - Edinburgh - Southside", dominosLngLat, dominosOpenDay, dominosMenu);        restaurants[3] = new Restaurant("Sodeberg Pavillion", sodebergLngLat, sodebergOpenDay, sodebergMenu);        MainOrderValidation obj = new MainOrderValidation();        obj.setup(restaurants);//      Creating an order array with sample orders for testing//       Order[] orders = new Order[1];//      Sample order 1 - Correct Pizzas        Pizza pizza1 = new Pizza("Super Cheese", 1400);        Pizza pizza2 = new Pizza("All Shrooms", 900);        // Sample credit card information        CreditCardInformation ccInfo = new CreditCardInformation("1349947269650409", "06/28", "952");        // Sample order        order1 = new Order(                "19514FE0",                LocalDate.parse("2023-09-01"),                OrderStatus.UNDEFINED,                OrderValidationCode.UNDEFINED,                2400,                new Pizza[]{pizza1, pizza2},                ccInfo        );//      Sample Order 2 - All Right Pizzas Defined        Pizza pizza3 = new Pizza("Proper Pizza", 1400);        Pizza pizza4 = new Pizza("Vegan Delight", 900);        // Sample credit card information        CreditCardInformation ccInfo2 = new CreditCardInformation("1349947269650409", "06/28", "952");        // Sample order        order2 = new Order(                "19514FE0",                LocalDate.parse("2023-09-01"),                OrderStatus.UNDEFINED,                OrderValidationCode.UNDEFINED,                2400,                new Pizza[]{pizza3, pizza4},                ccInfo2        );//      Sample Order 3 - All Wrong Pizzas Defined        Pizza pizza5 = new Pizza("Pizza-Extra1", 1400);        Pizza pizza6 = new Pizza("Pizza-Extra2", 900);        Pizza pizza7 = new Pizza("Pizza-Extra3", 900);        // Sample credit card information        CreditCardInformation ccInfo3 = new CreditCardInformation("1349947269650409", "06/28", "952");        // Sample order        order3 = new Order(                "19514FE0",                LocalDate.parse("2023-09-01"),                OrderStatus.UNDEFINED,                OrderValidationCode.UNDEFINED,                2400,                new Pizza[]{pizza5, pizza6},                ccInfo3        );//      Sample Order 4 - Two Right Pizzas and Two wrong pizzas defines        Pizza pizza8 = new Pizza("Proper Pizza", 1400);        Pizza pizza9 = new Pizza("Vegan Delight", 900);        Pizza pizza10 = new Pizza("Pizza-Extra1", 1400);        Pizza pizza11 = new Pizza("Pizza-Extra2", 900);        // Sample credit card information        CreditCardInformation ccInfo4 = new CreditCardInformation("1349947269650409", "06/28", "952");        // Sample order        order4 = new Order(                "19514FE0",                LocalDate.parse("2023-09-01"),                OrderStatus.UNDEFINED,                OrderValidationCode.UNDEFINED,                2400,                new Pizza[]{pizza8, pizza9, pizza10, pizza11},                ccInfo4        );//      Sample Order 5: Pizzas from multiple restaurants        Pizza pizza12 = new Pizza("Proper Pizza", 1400);        Pizza pizza13 = new Pizza("Vegan Delight", 900);        Pizza pizza14 = new Pizza("Meat Lover=", 1400);        Pizza pizza15 = new Pizza("All Shrooms", 900);        // Sample credit card information        CreditCardInformation ccInfo5 = new CreditCardInformation("1349947269650409", "06/28", "952");        // Sample order        order5 = new Order(                "19514FE0",                LocalDate.parse("2023-09-01"),                OrderStatus.UNDEFINED,                OrderValidationCode.UNDEFINED,                4600,                new Pizza[]{pizza12, pizza13, pizza14, pizza15},                ccInfo5        );//      Sample Order 6 - No Pizzas in Order (Empty array)        // Sample credit card information        CreditCardInformation ccInfo6 = new CreditCardInformation("1349947269650409", "06/28", "952");        // Sample order        order6 = new Order(                "19514FE0",                LocalDate.parse("2023-09-01"),                OrderStatus.UNDEFINED,                OrderValidationCode.UNDEFINED,                0,                new Pizza[]{},                ccInfo6        );    }    /****** Pizza From Multiple restaurants Checks ******/    @Test    public void noPizzaFromMultipleRestaurantsTest(){        Pizza[] pizzas = order1.getPizzasInOrder();        assertFalse(obj.pizzaMultipleRestaurantsCheck(pizzas));    }    @Test    public void allpizzaFromMultipleRestaurantsTest(){        Pizza[] pizzas = order5.getPizzasInOrder();        assertTrue(obj.pizzaMultipleRestaurantsCheck(pizzas));    }    /****** Pizza Defined Checks ******/    @Test    public void pizzaDefinedAllTest(){        Pizza[] pizzas = order1.getPizzasInOrder();        assertTrue(obj.pizzaDefineCheck(pizzas));    }    @Test    public void pizzaDefinedAllRightTest(){        Pizza[] pizzas = order2.getPizzasInOrder();        assertTrue(obj.pizzaDefineCheck(pizzas));    }    @Test    public void pizzaDefinedAllWrongTest(){        Pizza[] pizzas = order3.getPizzasInOrder();        assertFalse(obj.pizzaDefineCheck(pizzas));    }    @Test    public void pizzaDefinedTwoRightTwoWrongTest(){        Pizza[] pizzas = order4.getPizzasInOrder();        assertFalse(obj.pizzaDefineCheck(pizzas));    }    @Test    public void pizzaDefinedNoPizzaTest(){        Pizza[] pizzas = order6.getPizzasInOrder();        assertFalse(obj.pizzaDefineCheck(pizzas));    }    @Test    public void pizzaDefinedNullPizzaTest(){        order6.setPizzasInOrder(null);        Pizza[] pizzas = order6.getPizzasInOrder();        assertFalse(obj.pizzaDefineCheck(pizzas));    }    /****** Total Price Validation Check ******/    // TODO: Add more tests for Total Price Validation Check    @Test    public void totalPriceValidationTest(){        Pizza[] pizzas = order1.getPizzasInOrder();        assertTrue(obj.totalPriceCheck(pizzas, 2400));    }    /********* Pizza Count Checks *********/    @Test    public void pizzaCountNotExceedTest(){        Pizza[] pizzas = new Pizza[3];        pizzas[0] = new Pizza("margherita", 100);        pizzas[1] = new Pizza("margherita", 200);        pizzas[2] = new Pizza("margherita", 100);        assertTrue(obj.countCheck(pizzas));    }    @Test    public void pizzaCountExceedTest(){        Pizza[] pizzas = new Pizza[5];        pizzas[0] = new Pizza("margherita", 100);        pizzas[1] = new Pizza("margherita", 200);        pizzas[2] = new Pizza("margherita", 100);        pizzas[3] = new Pizza("margherita", 100);        pizzas[4] = new Pizza("margherita", 100);        assertFalse(obj.countCheck(pizzas));    }    @Test    public void pizzaCountZeroTest(){        Pizza[] pizzas = new Pizza[0];        assertFalse(obj.countCheck(pizzas));    }    @Test    public void pizzaCountEqualMaxCheck(){        Pizza[] pizzas = new Pizza[4];        pizzas[0] = new Pizza("margherita", 100);        pizzas[1] = new Pizza("margherita", 200);        pizzas[2] = new Pizza("margherita", 100);        pizzas[3] = new Pizza("margherita", 100);        assertTrue(obj.countCheck(pizzas));    }}