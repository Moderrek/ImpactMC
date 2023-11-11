package com.impact.lib;

import com.impact.lib.api.config.ConfigField;
import com.impact.lib.api.config.ConfigFile;
import com.impact.lib.api.config.ConfigType;
import com.impact.lib.api.util.Tuple2;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class ImpactConfig {

  private static final Map<String, Map<Field, Object>> defined_configs = new ConcurrentHashMap<>();

  private ImpactConfig() {}

  public static void Load(@NotNull Object config) throws IllegalAccessException {
    Class<?> clazz = config.getClass();
    if (!clazz.isAnnotationPresent(ConfigFile.class)) throw new RuntimeException("Object is not Impact Configuration Class");
    Tuple2<String, ConfigType> config_file = getConfigFile(config).orElseThrow();
    System.out.println(config_file.getFirst());
    System.out.println(config_file.getSecond());
    System.out.println(config_file);
    Set<Field> config_fields = findFields(clazz, ConfigField.class);
    Map<Field, Object> defaults = new HashMap<>();
    for (Field field : config_fields) {
      defaults.put(field, field.get(config));
    }
    defined_configs.put(config_file.getFirst(), defaults);
    for (Map.Entry<Field, Object> a : defaults.entrySet()) {
      System.out.println(a.getKey().getName() + ": " + String.valueOf(a.getValue()));
    }
  }

  public static void Reload(@NotNull Object object) {
    Class<?> clazz = object.getClass();
    for (Field field : findFields(clazz, ConfigField.class)) {
      System.out.println(getFieldName(object, field).orElseThrow());
    }
  }

  private static void defineConfig() {

  }

  private static Optional<String> getFieldName(Object object, Field field) {
    if (object == null) return Optional.empty();
    if (field == null) return Optional.empty();
    if (!field.isAnnotationPresent(ConfigField.class)) return Optional.empty();
    ConfigField configField = field.getAnnotation(ConfigField.class);
    String name = configField.name();
    if (!name.isEmpty()) return Optional.of(name);
    return Optional.of(field.getName());
  }

  private static Optional<Tuple2<String, ConfigType>> getConfigFile(Object object) {
    if (object == null) return Optional.empty();
    Class<?> clazz = object.getClass();
    if (!clazz.isAnnotationPresent(ConfigFile.class)) return Optional.empty();
    ConfigFile configFile = clazz.getAnnotation(ConfigFile.class);
    String name = (configFile.fileName().isEmpty()) ? clazz.getSimpleName().toLowerCase() : configFile.fileName().toLowerCase().replace(' ', '_');
    return Optional.of(new Tuple2<>(name, configFile.type()));
  }

  private static @NotNull Set<Field> findFields(Class<?> clazz, Class<? extends Annotation> annotation) {
    Set<Field> set = new HashSet<>();
    Class<?> searching = clazz;
    while (searching != null) {
      for (Field field : searching.getDeclaredFields()) if (field.isAnnotationPresent(annotation)) set.add(field);
      searching = searching.getSuperclass();
    }
    return set;
  }

}
