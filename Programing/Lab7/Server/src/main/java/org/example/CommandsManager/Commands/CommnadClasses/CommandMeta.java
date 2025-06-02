package org.example.CommandsManager.Commands.CommnadClasses;

import org.example.Enums.PrivilegeLevel;


public class CommandMeta {
    public Command command;
    public PrivilegeLevel privilege;

    public CommandMeta(Command command, PrivilegeLevel privilege) {
        this.command = command;
        this.privilege = privilege;
    }
}

