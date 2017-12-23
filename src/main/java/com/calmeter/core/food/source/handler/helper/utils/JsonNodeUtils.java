package com.calmeter.core.food.source.handler.helper.utils;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class JsonNodeUtils {

    public static Optional<Double> getNodeDouble(JsonNode nutrientsNode, int index, String key) {
        try {
            return Optional.of(nutrientsNode.get(index).get(key).asDouble());
        } catch (Exception e) {
            logger.debug("Unable to get node. Index; {}, Key; {}", index, key);
            return Optional.empty();
        }
    }

    public static Optional<Double> getNodeDouble(JsonNode nutrientsNode, String key) {
        try {
            logger.info("{}: {}", key, nutrientsNode.get(key).asText());
            return Optional.of(nutrientsNode.get(key).asDouble());
        } catch (Exception e) {
            logger.debug("Unable to get node. Key; {}", key);
            return Optional.empty();
        }
    }

    private static Logger logger = LoggerFactory.getLogger(JsonNodeUtils.class);


}
