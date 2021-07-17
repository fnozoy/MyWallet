package com.fnozoy.myWallet.api.controller;

import com.fnozoy.myWallet.api.dto.EntryDTO;
import com.fnozoy.myWallet.exceptions.BusinessRuleException;
import com.fnozoy.myWallet.model.entity.Entry;
import com.fnozoy.myWallet.model.enums.EntryCodeEnum;
import com.fnozoy.myWallet.model.enums.EntryStatusEnum;
import com.fnozoy.myWallet.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @PostMapping("/api/entry/v1/create")
    public ResponseEntity create (@RequestBody EntryDTO entryDTO){
        try {

            EntryDTO entryCreated = entryService.create(entryDTO);
            return new ResponseEntity(entryCreated, HttpStatus.OK);

        } catch (BusinessRuleException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/api/entry/v1/update")
    public ResponseEntity update (@RequestBody EntryDTO entryDTO){
        try {

            EntryDTO entryDTOUpdate = entryService.update(entryDTO);
            return new ResponseEntity(entryDTOUpdate, HttpStatus.OK);

        } catch (BusinessRuleException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/api/entry/v1/updatestatus")
    public ResponseEntity updateStatus(@RequestBody EntryDTO entryDTO){
        try {

            entryService.updateStatus(entryDTO);
            return ResponseEntity.ok("Status updated with success.");

        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/api/entry/v1/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        try {

            entryService.delete(id);
            return ResponseEntity.ok("Deleted with success.");

        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/api/entry/v1/search")
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

    @GetMapping("/api/entry/v1/getbalance/{id}")
    public ResponseEntity getBalance (@PathVariable Long id) {
        try {

            BigDecimal balance = entryService.getBalanceByUserId(id);
            return new ResponseEntity(balance, HttpStatus.OK);

        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/api/entry/v1/getEntryById/{id}")
    public ResponseEntity getEntryByid (@PathVariable Long id){
        try {

            EntryDTO entryDTO = entryService.getEntryById(id);
            return new ResponseEntity(entryDTO, HttpStatus.OK);

        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @PostMapping("/api/entry/v2/create")
    public ResponseEntity createAuth (@RequestBody EntryDTO entryDTO){
        try {

            EntryDTO entryCreated = entryService.create(entryDTO);
            return new ResponseEntity(entryCreated, HttpStatus.OK);

        } catch (BusinessRuleException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/api/entry/v2/update")
    public ResponseEntity updateAuth (@RequestBody EntryDTO entryDTO){
        try {

            EntryDTO entryDTOUpdate = entryService.update(entryDTO);
            return new ResponseEntity(entryDTOUpdate, HttpStatus.OK);

        } catch (BusinessRuleException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/api/entry/v2/updatestatus")
    public ResponseEntity updateStatusAuth(@RequestBody EntryDTO entryDTO){
        try {

            entryService.updateStatus(entryDTO);
            return ResponseEntity.ok("Status updated with success.");

        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/api/entry/v2/delete/{id}")
    public ResponseEntity deleteAuth(@PathVariable Long id){
        try {

            entryService.delete(id);
            return ResponseEntity.ok("Deleted with success.");

        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/api/entry/v2/search")
    public ResponseEntity searchAuth(
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

    @GetMapping("/api/entry/v3/search")
    public Page<EntryDTO> searchAuthPage(
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "status", required = false) EntryStatusEnum entryStatusEnum,
            @RequestParam(value = "code", required = false) EntryCodeEnum entryCodeEnum,
            @RequestParam("userId") Long id,
            @PageableDefault() Pageable pageable
            ){
        EntryDTO entryFilter = EntryDTO.builder()
                .description(description)
                .year(year)
                .month(month)
                .entryCode(entryCodeEnum)
                .entryStatus(entryStatusEnum)
                .userId(id)
                .build();

        Page<Entry> entryList = entryService.searchPage(entryFilter, pageable);
        return EntryDTO.convert(entryList);

    }

    @GetMapping("/api/entry/v2/getbalance/{id}")
    public ResponseEntity getBalanceAuth (@PathVariable Long id) {
        try {

            BigDecimal balance = entryService.getBalanceByUserId(id);
            return new ResponseEntity(balance, HttpStatus.OK);

        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/api/entry/v2/getEntryById/{id}")
    public ResponseEntity getEntryByidAuth (@PathVariable Long id){
        try {

            EntryDTO entryDTO = entryService.getEntryById(id);
            return new ResponseEntity(entryDTO, HttpStatus.OK);

        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

