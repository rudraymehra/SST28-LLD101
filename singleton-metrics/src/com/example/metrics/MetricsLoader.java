package com.example.metrics;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Loads default metric keys from a properties file.
 *
 * Current Implementation Details:
 * - Uses 'new MetricsRegistry()' instead of the singleton.
 *
 * TODO (student):
 *  - Use MetricsRegistry.fetchInstance() and remove all direct instantiation.
 */
public class MetricsLoader {

    public MetricsRegistry loadFromFile(String path) throws IOException {
        Properties propertiesBag = new Properties();
        try (FileInputStream fileInStream = new FileInputStream(path))
    {
            propertiesBag.load(fileInStream);
        }

        MetricsRegistry metricsStore = MetricsRegistry.fetchInstance();

        for (String key : propertiesBag.stringPropertyNames())
    {
            String raw = propertiesBag.getProperty(key, "0").trim();
            long v;
            try {
                v = Long.parseLong(raw);
            } catch (NumberFormatException e)
    {
                v = 0L;
            }
            metricsStore.setCount(key, v);
        }
        return metricsStore;
    }
}
