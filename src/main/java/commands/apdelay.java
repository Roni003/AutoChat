package commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import studio.roni.AutoPit;

public class apdelay extends CommandBase{

    /**
     * Gets the name of the command
     */
    @Override
    public String getCommandName() {
        return "apdelay";
    }

    /**
     * Gets the usage string for the command.
     *
     * @param sender
     */
    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/apdelay [delay in ms]";
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
    }
}
