package question1;

import java.util.*;

public class JSONParser {

    private int index;

    public Map<String, Object> parse(String json) {
        index = 0;
        return parseObject(json);
    }

    private Map<String, Object> parseObject(String json) {
        Map<String, Object> result = new HashMap<>();
        index++; // Skip opening curly brace '{'

        while (index < json.length()) {
            skipWhitespace(json);
            char currentChar = json.charAt(index);
            if (currentChar == '}') {
                index++; // Skip closing curly brace '}'
                return result;
            }

            String key = parseString(json);
            skipWhitespace(json);
            expect(json, ':');
            index++; // Skip colon ':'
            skipWhitespace(json);
            Object value = parseValue(json);
            result.put(key, value);

            skipWhitespace(json);
            if(json.length() > index) {
                if (json.charAt(index) == ',') {
                    index++; // Skip comma ','
                } else if (json.charAt(index) == '}') {
                    index++; // Skip closing curly brace '}'
                    return result;
                }
            }
        }

        return result;
    }

    private List<Object> parseArray(String json) {
        List<Object> result = new ArrayList<>();
        index++; // Skip opening square bracket '['

        while (index < json.length()) {
            skipWhitespace(json);
            char currentChar = json.charAt(index);
            if (currentChar == ']') {
                index++; // Skip closing square bracket ']'
                return result;
            }

            Object value = parseValue(json);
            result.add(value);

            skipWhitespace(json);
            if (json.charAt(index) == ',') {
                index++; // Skip comma ','
            } else if (json.charAt(index) == ']') {
                index++; // Skip closing square bracket ']'
                return result;
            }
        }

        return result;
    }

    // Rest of the methods are unchanged...
    private String parseString(String json) {
        StringBuilder stringBuilder = new StringBuilder();
        index++; // Skip opening double quote '"'

        while (index < json.length()) {
            char currentChar = json.charAt(index);
            if (currentChar == '"') {
                index++; // Skip closing double quote '"'
                return stringBuilder.toString();
            }
            stringBuilder.append(currentChar);
            index++;
        }

        return stringBuilder.toString();
    }

    private Object parseValue(String json) {
        if(json.length()>index) {
            char currentChar = json.charAt(index);
            if(currentChar == '['){
                return  parseArray(json);
            } else
            if (currentChar == '"') {
                return parseString(json);
            } else if (currentChar == '{') {
                return parseObject(json);
            } else if (Character.isDigit(currentChar) || currentChar == '-') {
                return parseNumber(json);
            } else if (currentChar == 't' || currentChar == 'f') {
                return parseBoolean(json);
            } else if (currentChar == 'n') {
                return parseNull(json);
            }
        }
        return null;
    }

    private Number parseNumber(String json) {
        int start = index;
        while (index < json.length() && "-.0123456789".contains(String.valueOf(json.charAt(index)))) {
            index++;
        }
        String numberStr = json.substring(start, index);
        if (numberStr.contains(".") || numberStr.contains("e") || numberStr.contains("E")) {
            return Double.parseDouble(numberStr);
        }
        return Integer.parseInt(numberStr);
    }

    private boolean parseBoolean(String json) {
        if (json.startsWith("true", index)) {
            index += 4;
            return true;
        } else if (json.startsWith("false", index)) {
            index += 5;
            return false;
        }
        return false;
    }

    private Object parseNull(String json) {
        if (json.startsWith("null", index)) {
            index += 4;
            return null;
        }
        return null;
    }

    private void skipWhitespace(String json) {
        while (index < json.length() && Character.isWhitespace(json.charAt(index))) {
            index++;
        }
    }

    private void expect(String json, char expected) {
        if (index < json.length() && json.charAt(index) != expected) {
            throw new RuntimeException("Expected '" + expected + "', found '" + json.charAt(index) + "'");
        }
    }

    public static void main(String[] args) {
        String input = "{ \"debug\" : \"on\", \"window\" : { \"title\" : \"sample\", \"size\": 500 }, \"numbers\": [1, 2, 3] }";
        JSONParser parser = new JSONParser();
        Map<String, Object> output = parser.parse(input);
        System.out.println("Map : "+output);
        System.out.println(output.get("debug").equals("on"));
        Map<String, Object> window = (Map<String, Object>) output.get("window");
        System.out.println(window.get("title").equals("sample"));
        List<Object> numbers = (List<Object>) output.get("numbers");
        System.out.println(numbers.get(0).equals(1));
    }
}
