package com.example.Jobportal.Controller;

import com.example.Jobportal.Model.Country;
import com.example.Jobportal.Services.CountryService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/country")
@CrossOrigin(origins = "http://localhost:3000")
public class CountryController {
    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping
    public ResponseEntity<Country> addCountry(@RequestBody Country country){
        return ResponseEntity.ok(countryService.addCountry(country));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Country>> getAllCountry(){
        return ResponseEntity.ok(countryService.getAllCountry());
    }

    @GetMapping
    public ResponseEntity<Page<Country>> getCountries(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {

        Page<Country> countries = countryService.getCountries(pageNo, pageSize);
        return ResponseEntity.ok(countries);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable long id, @RequestBody Country country){
        Country country1 = countryService.updateCountry(id,country);
        if(country1!=null){
            return ResponseEntity.ok(country1);
        }
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountry(@PathVariable long id){
        return ResponseEntity.ok(countryService.getCountry(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCountry(@PathVariable long id) {
        return ResponseEntity.ok(countryService.deleteCountry(id));
    }

}
