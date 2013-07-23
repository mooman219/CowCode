package com.gmail.mooman219.handler.config.store;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Modifier;

import com.gmail.mooman219.frame.file.ConfigJson;
import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.google.gson.Gson;

public class ConfigGlobal extends ConfigJson {
    public static Bull bull = new Bull();
    public static class Bull {}
    public static Handler handler = new Handler();
    public static class Handler {
        public Database database = new Database();
        public static class Database {
            public String server_id = "Alpha";
            public String server_loc = "US";
            public int downloadTimeout = 5; // Seconds
            public String hostname = "localhost";
            public int portnmbr = 27017;
            public String username = "cow";
            public String password = "c4qNflnf6zQWp9h2";
        }
        public Task task = new Task();
        public static class Task {
            public int threadCount = 5;
        }
    }
    public static Module module = new Module();
    public static class Module {
        public Chat chat = new Chat();
        public static class Chat {
            public int radius = 2500; // Blocks^2 (50 blocks = 2500 blocks^2)
            public int globalDelay = 10000; // Milliseconds
        }
        public Damage damage = new Damage();
        public static class Damage {
            public int damageDelay = 100; // Milliseconds
        }
        public Login login = new Login();
        public static class Login {
            public int loginDelay = 10000; // Milliseconds
        }
        public World world = new World();
        public static class World {
            public boolean disableBlockBurn = true;
            public boolean disableBlockSpread = true;
            public boolean disableBlockFade = true;
            public boolean disableBlockForm = true;
            public boolean disableBlockGrow = true;
            public boolean disableBlockFromTo = true;
            public boolean disableLeafDecay = true;
            public boolean disableLightningStrike = true;
            public boolean disableStructureGrow = true;
            public boolean disableWorldSaving = false;
            public boolean disableBuilding = false;
        }
    }

    public ConfigGlobal(String cast, String directory) {
        super(cast, directory, "config", "yml");
    }

    @Override
    public void onSave(FileWriter writer) {
        getGson().toJson(this, writer);
    }
    @Override
    public void onLoad(FileReader reader) {
        getGson().fromJson(reader, ConfigGlobal.class);
    }

    @Override
    public Gson getGson() {
        return JsonHelper.getGsonBuilder()
        .excludeFieldsWithModifiers(Modifier.TRANSIENT)
        .setPrettyPrinting()
        .create();
    }
}