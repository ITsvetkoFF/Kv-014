package test;

import edu.softserve.zoo.model.Task;
import edu.softserve.zoo.model.TaskStatistics;
import edu.softserve.zoo.persistence.repository.AnimalRepository;
import edu.softserve.zoo.persistence.repository.TaskRepository;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author Taras Zubrei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/persistence-test-ctx.xml")
public class TestClass {
    @Autowired
    AnimalRepository repo;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    SessionFactory sessionFactory;

    @Test
    @Transactional
    public void test() {
        System.out.println(repo.count());
        List map = sessionFactory.getCurrentSession().createCriteria(Task.class, "task")
                .add(Restrictions.eq("assignee.id", 2L))
                .createAlias("task", "taskType")
//                .setProjection(Projections.projectionList()
//                        .add(Projections.groupProperty("taskType"))
//                        .add(Projections.count("taskType")))
                .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP)
                .list();
        System.out.println(map);
    }
}
