package com.example.Jobportal.Services;

import com.example.Jobportal.Model.Country;
import com.example.Jobportal.Repository.CountryRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {
    private final CountryRepo countryRepo;

    public CountryService(CountryRepo countryRepo) {
        this.countryRepo = countryRepo;
    }

    public Country addCountry(Country country){
        return countryRepo.save(country);
    }

    public List<Country> getAllCountry(){
        return countryRepo.findAll();
    }

    public Page<Country> getCountries(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return countryRepo.findAll(pageable);
    }

    public Country getCountry(long id){
        return countryRepo.findById(id).get();
    }

    public Country updateCountry(long id, Country country){
        Country country1 = getCountry(id);
        if(country1!=null){
            country1.setName(country.getName());
            countryRepo.save(country1);
            return country1;
        }
        return null;
    }

    public boolean deleteCountry(long id) {
        if (countryRepo.existsById(id)) {
            countryRepo.deleteById(id);
            return true;
        }
        return false;
    }

}
