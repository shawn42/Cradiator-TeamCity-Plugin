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

import jetbrains.buildServer.serverSide.SBuildType;
import jetbrains.buildServer.serverSide.SProject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.cradiator.TeamCityStatusPlugin.BuildStatus.SUCCESS;

public class ProjectMonitorViewState {
    private final SProject project;
    private final Set<String> committers = new HashSet<String>();
    private final List<BuildTypeMonitorViewState> builds;

    public ProjectMonitorViewState(SProject project) {
        this.project = project;

        builds = new ArrayList<BuildTypeMonitorViewState>();
        for (SBuildType buildType : project.getBuildTypes()) {
            if (buildType.isAllowExternalStatus()) {
                builds.add(new BuildTypeMonitorViewState(buildType));
            }
        }

        for (BuildTypeMonitorViewState build : builds) {
            committers.addAll(build.getCommitters());
        }
    }

    public String getProjectName() {
        return project.getName();
    }

    public String getCombinedStatusClasses() {
        return status().toStringReflectingCurrentlyBuilding(isBuilding());
    }

    public String getStatus() {
        return status().toString();
    }

    public BuildStatus status() {
        if (builds.isEmpty()) {
            return BuildStatus.UNKNOWN;
        }
        else {
            BuildStatus status = SUCCESS;
            for (BuildTypeMonitorViewState build : builds) {
                status = status.mostSevere(build.status());
            }
            return status;
        }
    }

    public boolean isBuilding() {
        for (BuildTypeMonitorViewState build : builds) {
            if (build.isBuilding()) return true;
        }
        return false;
    }

    public List<BuildTypeMonitorViewState> getBuilds() {
        return builds;
    }
    
    public Set<String> getCommitters() {
        return committers;
    }
}
