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
