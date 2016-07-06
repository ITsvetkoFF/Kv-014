package edu.softserve.zoo.persistence.provider.specification_processing.builder.impl;


import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.model.BaseEntity;
import edu.softserve.zoo.persistence.exception.CompositeSpecificationBuilderException;
import edu.softserve.zoo.persistence.provider.specification_processing.builder.CompositeSpecificationBuilder;
import edu.softserve.zoo.persistence.specification.Specification;
import edu.softserve.zoo.persistence.specification.hibernate.composite.CompositeSpecification;
import edu.softserve.zoo.persistence.specification.hibernate.composite.DetachedCriteriaSpecificationWrapper;
import edu.softserve.zoo.persistence.specification.hibernate.composite.fields.EntityField;
import edu.softserve.zoo.util.Validator;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Set;


/**
 * DetachedCriteria based implementation of the {@link CompositeSpecificationBuilder}
 *
 * @author Vadym Holub
 */
@Component("DetachedCriteriaProcessingStrategy")
public class DetachedCriteriaCompositeSpecificationBuilder<T extends BaseEntity> implements CompositeSpecificationBuilder<T> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Specification<T> build(CompositeSpecification<T> specification) {
        if (specification.getFields() == null) {
            return specification;
        }
        ProjectionList projectionList = process(specification.getFields(), specification.getEntityType());
        DetachedCriteria criteria = (DetachedCriteria) specification.query();
        criteria.setProjection(projectionList);
        criteria.setResultTransformer(new AliasToBeanResultTransformer(specification.getEntityType()));
        return new DetachedCriteriaSpecificationWrapper<>(criteria);
    }

    private ProjectionList process(Set<? extends EntityField<T>> fields, Class<T> entityType) {
        ProjectionList projectionList = Projections.projectionList();
        for (EntityField entityField : fields) {
            Field field = ReflectionUtils.findField(entityType, entityField.getName());
            Validator.notNull(field, ApplicationException.getBuilderFor(CompositeSpecificationBuilderException.class)
                    .withMessage(String.format("'%s' doesn't contain entityField '%s'", entityType.getName(), entityField.getName()))
                    .forReason(CompositeSpecificationBuilderException.Reason.NO_SUCH_FIELD)
                    .build());
            projectionList.add(Projections.property(field.getName()), field.getName());
        }
        return projectionList;
    }
}
