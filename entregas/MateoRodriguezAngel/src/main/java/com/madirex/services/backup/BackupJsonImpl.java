package com.madirex.services.backup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.madirex.models.Funko;
import com.madirex.utils.LocalDateAdapter;
import com.madirex.utils.LocalDateTimeAdapter;
import com.madirex.utils.UuidAdapter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class BackupJsonImpl implements BackupJson {
    private static BackupJsonImpl instance;
    private final String APP_PATH = System.getProperty("user.dir");
    private final String DATA_DIR = APP_PATH + File.separator + "data";
    private final String BACKUP_FILE = DATA_DIR + File.separator + "funkos.json";

    private BackupJsonImpl() {
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
    }

    public static BackupJsonImpl getInstance() {
        if (instance == null) {
            instance = new BackupJsonImpl();
        }
        return instance;
    }


    @Override
    public void backup(List<Funko> funkos) throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(funkos);
        Files.writeString(new File(BACKUP_FILE).toPath(), json);
    }


    @Override
    public List<Funko> restore() throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(UUID.class, new UuidAdapter())
                .create();
        String json = "";
        json = Files.readString(new File(BACKUP_FILE).toPath());
        return gson.fromJson(json, new TypeToken<List<Funko>>() {
        }.getType());
    }
}