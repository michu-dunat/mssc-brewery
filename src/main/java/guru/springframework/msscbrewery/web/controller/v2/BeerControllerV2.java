package guru.springframework.msscbrewery.web.controller.v2;

import java.util.UUID;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.msscbrewery.services.v2.BeerServiceV2;
import guru.springframework.msscbrewery.web.model.v2.BeerDtoV2;

@RequestMapping("/api/v2/beer")
@RestController
@Slf4j
@RequiredArgsConstructor
public class BeerControllerV2
{
    private final BeerServiceV2 beerService;

    @GetMapping({ "/{beerId}" })
    public ResponseEntity<BeerDtoV2> getBeer(@PathVariable("beerId") UUID beerId)
    {

        return new ResponseEntity<>(beerService.getBeerById(beerId), HttpStatus.OK);
    }

    @PostMapping // POST - create new beer
    public ResponseEntity handlePost(@Valid @RequestBody BeerDtoV2 beerDto)
    {

        val savedDto = beerService.saveNewBeer(beerDto);

        val headers = new HttpHeaders();
        //todo add hostname to url
        headers.add("Location", "/api/v1/beer/" + savedDto.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping({ "/{beerId}" })
    public ResponseEntity handleUpdate(@PathVariable("beerId") UUID beerId, @Valid @RequestBody BeerDtoV2 beerDto)
    {

        beerService.updateBeer(beerId, beerDto);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping({ "/{beerId}" })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeer(@PathVariable("beerId") UUID beerId)
    {
        beerService.deleteById(beerId);
    }
}
