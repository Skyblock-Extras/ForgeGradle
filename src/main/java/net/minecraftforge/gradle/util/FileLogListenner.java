/*
 * A Gradle plugin for the creation of Minecraft mods and MinecraftForge plugins.
 * Copyright (C) 2013 Minecraft Forge
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
 * USA
 */
package net.minecraftforge.gradle.util;

import com.google.common.io.Files;
import org.gradle.BuildListener;
import org.gradle.BuildResult;
import org.gradle.api.initialization.Settings;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.logging.StandardOutputListener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class FileLogListenner implements StandardOutputListener, BuildListener {
    private BufferedWriter writer;

    public FileLogListenner(File file) {

        try {
            if (file.exists())
                file.delete();
            else
                file.getParentFile().mkdirs();

            file.createNewFile();

            writer = Files.newWriter(file, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void projectsLoaded(Gradle arg0) {
    }

    @Override
    public void onOutput(CharSequence arg0) {
        try {
            writer.write(arg0.toString());
        } catch (IOException e) {
            // to stop recursion....
        }
    }

    @Override
    public void buildFinished(BuildResult arg0) {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void projectsEvaluated(Gradle arg0) {
    }  // nothing

    @SuppressWarnings("DEPRECATED") // can't fix this
    @Override
    public void buildStarted(Gradle gradle) { }

    @Override
    public void beforeSettings(Settings settings) {

    }

    @Override
    public void settingsEvaluated(Settings arg0) {
    } // nothing

}
