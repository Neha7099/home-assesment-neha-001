package question1;
import java.util.*;

public class JSONParser {

    public static Map<String, Object> parse(String json) {
        json = json.replaceAll("\\s+", "");
        if (json.charAt(0) == '{' && json.charAt(json.length() - 1) == '}') {
            return parseObject(json.substring(1, json.length() - 1));
        }
        return null;
    }

    private static Map<String, Object> parseObject(String json) {
        Map<String, Object> result = new HashMap<>();
        String[] keyValuePairs = json.split(",");

        for (String pair : keyValuePairs) {
            String[] keyValue = pair.split(":");
            if (keyValue.length == 2) {
                String key = removeQuotes(keyValue[0]);
                String value = keyValue[1];

                if (value.charAt(0) == '{') {
                    result.put(key, parseObject(value.substring(1, value.length() - 1)));
                } else {
                    result.put(key, parseValue(value));
                }
            }
        }

        return result;
    }

    private static Object parseValue(String value) {
        if (value.equals("true") || value.equals("false")) {
            return Boolean.parseBoolean(value);
        } else if (value.matches("\\d+")) {
            return Integer.parseInt(value);
        } else {
            return removeQuotes(value);
        }
    }

    private static String removeQuotes(String str) {
        return str.replaceAll("\"", "");
    }

    public static void main(String[] args) {
        String input = "{ \"debug\" : \"on\", \"window\" : { \"title\" : \"sample\", \"size\": 500 } }";
        Map<String, Object> output = JSONParser.parse(input);

        System.out.println(output.get("debug").equals("on"));
    }
}


