/*
 * This file is part of UltimateCore, licensed under the MIT License (MIT).
 *
 * Copyright (c) Bammerbom
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package bammerbom.ultimatecore.sponge.modules.jail.commands;

import bammerbom.ultimatecore.sponge.UltimateCore;
import bammerbom.ultimatecore.sponge.api.command.HighCommand;
import bammerbom.ultimatecore.sponge.api.command.annotations.CommandInfo;
import bammerbom.ultimatecore.sponge.api.command.argument.Arguments;
import bammerbom.ultimatecore.sponge.api.language.utils.Messages;
import bammerbom.ultimatecore.sponge.api.permission.Permission;
import bammerbom.ultimatecore.sponge.api.teleport.Teleportation;
import bammerbom.ultimatecore.sponge.modules.jail.JailModule;
import bammerbom.ultimatecore.sponge.modules.jail.api.Jail;
import bammerbom.ultimatecore.sponge.modules.jail.api.JailPermissions;
import bammerbom.ultimatecore.sponge.modules.jail.commands.arguments.JailArgument;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

import java.util.Arrays;
import java.util.List;

@CommandInfo(module = JailModule.class, aliases = {"jailtp", "jailteleport", "tpjail", "teleportjail"})
public class JailtpCommand implements HighCommand {

    @Override
    public Permission getPermission() {
        return JailPermissions.UC_JAIL_JAILTP_BASE;
    }

    @Override
    public List<Permission> getPermissions() {
        return Arrays.asList(JailPermissions.UC_JAIL_DELJAIL_BASE);
    }

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{Arguments.builder(new JailArgument(Text.of("jail"))).onlyOne().build()};
    }

    @Override
    public CommandResult execute(CommandSource sender, CommandContext args) throws CommandException {
        checkIfPlayer(sender);
        checkPermission(sender, JailPermissions.UC_JAIL_JAILTP_BASE);

        Player p = (Player) sender;
        Jail jail = args.<Jail>getOne("jail").get();
        Transform<World> loc = jail.getLocation().toOptional().orElseThrow(() -> Messages.error(sender, "jail.command.jail.notavailable"));

        Teleportation tp = UltimateCore.get().getTeleportService().createTeleportation(p, Arrays.asList(p), loc, tel -> {
        }, (tel, reason) -> {
        }, false, false);
        tp.start();

        Messages.send(sender, "jail.command.jailtp.success", "%jail%", jail.getName());
        return CommandResult.success();
    }
}
