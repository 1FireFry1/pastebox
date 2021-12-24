package ru.sender.pastebox.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import ru.sender.pastebox.api.request.PasteboxRequest;
import ru.sender.pastebox.api.request.PublicStatus;
import ru.sender.pastebox.api.response.PasteBoxUrlResponse;
import ru.sender.pastebox.api.response.PasteboxResponse;
import ru.sender.pastebox.repository.PasteboxEntity;
import ru.sender.pastebox.repository.PasteboxRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "app")
public class PasteboxServiceImpl implements PasteboxService{

    private String host = "http://abc.ru";
    private int publicListSize = 10;

    private final PasteboxRepository repository;
    private AtomicInteger idGenerator = new AtomicInteger(0);

    @Override
    public PasteboxResponse getByHash(String hash) {
        PasteboxEntity pasteboxEntity = repository.getByHash(hash);
        return new PasteboxResponse(pasteboxEntity.getData(), pasteboxEntity.isPublic());
    }

    @Override
    public List<PasteboxResponse> getFirstPublicPasteBoxes() {
        List<PasteboxEntity> list = repository.getListOfPublicAndAlive(publicListSize);
        return list.stream().map(pasteboxEntity ->
                        new PasteboxResponse(pasteboxEntity.getData(), pasteboxEntity.isPublic()))
                .collect(Collectors.toList());
    }

    @Override
    public PasteBoxUrlResponse create(PasteboxRequest request) {
        int id = generateId();
        PasteboxEntity pasteboxEntity =new PasteboxEntity();
        pasteboxEntity.setData(request.getData());
        pasteboxEntity.setId(id);
        pasteboxEntity.setHash(Integer.toHexString(id));
        pasteboxEntity.setPublic(request.getPublicStatus() == PublicStatus.PUBLIC);
        pasteboxEntity.setLifeTime(LocalDateTime.now().plusSeconds(request.getExpirationTimeSeconds()));
        repository.add(pasteboxEntity);
        return new PasteBoxUrlResponse(host + "/" + pasteboxEntity.getHash());
    }

    private int generateId() {
        return idGenerator.getAndIncrement();
    }
}
