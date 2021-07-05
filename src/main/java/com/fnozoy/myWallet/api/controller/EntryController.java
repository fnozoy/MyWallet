package com.fnozoy.myWallet.api.controller;

import com.fnozoy.myWallet.api.dto.EntryDTO;
import com.fnozoy.myWallet.exceptions.BusinessRuleException;
import com.fnozoy.myWallet.model.entity.Entry;
import com.fnozoy.myWallet.model.entity.User;
import com.fnozoy.myWallet.model.enums.EntryCode;
import com.fnozoy.myWallet.model.enums.EntryStatusCode;
import com.fnozoy.myWallet.service.EntryService;
import com.fnozoy.myWallet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@RestController
@RequestMapping
public class EntryController {

    @Autowired
    private EntryService entryService;

    @Autowired
    private UserService userService;

    public EntryController(EntryService entryService, UserService userService) {
        this.entryService = entryService;
        this.userService = userService;
    }


    @PostMapping("/api/v1/entry/create")
    public ResponseEntity create (@RequestBody EntryDTO entryDTO){
        try {
            Entry entry = convertDTO(entryDTO);
            Entry entryCreated = entryService.create(entry);
            return new ResponseEntity(entryCreated, HttpStatus.OK);
        } catch (BusinessRuleException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/api/v1/entry/update/{id}")
    public ResponseEntity update (@PathVariable Long id, @RequestBody EntryDTO entryDTO){
        return entryService.findById(id).map(entity -> {
            try {
                Entry entry = convertDTO(entryDTO);
                entry.setId(entity.getId());
                entryService.update(entry);
                return ResponseEntity.ok(entry);
            } catch (BusinessRuleException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet( () ->
                new ResponseEntity("Entry not found.", HttpStatus.BAD_REQUEST));
    }

    @PutMapping("api/v1/entry/updatestatus/{id}")
    public ResponseEntity updateStatus(@PathVariable("id") Long id, @RequestBody EntryDTO entryDTO){
        try {
            Optional<Entry> entry = entryService.findById(id);
            if (!entry.isPresent()){
                return ResponseEntity.badRequest().body("Entry not found.");
            }
            entry.get().setEntryStatusCode(entryDTO.getEntryStatusCode());
            entryService.updateStatus(entry.get(), entryDTO.getEntryStatusCode());
            return ResponseEntity.ok("Status updated with success.");
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("api/v1/entry/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        return entryService.findById(id).map( entry -> {
            try {
                entryService.delete(id);
                return ResponseEntity.ok(HttpStatus.NO_CONTENT);
            } catch (BusinessRuleException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet( () ->
                new ResponseEntity("Entry not found.", HttpStatus.BAD_REQUEST));
    }

    @PostMapping("api/v1/entry/search")
    public ResponseEntity search(
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "status", required = false) EntryStatusCode entryStatusCode,
            @RequestParam(value = "code", required = false) EntryCode entryCode,
            @RequestParam("userId") Long id
            ){
        Entry entryFilter = new Entry();
        entryFilter.setDescription(description);
        entryFilter.setYear(year);
        entryFilter.setMonth(month);
        entryFilter.setEntryCode(entryCode);
        entryFilter.setEntryStatusCode(entryStatusCode);

        try {
            Optional<User> user = userService.findById(id);

            if (!user.isPresent()){
                return ResponseEntity.badRequest().body("Error! User not found.");
            } else {
                entryFilter.setUser(user.get());
            }
            List<Entry> entryList = entryService.search(entryFilter);
            return ResponseEntity.ok(entryList);
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Entry convertDTO(EntryDTO entryDTO){
        User user = new User();
        user.setId(entryDTO.getUserId());

        userService.findById(user.getId()).orElseThrow( () -> new BusinessRuleException("User does not exist."));

        Entry entry = Entry.builder()
                .description(entryDTO.getDescription())
                .month(entryDTO.getMonth())
                .year(entryDTO.getYear())
                .value(entryDTO.getValue())
                .user(user)
                .entryCode(entryDTO.getEntryCode())
                .entryStatusCode(entryDTO.getEntryStatusCode())
                .createDate(LocalDate.now())
                .build();


        return entry;
    }
}
