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
 
package com.ggasoftware.jdiuitest.web.selenium.driver;

import com.ggasoftware.jdiuitest.core.settings.JDIData;
import com.ggasoftware.jdiuitest.core.utils.common.StringUtils;
import com.ggasoftware.jdiuitest.core.utils.common.Timer;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

import static com.ggasoftware.jdiuitest.core.settings.JDISettings.driverFactory;
import static org.apache.commons.io.FileUtils.copyFile;
import static org.openqa.selenium.OutputType.FILE;

/**
 * Created by Roman_Iovlev on 7/21/2015.
 */
public class ScreenshotMaker {
    public static String screensPath = "/.logs/images/";
    public String pathSuffix = screensPath;

    public ScreenshotMaker() {
    }

    public ScreenshotMaker(String pathSuffix) {
        this.pathSuffix = pathSuffix;
    }

    public static String takeScreen() throws IOException {
        return new ScreenshotMaker().takeScreenshot();
    }

    public static String getValidUrl(String logPath) {
        if (logPath == null || logPath.equals(""))
            return "";
        String result = logPath.replace("/", "\\");
        if (result.charAt(1) != ':')
            if (result.substring(0, 3).equals("..\\"))
                result = result.substring(2);
        if (result.charAt(0) != '\\')
            result = "\\" + result;
        return (result.charAt(result.length() - 1) == '\\')
                ? result
                : result + "\\";
    }

    public static String doScreenshotGetMessage() {
        String screenshotPath;
        try {
            screenshotPath = takeScreen();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return (screenshotPath.equals(""))
                ? "Failed to do Screenshot"
                : StringUtils.LINE_BREAK + "Add screenshot to: " + screenshotPath;
    }

    public String takeScreenshot() throws IOException {
        String path = new File(".").getCanonicalPath() + getValidUrl(pathSuffix);
        String screensFilePath = getFileName(path + (JDIData.testName != null ? JDIData.testName : "screen") + Timer.nowDate().replace(":", "-"));
        new File(screensFilePath).getParentFile().mkdirs();
        File screensFile = ((TakesScreenshot) driverFactory.getDriver()).getScreenshotAs(FILE);
        copyFile(screensFile, new File(screensFilePath));
        return screensFilePath;
    }

    private String getFileName(String fileName) {
        int num = 1;
        String newName = fileName;
        while (new File(newName + ".jpg").exists())
            newName = fileName + "_" + num++;
        return newName + ".jpg";
    }
}