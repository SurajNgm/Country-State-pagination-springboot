package com.example.Jobportal.Repository;

import com.example.Jobportal.Model.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepo extends JpaRepository<State, Long> {
    State findByName(String name);
}
