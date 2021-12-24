package ru.sender.pastebox.service;

import ru.sender.pastebox.api.request.PasteboxRequest;
import ru.sender.pastebox.api.response.PasteBoxUrlResponse;
import ru.sender.pastebox.api.response.PasteboxResponse;

import java.util.List;

public interface PasteboxService {
    PasteboxResponse getByHash(String hash);
    List<PasteboxResponse> getFirstPublicPasteBoxes();
    PasteBoxUrlResponse create(PasteboxRequest request);
}
