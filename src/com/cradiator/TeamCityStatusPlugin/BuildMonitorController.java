/*
   Copyright (c) 2007-2009 Nat Pryce.

   This file is part of Team Piazza.

   Team Piazza is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 3 of the License, or
   (at your option) any later version.

   Team Piazza is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.cradiator.TeamCityStatusPlugin;

import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.serverSide.ProjectManager;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.serverSide.SProject;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class BuildMonitorController extends BaseController {
    public static final String PROJECT_ID = "projectId";

    private final ProjectManager projectManager;
    private final Cradiator cradiator;

    public BuildMonitorController(SBuildServer server, ProjectManager projectManager, Cradiator cradiator) {
        super(server);
        this.projectManager = projectManager;
        this.cradiator = cradiator;
    }

    protected ModelAndView doHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (requestHasParameter(request, PROJECT_ID)) {
            return showProject(request.getParameter(PROJECT_ID), response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "no project id specified");
            return null;
        }
    }

    private ModelAndView showProject(String projectId, HttpServletResponse response) throws IOException {
        SProject project = projectManager.findProjectById(projectId);
        if (project == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "no project with id " + projectId);
            return null;
        }
        return modelWithView("project-status.jsp")
                .addObject("project", new ProjectMonitorViewState(project));
    }

    private ModelAndView modelWithView(String viewJSP) {
        return new ModelAndView(cradiator.resourcePath(viewJSP))
                .addObject("version", cradiator.version())
                .addObject("resourceRoot", cradiator.resourcePath(""));
    }

    private boolean requestHasParameter(HttpServletRequest request, String parameterName) {
        return request.getParameterMap().containsKey(parameterName);
    }
}
