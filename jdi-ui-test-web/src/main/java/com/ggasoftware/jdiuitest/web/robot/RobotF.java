/*
 * Copyright 2004-2016 EPAM Systems
 *
 * This file is part of JDI project.
 *
 * JDI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JDI is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty ofMERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JDI. If not, see <http://www.gnu.org/licenses/>.
 */
 
package com.ggasoftware.jdiuitest.web.robot;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import static com.ggasoftware.jdiuitest.core.settings.JDISettings.exception;
import static com.ggasoftware.jdiuitest.core.utils.common.Timer.sleep;
import static java.awt.event.KeyEvent.*;

/**
 * Created by Roman_Iovlev on 9/7/2015.
 */
public class RobotF {
    public static RobotF robot = new RobotF();
    private Robot robotInstance;

    public RobotF() {
        try {
            robotInstance = new Robot();
        } catch (Exception ex) {
            throw exception("Can't instantiate Robot");
        }
    }

    public void pasteText(String text) {
        try {
            StringSelection stringSelection = new StringSelection(text);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, (clipboard1, contents) -> {
            });
            sleep(1000);
            robotInstance.keyPress(VK_CONTROL);
            robotInstance.keyPress(VK_V);

            robotInstance.keyRelease(VK_CONTROL);
            robotInstance.keyPress(VK_ENTER);
            robotInstance.keyRelease(VK_ENTER);
        } catch (Exception ex) {
            throw exception("Robot Input exception");
        }
    }
}