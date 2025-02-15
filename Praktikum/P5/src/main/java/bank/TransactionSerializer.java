package bank;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class TransactionSerializer implements JsonSerializer<Transaction>, JsonDeserializer<Transaction> {

    @Override
    public JsonElement serialize(Transaction transaction, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject wrapper = new JsonObject();
        JsonObject instance = new JsonObject();
        
        // Add basic transaction properties to instance
        instance.addProperty("date", transaction.getDate());
        instance.addProperty("description", transaction.getDescription());
        instance.addProperty("amount", transaction.getAmount());
        
        // Determine the correct CLASSNAME based on transaction type
        String className;
        if (transaction instanceof Payment) {
            className = "Payment";
            Payment payment = (Payment) transaction;
            instance.addProperty("incomingInterest", payment.getIncomingInterest());
            instance.addProperty("outgoingInterest", payment.getOutgoingInterest());
        } else if (transaction instanceof Transfer) {
            Transfer transfer = (Transfer) transaction;
            instance.addProperty("sender", transfer.getSender());
            instance.addProperty("recipient", transfer.getRecipient());
            
            // Determine if it's an incoming or outgoing transfer
            className = transaction instanceof IncomingTransfer ? "IncomingTransfer" : "OutgoingTransfer";
        } else {
            throw new JsonParseException("Unknown transaction type");
        }
        
        wrapper.addProperty("CLASSNAME", className);
        wrapper.add("INSTANCE", instance);
        return wrapper;
    }

    /**
     * Gson invokes this call-back method during deserialization when it encounters a field of the
     * specified type.
     *
     * <p>In the implementation of this call-back method, you should consider invoking {@link
     * JsonDeserializationContext#deserialize(JsonElement, Type)} method to create objects for any
     * non-trivial field of the returned object. However, you should never invoke it on the same type
     * passing {@code json} since that will cause an infinite loop (Gson will call your call-back
     * method again).
     *
     * @param jsonElement   The Json data being deserialized
     * @param typeOfT The type of the Object to deserialize to
     * @return a deserialized object of the specified type typeOfT which is a subclass of {@code T}
     * @throws JsonParseException if json is not in the expected format of {@code typeOfT}
     */
    @Override
    public Transaction deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) 
            throws JsonParseException {
        JsonObject wrapper = jsonElement.getAsJsonObject();
        
        if (!wrapper.has("CLASSNAME") || !wrapper.has("INSTANCE")) {
            throw new JsonParseException("Invalid transaction format: missing CLASSNAME or INSTANCE");
        }
        
        String type = wrapper.get("CLASSNAME").getAsString();
        JsonObject instance = wrapper.get("INSTANCE").getAsJsonObject();

        return switch (type) {
            case "Payment" -> createPayment(instance);
            case "Transfer" -> createTransfer(instance); // Handle generic Transfer case
            case "IncomingTransfer" -> createIncomingTransfer(instance);
            case "OutgoingTransfer" -> createOutgoingTransfer(instance);
            default -> throw new JsonParseException("Unknown transaction type: " + type);
        };
    }

    private Payment createPayment(JsonObject jsonObject) {
        try {
            return new Payment(
                    getStringOrDefault(jsonObject, "date", ""),
                    getStringOrDefault(jsonObject, "description", ""),
                    getDoubleOrDefault(jsonObject, "amount", 0.0),
                    getDoubleOrDefault(jsonObject, "incomingInterest", 0.0),
                    getDoubleOrDefault(jsonObject, "outgoingInterest", 0.0)
            );
        } catch (Exception e) {
            throw new JsonParseException("Error creating Payment object: " + e.getMessage());
        }
    }

    private Transfer createTransfer(JsonObject jsonObject) {
        // Create IncomingTransfer by default for backward compatibility
        return new IncomingTransfer(
                getStringOrDefault(jsonObject, "date", ""),
                getStringOrDefault(jsonObject, "description", ""),
                getDoubleOrDefault(jsonObject, "amount", 0.0),
                getStringOrDefault(jsonObject, "sender", "Unknown"),
                getStringOrDefault(jsonObject, "recipient", "Unknown")
        );
    }

    private String getStringOrDefault(JsonObject obj, String key, String defaultValue) {
        return obj.has(key) && !obj.get(key).isJsonNull() ? obj.get(key).getAsString() : defaultValue;
    }

    private double getDoubleOrDefault(JsonObject obj, String key, double defaultValue) {
        return obj.has(key) && !obj.get(key).isJsonNull() ? obj.get(key).getAsDouble() : defaultValue;
    }

    private IncomingTransfer createIncomingTransfer(JsonObject jsonObject) {
        return new IncomingTransfer(
                getStringOrDefault(jsonObject, "date", ""),
                getStringOrDefault(jsonObject, "description", ""),
                getDoubleOrDefault(jsonObject, "amount", 0.0),
                getStringOrDefault(jsonObject, "sender", "Unknown"),
                getStringOrDefault(jsonObject, "recipient", "Unknown")
        );
    }

    private OutgoingTransfer createOutgoingTransfer(JsonObject jsonObject) {
        return new OutgoingTransfer(
                getStringOrDefault(jsonObject, "date", ""),
                getStringOrDefault(jsonObject, "description", ""),
                getDoubleOrDefault(jsonObject, "amount", 0.0),
                getStringOrDefault(jsonObject, "sender", "Unknown"),
                getStringOrDefault(jsonObject, "recipient", "Unknown")
        );
    }
}