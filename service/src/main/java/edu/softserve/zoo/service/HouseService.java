package edu.softserve.zoo.service;

import edu.softserve.zoo.model.House;

import java.util.List;
import java.util.Map;

/**
 * House specific methods and business logic for service layer
 *
 * @author Serhii Alekseichenko
 */
public interface HouseService extends Service<House> {
    List<House> find(Map<String, String> filter);
}
