package project.authentication.manager.app.repository.specification;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import project.authentication.manager.app.model.User;

@Component
public class UserSpecificationManager implements SpecificationManager<User> {
    private final Map<String, SpecificationProvider<User>> providerMap;

    @Autowired
    public UserSpecificationManager(List<SpecificationProvider<User>> providerList) {
        this.providerMap = providerList.stream()
                .collect(Collectors.toMap(SpecificationProvider::getFilter, Function.identity()));
    }

    @Override
    public Specification<User> get(String filterKey, String[] params) {
        if (!providerMap.containsKey(filterKey)) {
            throw new RuntimeException(String
                    .format("Key: %s is not supported for data filtering", filterKey));
        }
        return providerMap.get(filterKey).getSpecification(params);
    }
}
