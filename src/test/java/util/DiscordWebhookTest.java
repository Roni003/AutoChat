package util;

import org.junit.Test;

import java.io.IOException;

    public class DiscordWebhookTest {

    @Test
    public void testWebhook() throws IOException {
        String url = "https://discord.com/api/webhooks/1248630842947797042/u3zPVi_LZRCtZPGOEZqa-W0kjnNfy2B4lC85qwcfiz-BFAlSKUos2gRH9UZPyXr_G0lk";
        DiscordWebhook webhook = new DiscordWebhook(url);
        webhook.setContent("abc");
        webhook.execute();
        webhook.execute();
    }

}
