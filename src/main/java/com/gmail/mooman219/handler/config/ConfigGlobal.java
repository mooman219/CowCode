package com.gmail.mooman219.handler.config;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Modifier;

import com.gmail.mooman219.frame.MathHelper;
import com.gmail.mooman219.frame.file.ConfigJson;
import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.google.gson.Gson;

public class ConfigGlobal extends ConfigJson {
    public static Bull bull = new Bull();
    public static class Bull {
        public Chunk chunk = new Chunk();
        public static class Player {
            public int nameUpdateRadius = 10; // Chunks
        }
        public Player player = new Player();
        public static class Chunk {
            public int chunkTickPeriod = 30; // Ticks
            public int chunkUnloadDelay = 2; // Minutes
        }
    }
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
            public int radius = 50; // Blocks
            public int globalDelay = 10; // Seconds
        }
        public Login login = new Login();
        public static class Login {
            public int loginDelay = 10; // Seconds
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

    private transient boolean isAdjusted = false;

    public ConfigGlobal(String directory) {
        super(directory, "config", "yml");
    }

    private void adjustedValues() {
        if(!isAdjusted) {
            ConfigGlobal.bull.player.nameUpdateRadius = MathHelper.toInt(Math.pow(ConfigGlobal.bull.player.nameUpdateRadius * 16, 2));
            ConfigGlobal.bull.chunk.chunkUnloadDelay = ConfigGlobal.bull.chunk.chunkUnloadDelay * 60 * 1000;
            ConfigGlobal.module.chat.radius = MathHelper.toInt(Math.pow(ConfigGlobal.module.chat.radius, 2));
            ConfigGlobal.module.chat.globalDelay = ConfigGlobal.module.chat.globalDelay * 1000;
            ConfigGlobal.module.login.loginDelay = ConfigGlobal.module.login.loginDelay * 1000;
            isAdjusted = true;
        }
    }

    private void normalValues() {
        if(isAdjusted) {
            ConfigGlobal.bull.player.nameUpdateRadius = MathHelper.toInt(Math.sqrt(ConfigGlobal.bull.player.nameUpdateRadius) / 16);
            ConfigGlobal.bull.chunk.chunkUnloadDelay = ConfigGlobal.bull.chunk.chunkUnloadDelay / 1000 / 60;
            ConfigGlobal.module.chat.radius = MathHelper.toInt(Math.sqrt(ConfigGlobal.module.chat.radius));
            ConfigGlobal.module.chat.globalDelay = ConfigGlobal.module.chat.globalDelay / 1000;
            ConfigGlobal.module.login.loginDelay = ConfigGlobal.module.login.loginDelay / 1000;
            isAdjusted = false;
        }
    }

    @Override
    public void onSave(FileWriter writer) {
        normalValues();
        getGson().toJson(this, writer);
        adjustedValues();
    }

    @Override
    public void onLoad(FileReader reader) {
        normalValues();
        getGson().fromJson(reader, ConfigGlobal.class);
        adjustedValues();
    }

    @Override
    public Gson getGson() {
        return JsonHelper.getGsonBuilder()
        .excludeFieldsWithModifiers(Modifier.TRANSIENT)
        .setPrettyPrinting()
        .create();
    }
}