package com.gmail.mooman219.handler.config;

import com.gmail.mooman219.frame.MathHelper;
import com.gmail.mooman219.frame.file.ConfigBase;

public class ConfigGlobal extends ConfigBase {
    // [+] Bull
    // [ ]---[+] Player
    public static int nameUpdateRadius = 10; // Chunks
    // [ ]---[+] Chunk
    public static int chunkTickPeriod = 30; // Ticks
    public static int chunkUnloadDelay = 5; // Minutes
    // [+] Handler
    // [ ]---[+] Task
    public static int threadCount = 5;
    // [ ]---[+] Database
    public static String server_id = "Alpha";
    public static String server_loc = "US";
    public static int downloadTimeout = 5; // Seconds
    public static String hostname = "localhost";
    public static int portnmbr = 27017;
    public static String username = "cow";
    public static String password = "c4qNflnf6zQWp9h2";
    // [+] Module
    // [ ]---[+] Chat
    public static int chatRadius = 50; // Blocks
    public static int chatGlobalDelay = 15; // Seconds
    // [ ]---[+] Login
    public static int loginDelay = 10; // Mills
    // [ ]---[+] World
    public static boolean disableBlockBurn = true;
    public static boolean disableBlockSpread = true;
    public static boolean disableBlockFade = true;
    public static boolean disableBlockForm = true;
    public static boolean disableBlockGrow = true;
    public static boolean disableBlockFromTo = true;
    public static boolean disableLeafDecay = true;
    public static boolean disableLightningStrike = true;
    public static boolean disableStructureGrow = true;
    public static boolean disableWorldSaving = false;

    public ConfigGlobal() {
        super(CHConfig.directory, "config.yml");
    }

    @Override
    public void onLoad() {
        // [+] Bull
        // [ ]---[+] Player
        nameUpdateRadius = MathHelper.toInt(Math.pow((loadVar("Bull.Player.Name_Update_Radius", nameUpdateRadius) * 16), 2));
        // [ ]---[+] Chunk
        chunkTickPeriod = loadVar("Bull.Chunk.Tick_Period", chunkTickPeriod);
        chunkUnloadDelay = loadVar("Bull.Chunk.Unload_Delay", chunkUnloadDelay) * 60 * 1000;
        // [+] Handler
        // [ ]---[+] Task
        threadCount = loadVar("Handler.Task.Thread_Count", threadCount);
        // [ ]---[+] Database
        server_id = loadVar("Handler.Database.Server.ID", server_id);
        server_loc = loadVar("Handler.Database.Server.Location", server_loc);
        downloadTimeout = loadVar("Handler.Database.Server.Player.Download_Timeout", downloadTimeout);
        hostname = loadVar("Handler.Database.Mongo.Host", hostname);
        portnmbr = loadVar("Handler.Database.Mongo.Port", portnmbr);
        username = loadVar("Handler.Database.Mongo.User", username);
        password = loadVar("Handler.Database.Mongo.Pass", password);
        // [+] Module
        // [ ]---[+] Chat
        chatRadius = MathHelper.toInt(Math.pow(loadVar("Module.Chat.Max_Distance", chatRadius), 2));
        chatGlobalDelay = loadVar("Module.Chat.Global_Delay", chatGlobalDelay) * 1000;
        // [ ]---[+] Login
        loginDelay = loadVar("Module.Login.Delay", loginDelay) * 1000;
        // [ ]---[+] World
        disableBlockBurn = loadVar("Module.World.Disable.Block_Burn", disableBlockBurn);
        disableBlockSpread = loadVar("Module.World.Disable.Block_Spread", disableBlockSpread);
        disableBlockFade = loadVar("Module.World.Disable.Block_Fade", disableBlockFade);
        disableBlockForm = loadVar("Module.World.Disable.Block_Form", disableBlockForm);
        disableBlockGrow = loadVar("Module.World.Disable.Block_Grow", disableBlockGrow);
        disableBlockFromTo = loadVar("Module.World.Disable.Block_From_To", disableBlockFromTo);
        disableLeafDecay = loadVar("Module.World.Disable.Leaf_Decay", disableLeafDecay);
        disableLightningStrike = loadVar("Module.World.Disable.Lightning_Strike", disableLightningStrike);
        disableStructureGrow = loadVar("Module.World.Disable.Structure_Grow", disableStructureGrow);
        disableWorldSaving = loadVar("Module.World.Disable.World_Saving", disableWorldSaving);
    }

    @Override
    public void onSave() {
        // [+] Bull
        // [ ]---[+] Player
        saveVar("Bull.Player.Name_Update_Radius", MathHelper.toInt(Math.sqrt(nameUpdateRadius) / 16));
        // [ ]---[+] Chunk
        saveVar("Bull.Chunk.Tick_Period", chunkTickPeriod);
        saveVar("Bull.Chunk.Unload_Delay", chunkUnloadDelay / 1000 / 60);
        // [+] Handler
        // [ ]---[+] Task
        saveVar("Handler.Task.Thread_Count", threadCount);
        // [ ]---[+] Database
        saveVar("Handler.Database.Server.ID", server_id);
        saveVar("Handler.Database.Server.Location", server_loc);
        saveVar("Handler.Database.Server.Player.Download_Timeout", downloadTimeout);
        saveVar("Handler.Database.Mongo.Host", hostname);
        saveVar("Handler.Database.Mongo.Port", portnmbr);
        saveVar("Handler.Database.Mongo.User", username);
        saveVar("Handler.Database.Mongo.Pass", password);
        // [+] Module
        // [ ]---[+] Chat
        saveVar("Module.Chat.Max_Distance", MathHelper.toInt(Math.sqrt(chatRadius)));
        saveVar("Module.Chat.Global_Delay", chatGlobalDelay / 1000);
        // [ ]---[+] Login
        saveVar("Module.Login.Delay", loginDelay / 1000);
        // [ ]---[+] World
        saveVar("Module.World.Disable.Block_Burn", disableBlockBurn);
        saveVar("Module.World.Disable.Block_Spread", disableBlockSpread);
        saveVar("Module.World.Disable.Block_Fade", disableBlockFade);
        saveVar("Module.World.Disable.Block_Form", disableBlockForm);
        saveVar("Module.World.Disable.Block_Grow", disableBlockGrow);
        saveVar("Module.World.Disable.Block_From_To", disableBlockFromTo);
        saveVar("Module.World.Disable.Leaf_Decay", disableLeafDecay);
        saveVar("Module.World.Disable.Lightning_Strike", disableLightningStrike);
        saveVar("Module.World.Disable.Structure_Grow", disableStructureGrow);
        saveVar("Module.World.Disable.World_Saving", disableWorldSaving);
    }
}