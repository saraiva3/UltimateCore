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
package bammerbom.ultimatecore.sponge.modules.jail.api;

import bammerbom.ultimatecore.sponge.api.permission.Permission;
import bammerbom.ultimatecore.sponge.api.permission.PermissionLevel;
import bammerbom.ultimatecore.sponge.api.permission.PermissionOption;
import org.spongepowered.api.text.Text;

public class JailPermissions {
    public static Permission UC_JAIL_JAIL_BASE = Permission.create("uc.jail.jail.base", "jail", PermissionLevel.ADMIN, "jail", Text.of("Allows you to use the jail command."));
    public static Permission UC_JAIL_UNJAIL_BASE = Permission.create("uc.jail.unjail.base", "jail", PermissionLevel.ADMIN, "unjail", Text.of("Allows you to use the unjail command."));
    public static Permission UC_JAIL_SETJAIL_BASE = Permission.create("uc.jail.setjail.base", "jail", PermissionLevel.ADMIN, "setjail", Text.of("Allows you to use the setjail command."));
    public static Permission UC_JAIL_DELJAIL_BASE = Permission.create("uc.jail.deljail.base", "jail", PermissionLevel.ADMIN, "deljail", Text.of("Allows you to use the deljail command."));
    public static Permission UC_JAIL_JAILLIST_BASE = Permission.create("uc.jail.jaillist.base", "jail", PermissionLevel.ADMIN, "jaillist", Text.of("Allows you to use the jaillist command."));
    public static Permission UC_JAIL_JAILTP_BASE = Permission.create("uc.jail.jailtp.base", "jail", PermissionLevel.ADMIN, "jailtp", Text.of("Allows you to use the jailtp command."));

    public static PermissionOption UC_JAIL_EXEMPTPOWER = PermissionOption.create("uc.jail.exemptpower", "jail", "jail", "0", Text.of("The amount of exemptpower the player has. If a player's jailpower is higher than or equal to the targets exemptpower he can jail the target."));
    public static PermissionOption UC_JAIL_POWER = PermissionOption.create("uc.jail.power", "jail", "jail", "0", Text.of("The amount of jailpower the player has. If a player's jailpower is higher than or equal to the targets exemptpower he can jail the target."));
}
