package com.dallinjohnson.financeManagerAPI.repository.specification;

import com.dallinjohnson.financeManagerAPI.dto.TransactionFilterDTO;
import com.dallinjohnson.financeManagerAPI.model.Transaction;
import com.dallinjohnson.financeManagerAPI.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class TransactionSpecifications {

    // Always filter by the logged-in user
    public static Specification<Transaction> forUser(User user) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("user").get("id"), user.getId());
    }

    public static Specification<Transaction> withFilters(TransactionFilterDTO filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // filter by isCredit
            if (filter.getIsCredit() != null) {
                predicates.add(criteriaBuilder.equal(root.get("isCredit"), filter.getIsCredit()));
            }

            // filter by accountId
            if (filter.getAccountId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("account").get("id"), filter.getAccountId()));
            }

            // filter by categoryId
            if (filter.getCategoryId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), filter.getCategoryId()));
            }

            // filter by date between
            if (filter.getDateAfter() != null && filter.getDateBefore() != null) {
                predicates.add(criteriaBuilder.between(root.get("date"),
                        filter.getDateAfter(),
                        filter.getDateBefore()));
            } else {
                // filter by date on or after
                if (filter.getDateAfter() != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                            root.get("date"),
                            filter.getDateAfter()));
                }
                // filter by date on or before
                if (filter.getDateBefore() != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(
                            root.get("date"),
                            filter.getDateBefore()
                    ));
                }
            }

            // filter by transaction amount between
            if (filter.getAmountFrom() != null && filter.getAmountTo() != null) {
                predicates.add(criteriaBuilder.between(root.get("amount"),
                        filter.getAmountFrom(),
                        filter.getAmountTo()));
            } else {
                // filter by transaction amount greater than or equal to
                if (filter.getAmountFrom() != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                            root.get("amount"),
                            filter.getAmountFrom()));
                }
                // filter by transaction amount less than or equal to
                if (filter.getAmountTo() != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(
                            root.get("amount"),
                            filter.getAmountTo()
                    ));
                }
            }

            // filter by description contains substring
            if (filter.getDescriptionContains() != null) {
                String likeString = "%" + filter.getDescriptionContains().toLowerCase() + "%";
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("description")),
                                likeString
                        )
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
