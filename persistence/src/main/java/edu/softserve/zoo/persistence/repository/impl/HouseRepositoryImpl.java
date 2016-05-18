package edu.softserve.zoo.persistence.repository.impl;

import edu.softserve.zoo.model.House;
import edu.softserve.zoo.persistence.repository.HouseRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ilya Doroshenko
 */
@Repository
public class HouseRepositoryImpl extends AbstractRepository<House> implements HouseRepository {
}
