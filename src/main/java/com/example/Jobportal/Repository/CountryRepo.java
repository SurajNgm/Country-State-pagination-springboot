package com.example.Jobportal.Repository;

import com.example.Jobportal.Model.Country;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CountryRepo extends JpaRepository<Country, Long> {
    Country findByName(String name);
}
