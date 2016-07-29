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
package bammerbom.ultimatecore.sponge.impl.module;

import bammerbom.ultimatecore.sponge.UltimateCore;
import bammerbom.ultimatecore.sponge.api.module.Module;
import bammerbom.ultimatecore.sponge.api.module.ModuleService;
import bammerbom.ultimatecore.sponge.utils.Messages;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.stream.Collectors;

public class UCModuleService implements ModuleService {

    private List<Module> modules = new ArrayList<>();

    /**
     * Retrieves list of all registered modules
     *
     * @return the list
     */
    @Override
    public List<Module> getRegisteredModules() {
        return modules;
    }

    /**
     * Retrieves a specific module by id
     *
     * @param id the id to search for
     * @return the module, or Optional.empty() if not found
     */
    @Override
    public Optional<Module> getModule(String id) {
        for (Module mod : modules) {
            if (mod.getIdentifier().equalsIgnoreCase(id)) {
                return Optional.of(mod);
            }
        }
        return Optional.empty();
    }

    /**
     * Registers a new module
     *
     * @param module
     * @return
     */
    @Override
    public boolean registerModule(Module module) {
        modules.add(module);
        return true;
    }

    /**
     * Unregisters a module
     *
     * @param id The identifier of the module
     * @return Whether the module was found
     */
    @Override
    public boolean unregisterModule(String id) {
        return getModule(id).isPresent() ? unregisterModule(getModule(id).get()) : false;
    }

    /**
     * Unregisters a module
     *
     * @param module The instance of the module
     * @return Whether the module was found
     */
    @Override
    public boolean unregisterModule(Module module) {
        if (modules.contains(module)) {
            modules.remove(module);
            return true;
        }
        return false;
    }

    /**
     * This loads module, registers it at the ModuleService and calls the onRegister() method for the module.
     *
     * @param file The .jar file for the module
     * @return The module, or Optional.empty() if not found
     */
    @Override
    public Optional<Module> load(File file) {
        try {
            URL url = file.toURI().toURL();
            URL[] urls = new URL[]{url};
            ClassLoader cl = new URLClassLoader(urls);

            List<String> lines;
            InputStream inputStream = cl.getResourceAsStream("ucmodule.info");
            try (BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream))) {
                lines = buffer.lines().collect(Collectors.toList());
            } catch (Exception ex) {
                ex.printStackTrace();
                return Optional.empty();
            }
            inputStream.close();

            Map<String, String> keys = new HashMap<>();
            for (String line : lines) {
                if (line.contains("=")) {
                    keys.put(line.split("=")[0], line.split("=")[1]);
                } else if (!line.startsWith("//")) {
                    Messages.log("Invalid line in ucmodule.info file of " + file.getName());
                    Messages.log(line);
                }
            }

            if (keys.containsKey("main")) {
                Module module = (Module) cl.loadClass(keys.get("main")).getConstructors()[0].newInstance();
                UltimateCore.get().getModuleService().registerModule(module);
                module.onRegister();
                return Optional.of(module);
            } else {
                Messages.log("The ucmodule.info file of " + file.getName() + " doesn't contain a main class reference.");
                return Optional.empty();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }
}