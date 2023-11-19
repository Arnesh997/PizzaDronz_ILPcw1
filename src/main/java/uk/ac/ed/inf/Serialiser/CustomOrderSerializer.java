package uk.ac.ed.inf.Serialiser;import com.fasterxml.jackson.core.JsonGenerator;import com.fasterxml.jackson.databind.SerializerProvider;import com.fasterxml.jackson.databind.ser.std.StdSerializer;import uk.ac.ed.inf.ilp.data.Order;import java.io.IOException;/** * Custom serializer for the Order class. * This serializer extends the standard Jackson serializer to provide custom serialization logic * for Order objects. It is used to convert Order objects into a JSON representation, focusing on specific * fields of interest in the Order class. */public class CustomOrderSerializer extends StdSerializer<Order> {    /**     * Default constructor for CustomOrderSerializer.     * Calls the secondary constructor with null as the class type.     */    public CustomOrderSerializer() {        this(null);    }    /**     * Constructs a CustomOrderSerializer for the specified class.     * This constructor is mainly used for registration with Jackson serialization framework.     *     * @param t Class for which the serializer is to be used, typically Order.class.     */    public CustomOrderSerializer(Class<Order> t) {        super(t);    }    /**     * Custom serialization for Order objects.     * Serializes an Order object to JSON, including only specific fields such as order number,     * order status, validation code, and total cost.     *     * @param order The Order object to serialize.     * @param jgen The JsonGenerator used to write the JSON output.     * @param provider The SerializerProvider that can be used to get serializers for serializing     *                 objects contained within this order, if any.     * @throws IOException If an error occurs during JSON generation.     */    @Override    public void serialize(Order order, JsonGenerator jgen, SerializerProvider provider) throws IOException {        jgen.writeStartObject();        jgen.writeStringField("orderNo", order.getOrderNo());        jgen.writeStringField("orderStatus", order.getOrderStatus().toString());        jgen.writeStringField("orderValidationCode", order.getOrderValidationCode().toString());        jgen.writeNumberField("costInPence", order.getPriceTotalInPence());        jgen.writeEndObject();    }}