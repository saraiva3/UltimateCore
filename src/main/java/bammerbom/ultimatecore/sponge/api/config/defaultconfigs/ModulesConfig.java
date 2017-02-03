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
package bammerbom.ultimatecore.sponge.api.config.defaultconfigs;

import bammerbom.ultimatecore.sponge.UltimateCore;
import bammerbom.ultimatecore.sponge.api.config.config.RawFileConfig;
import bammerbom.ultimatecore.sponge.api.error.utils.ErrorLogger;
import bammerbom.ultimatecore.sponge.api.language.utils.Messages;
import bammerbom.ultimatecore.sponge.api.module.Module;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;

import java.io.File;
import java.io.IOException;

public class ModulesConfig extends RawFileConfig {
    public ModulesConfig() {
        super(new File(UltimateCore.get().getConfigFolder().toFile().getPath(), "modules.conf"));
    }

    public void postload() {
        try {
            boolean modified = false;
            if (!node.getNode("modules").getComment().isPresent()) {
                node.getNode("modules").setComment("Set state to 'force', 'enabled' or 'disabled'\nForce will load the module even when another plugin blocks the loading process.");
            }
            for (Module mod : UltimateCore.get().getModuleService().getModules()) {
                if (mod.getIdentifier().equals("default")) {
                    continue;
                }
                CommentedConfigurationNode modnode = node.getNode("modules", mod.getIdentifier());
                if (modnode.getNode("state").isVirtual()) {
                    modified = true;
                    modnode.getNode("state").setValue("enabled");
                }
            }
            if (modified) {
                loader.save(node);
            }
        } catch (IOException e) {
            Messages.log(Messages.getFormatted("core.config.malformedfile", "%conf%", "modules.conf"));
            ErrorLogger.log(e, "Failed to postload modules config");
        }
    }
}
