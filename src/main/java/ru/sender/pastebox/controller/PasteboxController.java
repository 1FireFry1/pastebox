package ru.sender.pastebox.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.sender.pastebox.api.request.PasteboxRequest;
import ru.sender.pastebox.api.response.PasteBoxUrlResponse;
import ru.sender.pastebox.api.response.PasteboxResponse;
import ru.sender.pastebox.service.PasteboxService;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequiredArgsConstructor
public class PasteboxController {
    private final PasteboxService pasteboxService;

    @GetMapping("/")
    public Collection<PasteboxResponse> getPublicPasteList(){
        return pasteboxService.getFirstPublicPasteBoxes();
    }
    
    @GetMapping("/{hash}")
    public PasteboxResponse getByHash(@PathVariable String hash){
        return pasteboxService.getByHash(hash);
    }

    @PostMapping("/")
    public PasteBoxUrlResponse add(@RequestBody PasteboxRequest request){
        return pasteboxService.create(request);
    }
}
