package studio.roni;

import net.minecraft.command.ICommand;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.opengl.Display;
import net.minecraftforge.client.ClientCommandHandler;

import commands.apdelay;

@Mod(
        modid = AutoPit.MODID,
        name = "AutoPit",
        version = AutoPit.VERSION
)

public class AutoPit {
    public static final String MODID = "sneaktogglesmod";
    public static final String VERSION = "1.0";

    Minecraft client = Minecraft.getMinecraft();

    boolean modRunning = false;
    long lastToggledGrinder = 0;

    long lastTickTime;

    boolean justJoined = false;

    boolean inPit = false;
    int commandDelay = 10000; // Delay in ms

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        ClientCommandHandler.instance.registerCommand((ICommand) new apdelay(this));
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        long curTime = System.currentTimeMillis();

        long toggledGrinderTimeDiff = curTime - lastToggledGrinder;

        if (toggledGrinderTimeDiff > 500 && Keyboard.isKeyDown(Keyboard.KEY_J)) {
            modRunning = !modRunning;
            System.out.println("Mod running status: " + modRunning);
            selfChatMsg("Toggled AutoPit: " + (modRunning ? "Running" : "Off"));

            lastToggledGrinder = curTime;
        }

        setWindowTitle(); // Update window name
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent e) {
        if(Minecraft.getMinecraft() == null) return;
        if(!modRunning) return;
        if(System.currentTimeMillis() - lastTickTime < 500) return;

        lastTickTime = System.currentTimeMillis();

        System.out.println("Tick");
        chatMsg("/msg");


    }

    @SubscribeEvent
    public void overlayFunc(RenderGameOverlayEvent.Post event) {
        updateOverlay();
    }

    @SubscribeEvent
    public void WorldEvent(WorldEvent.Load e) {
        lastTickTime = System.currentTimeMillis();
        justJoined = true;
    }

    public void setWindowTitle() {
        if(client.thePlayer != null) Display.setTitle("AutoPit - " + client.thePlayer.getName());
    }

    public void updateOverlay() {
        try {
            String[][] infoToDraw = {
                    {"Username", client.thePlayer.getName()},
                    {"AutoPit", modRunning ? "Running" : "Off"},
                    {"FPS", Integer.toString(Minecraft.getDebugFPS())},
                    {"Location", inPit ? "In the pit" : "Not in the pit"},
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

    public void selfChatMsg(String message) {
        IChatComponent msg = new ChatComponentText(message);
        client.thePlayer.addChatMessage(msg);
    }
    public void chatMsg(String message) {
        client.thePlayer.sendChatMessage(message);
    }

    public void drawText(String text, float x, float y, int col) {
        client.fontRendererObj.drawStringWithShadow(text, x, y, 0xffffff);
    }

    public void setCommandDelay(int delay) {
        this.commandDelay = delay;
    }
}
