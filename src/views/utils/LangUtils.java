package src.views.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LangUtils {

  private static final int LANG_EN = 0;
  private static final int LANG_FR = 1;
  public static int lang = 0;

  /**
   * Retrieves the text associated with the given key. If the key is not found in
   * the language JSON file, the default value is the key itself.
   *
   * @param key the key used to retrieve the text
   * @return the text associated with the key, or the key itself if not found
   */
  public static String getText(String key) {
    String result = key; // Default value is the key itself

    switch (lang) {
    case LANG_EN:
      Map<String, String> enJson = loadJson("en.json");
      if (enJson.containsKey(key)) {
        result = enJson.get(key);
      }
      break;
    case LANG_FR:
      Map<String, String> frJson = loadJson("fr.json");
      if (frJson.containsKey(key)) {
        result = frJson.get(key);
      }
      break;
    }

    return result;
  }

  /**
   * Loads a JSON file and returns its contents as a map of key-value pairs.
   *
   * @param fileName the name of the JSON file to load
   * @return a map containing the key-value pairs from the JSON file
   */
  private static Map<String, String> loadJson(String fileName) {
    Map<String, String> jsonMap = new HashMap<>();
    String filePath = "lang/" + fileName;

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      StringBuilder sb = new StringBuilder();
      while ((line = br.readLine()) != null) {
        sb.append(line);
      }
      String jsonString = sb.toString();

      // Assuming the JSON file contains only key-value pairs, like {"key1": "value1",
      // "key2": "value2"}
      int startIndex = 0;
      while (startIndex < jsonString.length()) {
        int keyStart = jsonString.indexOf('"', startIndex);
        int keyEnd = jsonString.indexOf('"', keyStart + 1);
        int valueStart = jsonString.indexOf('"', keyEnd + 1);
        int valueEnd = jsonString.indexOf('"', valueStart + 1);

        // Check if all indices are valid
        if (keyStart < 0 || keyEnd < 0 || valueStart < 0 || valueEnd < 0) {
          break;
        }

        String key = jsonString.substring(keyStart + 1, keyEnd);
        String value = jsonString.substring(valueStart + 1, valueEnd);

        // If the value is an empty string, use the key itself
        if (value.isEmpty()) {
          value = key;
        }

        jsonMap.put(key, value);

        startIndex = valueEnd + 1;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return jsonMap;
  }

}
