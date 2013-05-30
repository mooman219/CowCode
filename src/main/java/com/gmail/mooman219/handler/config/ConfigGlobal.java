package com.gmail.mooman219.handler.config;

import com.gmail.mooman219.frame.file.ConfigBase;

public class ConfigGlobal extends ConfigBase {
    // [+] Handler
    // [ ]---[+] Task
    public static int threadCount = 3;
    // [ ]---[+] Database
    public static String server_id = "Alpha";
    public static String server_loc = "US";
    public static String hostname = "localhost";
    public static int portnmbr = 27017;
    public static String username = "cow";
    public static String password = "c4qNflnf6zQWp9h2";
    // [+] Module
    // [ ]---[+] Service
    public static int nameUpdateRadius = 57600;
    // [ ]---[+] Chat
    public static int chatRadius = 50;
    public static int chatGlobalDelay = 15000;
    // [ ]---[+] Login
    public static int loginDelay = 20000;
    // [ ]---[+] World
    public static boolean disableBlockBurn = false;
    public static boolean disableBlockSpread = false;
    public static boolean disableBlockFade = false;
    public static boolean disableBlockForm = false;
    public static boolean disableBlockGrow = false;
    public static boolean disableBlockFromTo = false;
    public static boolean disableLeafDecay = false;
    public static boolean disableLightningStrike = false;

    public ConfigGlobal() {
        super(CHConfig.directory, "config.yml");
        super.init();
    }

    @Override
    public void onLoad() {
        // [+] Handler
        // [ ]---[+] Task
        threadCount = loadVar("Task.Thread_Count", threadCount);
        // [ ]---[+] Database
        server_id = loadVar("Server.ID", server_id);
        server_loc = loadVar("Server.Location", server_loc);
        hostname = loadVar("Mongo.Host", hostname);
        portnmbr = loadVar("Mongo.Port", portnmbr);
        username = loadVar("Mongo.User", username);
        password = loadVar("Mongo.Pass", password);
        // [+] Module
        // [ ]---[+] Service
        nameUpdateRadius = loadVar("Service.Player.Name_Update_Radius", nameUpdateRadius);
        // [ ]---[+] Chat
        chatRadius = loadVar("Chat.Max_Distance", chatRadius);
        chatGlobalDelay = loadVar("Chat.Global_Delay", chatGlobalDelay);
        // [ ]---[+] Login
        loginDelay = loadVar("Login.Delay", loginDelay);
        // [ ]---[+] World
        disableBlockBurn = loadVar("World.Disable.Block_Burn", disableBlockBurn);
        disableBlockSpread = loadVar("World.Disable.Block_Spread", disableBlockSpread);
        disableBlockFade = loadVar("World.Disable.Block_Fade", disableBlockFade);
        disableBlockForm = loadVar("World.Disable.Block_Form", disableBlockForm);
        disableBlockGrow = loadVar("World.Disable.Block_Grow", disableBlockGrow);
        disableBlockFromTo = loadVar("World.Disable.Block_From_To", disableBlockFromTo);
        disableLeafDecay = loadVar("World.Disable.Leaf_Decay", disableLeafDecay);
        disableLightningStrike = loadVar("World.Disable.Lightning_Strike", disableLightningStrike);
    }

    @Override
    public void onSave() {
        // [+] Handler
        // [ ]---[+] Task
        saveVar("Task.Thread_Count", threadCount);
        // [ ]---[+] Database
        saveVar("Server.ID", server_id);
        saveVar("Server.Location", server_loc);
        saveVar("Mongo.Host", hostname);
        saveVar("Mongo.Port", portnmbr);
        saveVar("Mongo.User", username);
        saveVar("Mongo.Pass", password);
        // [+] Module
        // [ ]---[+] Service
        saveVar("Service.Player.Name_Update_Radius", nameUpdateRadius);
        // [ ]---[+] Chat
        saveVar("Chat.Max_Distance", chatRadius);
        saveVar("Chat.Global_Delay", chatGlobalDelay);
        // [ ]---[+] Login
        saveVar("Login.Delay", loginDelay);
        // [ ]---[+] World
        saveVar("World.Disable.Block_Burn", disableBlockBurn);
        saveVar("World.Disable.Block_Spread", disableBlockSpread);
        saveVar("World.Disable.Block_Fade", disableBlockFade);
        saveVar("World.Disable.Block_Form", disableBlockForm);
        saveVar("World.Disable.Block_Grow", disableBlockGrow);
        saveVar("World.Disable.Block_From_To", disableBlockFromTo);
        saveVar("World.Disable.Leaf_Decay", disableLeafDecay);
        saveVar("World.Disable.Lightning_Strike", disableLightningStrike);
    }
}