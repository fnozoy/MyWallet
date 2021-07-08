package com.fnozoy.myWallet.api.controller;

import com.fnozoy.myWallet.api.dto.EntryDTO;
import com.fnozoy.myWallet.exceptions.BusinessRuleException;
import com.fnozoy.myWallet.model.enums.EntryCodeEnum;
import com.fnozoy.myWallet.model.enums.EntryStatusEnum;
import com.fnozoy.myWallet.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping
public class EntryController {

    @Autowired
    private final EntryService entryService;

    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }

    @PostMapping("/api/v1/entry/create")
    public ResponseEntity create (@RequestBody EntryDTO entryDTO){
        try {

            EntryDTO entryCreated = entryService.create(entryDTO);
            return new ResponseEntity(entryCreated, HttpStatus.OK);

        } catch (BusinessRuleException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/api/v1/entry/update")
    public ResponseEntity update (@RequestBody EntryDTO entryDTO){
        try {

            EntryDTO entryDTOUpdate = entryService.update(entryDTO);
            return new ResponseEntity(entryDTOUpdate, HttpStatus.OK);

        } catch (BusinessRuleException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("api/v1/entry/updatestatus")
    public ResponseEntity updateStatus(@RequestBody EntryDTO entryDTO){
        try {

            entryService.updateStatus(entryDTO);
            return ResponseEntity.ok("Status updated with success.");

        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("api/v1/entry/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        try {

            entryService.delete(id);
            return ResponseEntity.ok("Deleted with success.");

        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //TODO: change to receive a DTO
    @PostMapping("api/v1/entry/search")
    public ResponseEntity search(
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "status", required = false) EntryStatusEnum entryStatusEnum,
            @RequestParam(value = "code", required = false) EntryCodeEnum entryCodeEnum,
            @RequestParam("userId") Long id
            ){
        EntryDTO entryFilter = EntryDTO.builder()
                .description(description)
                .year(year)
                .month(month)
                .entryCode(entryCodeEnum)
                .entryStatus(entryStatusEnum)
                .userId(id)
                .build();
        try {

            List<EntryDTO> entryList = entryService.search(entryFilter);
            return ResponseEntity.ok(entryList);

        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/api/v1/entry/getbalance/{id}")
    public ResponseEntity getBalance (@PathVariable Long id){
       try {

            BigDecimal balance = entryService.getBalanceByUserId(id);
            return new ResponseEntity(balance, HttpStatus.OK);

        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
    }
}

}
