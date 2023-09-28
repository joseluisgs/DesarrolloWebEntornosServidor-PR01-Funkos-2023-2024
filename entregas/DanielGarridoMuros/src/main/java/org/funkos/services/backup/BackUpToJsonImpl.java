package org.funkos.services.backup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.funkos.models.Funko;
import org.funkos.services.funkos.FunkoServiceImpl;
import org.funkos.utils.LocalDateAdapter;
import org.funkos.utils.LocalDateTimeAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BackUpToJsonImpl implements Backup<Funko>{
    private static BackUpToJsonImpl instance;
    private final String APP_PATH = System.getProperty("user.dir");
    private final String DATA_DIR = APP_PATH + File.separator + "data";
    private final String BACKUP_FILE = DATA_DIR + File.separator + "funkos.json";

    private static Logger logger = LoggerFactory.getLogger(BackUpToJsonImpl.class);


    private BackUpToJsonImpl() {
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
    }


    public static BackUpToJsonImpl getInstance() {
        if (instance == null) {
            instance = new BackUpToJsonImpl();
        }
        return instance;
    }


    @Override
    public void backup(List<Funko> funkos) throws IOException {
        logger.debug("Creando backup de funkos");
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(funkos);
        Files.writeString(new File(BACKUP_FILE).toPath(), json);
    }


    @Override
    public List<Funko> restore() throws IOException {
        logger.debug("Borrando backup de funkos");
        Gson gson = new GsonBuilder().create();
        String json = "";
        json = Files.readString(new File(BACKUP_FILE).toPath());
        return gson.fromJson(json, new TypeToken<List<Funko>>() {
        }.getType());
    }


}
