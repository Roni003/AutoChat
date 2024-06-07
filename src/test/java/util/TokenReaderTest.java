package util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;

public class TokenReaderTest {

    @Test
    public void testPath() {
        TokenReader tr = new TokenReader();
        assertNotNull(tr.windowsPath);
        System.out.println("WindowsPath: " + tr.windowsPath);
    }

    @Test
    public void testFile() {
        TokenReader tr = new TokenReader();
        File f = new File(tr.windowsPath);
        assertTrue(f.isFile() && f.canRead());

    }

    @Test
    public void testReadFile() throws FileNotFoundException, IOException{
        String content = new TokenReader().readLunarFile();
        assertNotEquals(content,"");
        System.out.println(content);
    }

    @Test
    public void testTokenGrabber() throws IOException {
        TokenReader tr = new TokenReader();
        Map<String, String> tokens = tr.getTokens();

        for(String key : tokens.keySet()) {
            System.out.println(key + ": " + tokens.get(key));

        }
    }
}
