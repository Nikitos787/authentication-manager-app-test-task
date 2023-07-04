package project.authentication.manager.app.repository.specification.user;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import project.authentication.manager.app.model.User;
import project.authentication.manager.app.repository.specification.SpecificationProvider;

@Component
public class UserLastNameInSpecification implements SpecificationProvider<User> {
    private static final String FIELD_NAME = "lastName";
    private static final String FILTER = "lastNameIn";

    @Override
    public Specification<User> getSpecification(String[] lastNames) {
        return ((root, query, cb) -> {
            CriteriaBuilder.In<String> predicate = cb.in(root.get(FIELD_NAME));
            for (String value : lastNames) {
                predicate.value(value);
            }
            return cb.and(predicate, predicate);
        });
    }

    @Override
    public String getFilter() {
        return FILTER;
    }
}
