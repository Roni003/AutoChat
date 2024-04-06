package commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.EnumChatFormatting;
import studio.roni.AutoChat;

public class acdelay extends CommandBase{
    AutoChat modInstance;

    public acdelay(AutoChat modInstance) {
        this.modInstance = modInstance;
    }

    /**
     * Gets the name of the command
     */
    @Override
    public String getCommandName() {
        return "acdelay";
    }

    /**
     * Gets the usage string for the command.
     *
     * @param sender
     */
    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/acdelay [delay in ms]";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender)
    {
        return true;
    }

    /**
     * Callback when the command is invoked
     *
     * @param sender
     * @param args
     */
    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if(args.length < 1) {
            System.out.println("Missing command parameter for: " + getCommandName());
            return;
        }
        int delay = Integer.parseInt(args[0]);
        System.out.println("new delay: " + args[0]);
        modInstance.setCommandDelay(delay);
        modInstance.selfChatMsg("Set delay between commands to: " + args[0], EnumChatFormatting.AQUA);
    }
}
