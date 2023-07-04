package project.authentication.manager.app.repository.specification.user;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import project.authentication.manager.app.model.User;
import project.authentication.manager.app.repository.specification.SpecificationProvider;

@Component
public class UserIsDeletedInSpecification implements SpecificationProvider<User> {
    private static final String FIELD_NAME = "isDeleted";
    private static final String FILTER = "isDeletedIn";

    @Override
    public Specification<User> getSpecification(String[] params) {
        return ((root, query, cb) -> {
            CriteriaBuilder.In<Boolean> predicate = cb.in(root.get(FIELD_NAME));
            for (String value : params) {
                predicate.value(Boolean.valueOf(value));
            }
            return cb.and(predicate);
        });
    }

    @Override
    public String getFilter() {
        return FILTER;
    }
}
