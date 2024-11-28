package com.example.Jobportal.Services;

import com.example.Jobportal.DTO.StateDTO;
import com.example.Jobportal.Model.Country;
import com.example.Jobportal.Model.State;
import com.example.Jobportal.Repository.CountryRepo;
import com.example.Jobportal.Repository.StateRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StateService {
    private final StateRepo stateRepo;
    private final CountryRepo countryRepo;

    public StateService(StateRepo stateRepo, CountryRepo countryRepo) {
        this.stateRepo = stateRepo;
        this.countryRepo = countryRepo;
    }

    public StateDTO addState(StateDTO stateDto) {
        Country country = countryRepo.findByName(stateDto.getCountry());
        State state = new State();
        state.setName(stateDto.getName());
        state.setCountry(country);
        stateRepo.save(state);
        stateDto.setId(state.getId());
        return stateDto;
    }

    public List<StateDTO> getAllState() {
        List<State> states = stateRepo.findAll();

        return states.stream().map(state -> {
            StateDTO dto = new StateDTO();
            dto.setId(state.getId());
            dto.setName(state.getName());
            dto.setCountry(state.getCountry() != null ? state.getCountry().getName() : "Unknown Country");
            return dto;
        }).collect(Collectors.toList());
    }

    public Page<StateDTO> getStates(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<State> states = stateRepo.findAll(pageable);

        List<StateDTO> stateDTOList = states.getContent().stream().map(state -> {
            StateDTO dto = new StateDTO();
            dto.setId(state.getId());
            dto.setName(state.getName());
            dto.setCountry(state.getCountry() != null ? state.getCountry().getName() : "Unknown Country");
            return dto;
        }).collect(Collectors.toList());

        return new PageImpl<>(stateDTOList, pageable, states.getTotalElements());
    }

    public StateDTO getStateById(long id) {
        State state = stateRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("State not found with ID: " + id));
        StateDTO dto1 = new StateDTO();
        dto1.setId(state.getId());
        dto1.setName(state.getName());
        dto1.setCountry(state.getCountry() != null ? state.getCountry().getName() : "Unknown Country");
        return dto1;
    }

    public StateDTO updateState(long id, StateDTO stateDto) {
        State existingState = stateRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("State not found with ID: " + id));

        Country country = countryRepo.findByName(stateDto.getCountry());
        if (country == null) {
            throw new RuntimeException("Country not found with name: " + stateDto.getCountry());
        }
        existingState.setName(stateDto.getName());
        existingState.setCountry(country);
        existingState = stateRepo.save(existingState);
        stateDto.setId(existingState.getId());
        return stateDto;
    }

    public boolean deleteState(long id) {
        if (stateRepo.existsById(id)) {
            stateRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
