package commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.EnumChatFormatting;
import studio.roni.AutoChat;

public class acmsg extends CommandBase {
    AutoChat modInstance;

    public acmsg(AutoChat modInstance) {
        this.modInstance = modInstance;
    }

    /**
     * Gets the name of the command
     */
    @Override
    public String getCommandName() {
        return "acmsg";
    }

    /**
     * Gets the usage string for the command.
     *
     * @param sender
     */
    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/acmsg [message]";
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

        StringBuilder newCommand = new StringBuilder();
        for (String arg : args) {
            newCommand.append(arg);
            System.out.println(arg);
            newCommand.append(" ");
        }
        newCommand.deleteCharAt(newCommand.length()-1);

        System.out.println("new command set: " + newCommand);

        modInstance.setCommand(newCommand.toString());
        modInstance.selfChatMsg("Set chat message to: " + newCommand, EnumChatFormatting.AQUA);
    }
}
