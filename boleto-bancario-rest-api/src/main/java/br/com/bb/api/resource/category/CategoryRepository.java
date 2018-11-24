package br.com.bb.api.resource.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT * FROM category c1\n" +
            "    GROUP BY c1.id, c1.name\n" +
            "    HAVING MAX(LENGTH(c1.name) - LENGTH(REPLACE(LOWER(c1.name), :pattern, ''))) > 0\n" +
            "    AND MAX(LENGTH(c1.name) - LENGTH(REPLACE(LOWER(c1.name), :pattern, ''))) " +
            "= (SELECT MAX(LENGTH(c2.name) - LENGTH(REPLACE(LOWER(c2.name), :pattern, ''))) FROM category c2)",
            nativeQuery = true)
    List<Category> findWithNameContainingPatternMostTimes(@Param("pattern") String pattern);

}
