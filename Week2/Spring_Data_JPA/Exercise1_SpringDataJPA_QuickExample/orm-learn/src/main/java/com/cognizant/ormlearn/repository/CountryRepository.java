package com.cognizant.ormlearn.repository;

import com.cognizant.ormlearn.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// JpaRepository gives us findAll(), findById(), save(), deleteById() for free.
@Repository
public interface CountryRepository extends JpaRepository<Country, String> {

    // Query method: Spring builds the query from the method name.
    List<Country> findByNameContaining(String partialName);

    // HQL (JPQL) query - uses the entity/field names, not table/column names.
    @Query("SELECT c FROM Country c WHERE c.name = :name")
    Country findByExactNameHQL(@Param("name") String name);

    // Native SQL query - uses the real table/column names.
    @Query(value = "SELECT * FROM country WHERE co_code = :code", nativeQuery = true)
    Country findByCodeNative(@Param("code") String code);
}
