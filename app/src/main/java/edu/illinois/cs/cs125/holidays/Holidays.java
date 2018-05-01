package edu.illinois.cs.cs125.holidays;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 *
 */
public final class Holidays {

    /** * The constructor. */
    public Holidays() { }

    public static String name(final String json) {
        if (json != null) {
            JsonParser parser = new JsonParser();
            JsonObject result = parser.parse(json).getAsJsonObject();
            JsonArray holidays = result.getAsJsonArray("holidays");
            if (holidays.size() != 0) {
                JsonObject name = holidays.get(0).getAsJsonObject();
                return name.get("name").getAsString();
            }
        }
        return "It is not a holiday";
    }
}
