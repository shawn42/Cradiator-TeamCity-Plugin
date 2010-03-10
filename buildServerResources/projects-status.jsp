<%@ include file="/include.jsp" %>
<%@ page language="java" session="true" errorPage="/runtimeError.jsp" %>
<jsp:useBean id="projects" type="com.cradiator.TeamCityStatusPlugin.ProjectsMonitorViewState" scope="request"/>
<ActiveProjects>
<c:forEach var="project" items="${projects.projects}">
    <Group CCType="TeamCity" status="${project.status}" 
        name="${project.projectName}">
       
        <c:forEach var="build" items="${project.builds}">
          <c:if test="${build.latestBuild != null}">
              <Project name="${build.name}"
                       category=""
                       activity="${build.activityMessage}"
                       lastBuildStatus="${build.lastFinishedBuild.buildStatus}"
                       lastBuildLabel="${build.lastFinishedBuild.buildNumber}"
                       lastBuildTime="${build.lastFinishedBuild.finishDate}"
                       webUrl=""
                       CurrentMessage=""
                       BuildStage=""
                       BuiltPercent="${build.completedPercent}">
                  <messages>
                      <c:if test="${build.failing}">
                          <message text="${build.committersString}" kind="Breakers"/>
                          <message text="${build.responsibleUser}" kind="Breakers"/>
                      </c:if>
                  </messages>
              </Project>
            </c:if>
        </c:forEach>
    </Group>
</c:forEach>
</ActiveProjects>
