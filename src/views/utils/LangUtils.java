package src.views.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import src.views.listeners.LanguageChangeListener;

/**
 * The LangUtils class provides utility methods for language-related operations.
 */
public class LangUtils {

  private static final int LANG_EN = 0;
  private static final int LANG_FR = 1;
  public static int lang = LANG_EN;

  /**
   * The list of language change listeners.
   */
  private static final List<LanguageChangeListener> listeners = new ArrayList<>();

  /**
   * Adds a language change listener to the list of listeners.
   *
   * @param listener the language change listener to be added
   */
  public static void addLanguageChangeListener(LanguageChangeListener listener) {
    listeners.add(listener);
  }

  /**
   * Removes a language change listener from the list of registered listeners.
   *
   * @param listener the language change listener to be removed
   */
  public static void removeLanguageChangeListener(LanguageChangeListener listener) {
    listeners.remove(listener);
  }

  /**
   * Sets the language to the given language.
   *
   * @param lang the language to set
   */
  public static void setLang(int lang) {
    LangUtils.lang = lang;
    for (LanguageChangeListener listener : listeners) {
      listener.updateText();
    }
  }

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
   * Return the language
   */
  public static String getLang() {
    return lang == LANG_EN ? "English" : "French";
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