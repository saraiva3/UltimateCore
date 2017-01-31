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
package bammerbom.ultimatecore.sponge.api.config.datafiles;

import bammerbom.ultimatecore.sponge.UltimateCore;
import bammerbom.ultimatecore.sponge.utils.ErrorLogger;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.world.World;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class WorldDataFile implements DataFile {
    private static File path = new File(UltimateCore.get().getDataFolder().toFile().getPath() + "/worlddata");
    private UUID uuid;

    public WorldDataFile(World world) {
        this.uuid = world.getUniqueId();
    }

    public WorldDataFile(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public File getFile() {
        if (!path.exists()) {
            path.mkdirs();
        }
        //TODO maybe move file to world folder when world#getdirectory is implemented?
        File file = new File(path, uuid.toString() + ".data");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            ErrorLogger.log(e, "Failed to load worlds data file for " + uuid);
        }
        return file;
    }

    @Override
    public ConfigurationLoader<CommentedConfigurationNode> getLoader() {
        File file = getFile();
        return HoconConfigurationLoader.builder().setFile(file).build();
    }

    @Override
    public CommentedConfigurationNode get() {
        try {
            return getLoader().load();
        } catch (IOException e) {
            ErrorLogger.log(e, "Failed to load node for  " + uuid);
            return null;
        }
    }

    @Override
    public boolean save(CommentedConfigurationNode node) {
        try {
            getLoader().save(node);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}