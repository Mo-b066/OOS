package bank;

import com.google.gson.*;

import java.lang.reflect.Type;

public class TransactionSerializer implements JsonSerializer<Transaction>, JsonDeserializer<Transaction> {

    private static final String CLASSNAME = "CLASSNAME";
    private static final String INSTANCE = "INSTANCE";
    // This method is used to serialize the Transaction object to JSON
    @Override
    public JsonElement serialize(Transaction transaction, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        // Add CLASSNAME to identify the concrete type
        jsonObject.addProperty(CLASSNAME, transaction.getClass().getSimpleName());

        // Create INSTANCE object to hold transaction data
        JsonObject instanceObject = new JsonObject();

        // Add common fields
        instanceObject.addProperty("date", transaction.getDate());
        instanceObject.addProperty("description", transaction.getDescription());
        instanceObject.addProperty("amount", transaction.getAmount());

        // Add specific fields based on type
        if (transaction instanceof Payment payment) {
            instanceObject.addProperty("incomingInterest", payment.getIncomingInterest());
            instanceObject.addProperty("outgoingInterest", payment.getOutgoingInterest());
        } else if (transaction instanceof Transfer transfer) {
            instanceObject.addProperty("sender", transfer.getSender());
            instanceObject.addProperty("recipient", transfer.getRecipient());
        }

        // Add INSTANCE to the main object
        jsonObject.add(INSTANCE, instanceObject);

        return jsonObject;
    }

    @Override
    public Transaction deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        try {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonElement classNameElement = jsonObject.get(CLASSNAME);
            if (classNameElement == null) {
                throw new JsonParseException("Missing CLASSNAME field");
            }
            String className = classNameElement.getAsString();
            JsonObject instanceObject = jsonObject.getAsJsonObject(INSTANCE);

            String date = getStringOrThrow(instanceObject, "date");
            String description = getStringOrThrow(instanceObject, "description");
            double amount = getDoubleOrThrow(instanceObject, "amount");

            switch (className) {
                case "Payment" -> {
                    double incomingInterest = getDoubleOrThrow(instanceObject, "incomingInterest");
                    double outgoingInterest = getDoubleOrThrow(instanceObject, "outgoingInterest");
                    return new Payment(date,  amount,description, incomingInterest, outgoingInterest);
                }
                case "IncomingTransfer" -> {
                    String sender = getStringOrThrow(instanceObject, "sender");
                    String recipient = getStringOrThrow(instanceObject, "recipient");
                    return new IncomingTransfer(date, description, sender, recipient,amount);
                }
                case "OutgoingTransfer" -> {
                    String sender = getStringOrThrow(instanceObject, "sender");
                    String recipient = getStringOrThrow(instanceObject, "recipient");
                    return new OutgoingTransfer(date, description, sender, recipient, amount);
                }
                default -> throw new JsonParseException("Unknown transaction type: " + className);
            }
        } catch (Exception e) {
            throw new JsonParseException("Error deserializing transaction: " + e.getMessage());
        }
    }

    private String getStringOrThrow(JsonObject jsonObject, String memberName) {
        JsonElement element = jsonObject.get(memberName);
        if (element == null || element.isJsonNull()) {
            throw new JsonParseException("Missing or null field: " + memberName);
        }
        return element.getAsString();
    }

    private double getDoubleOrThrow(JsonObject jsonObject, String memberName) {
        JsonElement element = jsonObject.get(memberName);
        if (element == null || element.isJsonNull()) {
            throw new JsonParseException("Missing or null field: " + memberName);
        }
        return element.getAsDouble();
    }
}