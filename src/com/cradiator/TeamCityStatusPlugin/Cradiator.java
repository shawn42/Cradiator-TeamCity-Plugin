
/*
 * Cradiator TeamCity Plugin Copyright (c) 2009 Peter van der Woude, James Summerton
 *
 *    This file is part of Cradiator - http://cradiator.codeplex.com/
 *
 *    Cradiator is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    Cradiator is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *    Original idea and TeamCity plugin from Team Piazza Copyright (c) 2007-2009 Nat Pryce.
 */

package com.cradiator.TeamCityStatusPlugin;

import jetbrains.buildServer.serverSide.MainConfigProcessor;
import jetbrains.buildServer.serverSide.ProjectManager;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import jetbrains.buildServer.web.openapi.WebResourcesManager;
import org.jdom.Element;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Cradiator implements MainConfigProcessor {
	public static final String PLUGIN_NAME = Cradiator.class.getSimpleName().toLowerCase();
    public static final String PATH = "/" + PLUGIN_NAME + ".html";

	private final WebResourcesManager webResourcesManager;
	private final String version;

    public Cradiator(SBuildServer server,
                  ProjectManager projectManager,
				  WebControllerManager webControllerManager,
				  WebResourcesManager webResourcesManager) 
	{
		this.webResourcesManager = webResourcesManager;
		this.version = loadVersionFromResource();

        server.registerExtension(MainConfigProcessor.class, PLUGIN_NAME, this);

        webResourcesManager.addPluginResources(PLUGIN_NAME, PLUGIN_NAME + ".jar");
        webControllerManager.registerController(
                PATH, new BuildMonitorController(server, projectManager, this));
	}

    public String resourcePath(String resourceName) {
		return webResourcesManager.resourcePath(PLUGIN_NAME, resourceName);
	}
	
	public String version() {
		return version;
	}
	
	private String loadVersionFromResource() {
		Properties properties = new Properties();
		
		InputStream input = getClass().getResourceAsStream("/version.properties");
		try {
			try {
				properties.load(input);
			}
			finally {
				input.close();
			}
		}
		catch (IOException e) {
			throw new RuntimeException("version information incorrectly configured");
		}
		
		return properties.getProperty("cradiator.version");
	}

    public void readFrom(Element serverConfigRoot) {
        /*Element piazzaConfigRoot = serverConfigRoot.getChild("TeamCityStatusPlugin");
        if (piazzaConfigRoot != null) {
            this.userGroup = UserGroup.loadFrom(piazzaConfigRoot);
        }
        else {
            this.userGroup = new UserGroup();
        }*/
    }
    
    public void writeTo(Element serverConfigRoot) {
        /*Element piazzaConfigRoot = new Element("TeamCityStatusPlugin");
        userGroup.writeTo(piazzaConfigRoot);
        serverConfigRoot.addContent(piazzaConfigRoot);*/
    }    
}
