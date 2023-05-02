package com.atech.pma.controllers;

import com.atech.pma.entity.mysql.CardHolder;
import com.atech.pma.model.CardHolderDTO;
import com.atech.pma.service.CardHoldersService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author raed abu Sa'da
 * on 31/03/2023
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/card-holders")
public class CardHoldersController {

    private final CardHoldersService cardHoldersService;

    @GetMapping("/reload")
    public ResponseEntity<String> populateCardHoldersFromOnguardDb(){

        cardHoldersService.populateCardHoldersFromOnguardDB();

        return ResponseEntity.ok().body("success");
    }

    /**
     *
     * @return card-holders list
     */
    @GetMapping({"", "/"})
    public ResponseEntity<List<CardHolder>> getCardHoldersList(){

        return ResponseEntity.ok().body(cardHoldersService.getCardHoldersList());
    }


    @GetMapping("/expire")
    public ResponseEntity<List<CardHolderDTO>> getExpiredCardHolders(){

        return ResponseEntity.ok().body(cardHoldersService.getCardHoldersLicenseExpireInOneWeek());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardHolder> getCardHolderById(@PathVariable("id") Long id){

        return ResponseEntity.ok().body(cardHoldersService.findCardHolderById(id));
    }

    @PostMapping("/update")
    public ResponseEntity<CardHolder> updateCardHolder(@RequestBody CardHolder cardHolder){

        return ResponseEntity.status(201).body(cardHoldersService.updateCardHolder(cardHolder));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCardHolderById(@PathVariable Long id){

        cardHoldersService.deleteCardHolder(id);
    }
}
