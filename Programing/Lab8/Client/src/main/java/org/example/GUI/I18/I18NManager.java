package org.example.GUI.I18;

import org.example.GUI.RefreshableUI;

import java.util.*;

public class I18NManager {
    private static I18NManager instance;
    private Locale currentLocale;
    private ResourceBundle resourceBundle;

    private final Map<String, Locale> availableLocales = Map.of(
            "Русский", new Locale("ru", "RU"),
            "Norsk", new Locale("no", "NO"),
            "Shqip", new Locale("sq", "AL"),
            "English (NZ)", new Locale("en", "NZ")
    );

    public Map<String, Locale> getAvailableLocales() {
        return availableLocales;
    }

    private final List<RefreshableUI> registeredComponents = new ArrayList<>();

    private I18NManager() {
        setLocale(new Locale("ru", "RU"));
    }

    public static synchronized I18NManager getInstance() {
        if (instance == null) {
            instance = new I18NManager();
        }
        return instance;
    }

    public void setLocale(Locale locale) {
        this.currentLocale = locale;
        this.resourceBundle = ResourceBundle.getBundle("GUI/i18n/messages", locale);
        refreshAllComponents();
    }

    public String getMessage(String key) {
        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            return "!" + key + "!";
        }
    }

    public void registerComponent(RefreshableUI component) {
        registeredComponents.add(component);
    }

    private void refreshAllComponents() {
        registeredComponents.forEach(RefreshableUI::refreshUI);
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }
}