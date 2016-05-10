package edu.softserve.zoo.persistence.specification.generic;

import edu.softserve.zoo.exceptions.persistence.PersistenceException;
import edu.softserve.zoo.model.Animal;
import edu.softserve.zoo.persistence.repository.impl.AnimalRepository;
import edu.softserve.zoo.persistence.specification.stubs.generic.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * @author Bohdan Cherniakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:persistence-test-ctx.xml")
public class SupportedSpecificationTest {
    @Autowired
    AnimalRepository repository;

    @Test
    @Transactional
    public void shouldCollectAllTheAnimalsWithCorrectSpecification() {
        int expectedCount = 12;
        Collection<Animal> animals = repository.find(new StubGetAllAnimalsSingleCorrectInterface());
        int actualCount = animals.size();
        assertEquals("Should collect 12 animals", expectedCount, actualCount);
    }

    @Test
    @Transactional
    public void shouldCollectAllTheAnimalsWithMultiInterfaceSpecification() {
        int expectedCount = 12;
        Collection<Animal> animals = repository.find(new StubGetAllAnimalsMultiInterface());
        int actualCount = animals.size();
        assertEquals("Should collect 12 animals", expectedCount, actualCount);
    }

    @Test
    @Transactional
    public void shouldCollectAllTheAnimalsWithMultiInterfaceSpecificationSuccessor() {
        int expectedCount = 12;
        Collection<Animal> animals = repository.find(new StubGetAllAnimalsSuccessor());
        int actualCount = animals.size();
        assertEquals("Should collect 12 animals", expectedCount, actualCount);
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void shouldInvokePersistenceExceptionWithUnsupportedSpecification() {
        repository.find(new StubGetAllAnimalsUnsupportedSpecification());
    }

    @Test (expected = PersistenceException.class)
    @Transactional
    public void shouldInvokePersistenceExceptionWithInterferedSpecification() {
        repository.find(new StubGetAllAnimalsMultiInterfaceSpecificationInterference());
    }

    @Test(expected = NullPointerException.class)
    @Transactional
    public void shouldInvokeNullPointerException() {
        repository.find(null);
    }

}
