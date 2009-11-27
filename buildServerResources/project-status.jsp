<%@ include file="/include.jsp" %>
<%@ page language="java" session="true" errorPage="/runtimeError.jsp" %>
<%--<jsp:useBean id="build" type="com.natpryce.piazza.BuildTypeMonitorViewState" scope="request"/>--%>
<jsp:useBean id="project" type="com.natpryce.piazza.ProjectMonitorViewState" scope="request"/>
<%--<jsp:useBean id="resourceRoot" type="java.lang.String" scope="request"/>--%>
<%--<jsp:useBean id="version" type="java.lang.String" scope="request"/>--%>
<Projects CCType="TeamCity">
    <c:forEach var="build" items="${project.builds}">
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
                </c:if>
            </messages>
        </Project>
    </c:forEach>
</Projects>