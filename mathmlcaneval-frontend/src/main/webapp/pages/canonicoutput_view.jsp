<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">  
        <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
            <div class="modal fade" id="findSimilarModal" tabindex="-1" role="dialog" aria-labelledby="findSimilarModal" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <form:form modelAttribute="findSimilarForm" action="${pageContext.request.contextPath}/formula/similar/" method="POST">
                            <input type="hidden" value="<c:out value="${canonicOutput.parents[0].id}" />" name="formulaID" />
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                <h4 class="modal-title" id="myModalLabel"><spring:message code="general.label.title.similarity.match" /></h4>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-md-8">
                                        <h3 class="disable-top-margin"><spring:message code="similarity.method.distance" /></h3>

                                    </div>
                                    <div class="col-md-4">
                                        <div class="checkbox">
                                            <label>                                                
                                                <form:checkbox path="useDistance" /> 
                                                <spring:message code="general.label.method.use" /> 
                                            </label>
                                        </div>                                    
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-8 col-md-offset-4">
                                        <div class="row">
                                            <div class="col-md-4">
                                                <h4 class="disable-top-margin"><spring:message code="similarity.method.distance.treshold" /></h4>
                                            </div>
                                            <div class="col-md-6">
                                                <input 
                                                    id="similarityFuzzySlider"
                                                    name="distanceMethodValue" 
                                                    data-slider="true"
                                                    data-slider-range="0,1"
                                                    data-slider-step="0.05"
                                                    data-slider-theme="volume"
                                                    />
                                            </div>
                                            <div class="col-md-2" id="tresholdOutput">
                                                0.00
                                            </div>
                                        </div>                                    
                                    </div>
                                </div>
                                <hr />
                                <div class="row">
                                    <div class="col-md-8">
                                        <h3 class="disable-top-margin"><spring:message code="similarity.method.count" /></h3>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="checkbox">
                                            <label>                                                
                                                <form:checkbox path="useCount"/> 
                                                <spring:message code="general.label.method.use" /> 
                                            </label>                                           
                                        </div>                                    
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-4">
                                        <form:select path="countCondition" cssClass="form-control">
                                            <form:option value="must"><spring:message code="general.label.logic.and" /></form:option>
                                            <form:option value="should"><spring:message code="general.label.logic.or" /></form:option>
                                        </form:select>                                                                   
                                    </div>
                                    <div class="col-md-8">                                  
                                        <div class="row">
                                            <div class="col-md-4">
                                                <h4 class="disable-top-margin"><spring:message code="similarity.method.count.profile" /></h4>
                                            </div>
                                            <div class="col-md-8">
                                                <form:select path="countElementMethodValue" cssClass="form-control">
                                                    <form:option value="must"><spring:message code="similarity.method.count.must" /></form:option>
                                                    <form:option value="should"><spring:message code="similarity.method.count.should" /></form:option>
                                                </form:select>
                                            </div>
                                        </div>
                                    </div>
                                </div>                                
                                <hr />
                                <div class="row">
                                    <div class="col-md-12">
                                        <h3 class="disable-top-margin"><spring:message code="similarity.method.other.options" /></h3>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="checkbox">
                                            <label>
                                                <form:checkbox path="override" />
                                                <spring:message code="similarity.override" /> 
                                            </label>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="checkbox">
                                            <label>
                                                <form:checkbox path="directWrite" />
                                                <spring:message code="similarity.direct.import" />
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="general.label.close" /></button>
                                <input type="submit" class="btn btn-warning" value="<spring:message code="general.button.submit" />"/>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </sec:authorize>

        <div class="container content">
            <div class="row">
                <div class="col-md-12">
                    <h1><spring:message code="entity.output.entry" /></h1>
                </div> <!-- /col-md-12>
            </div> <!-- /row -->

            <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                <div class="row space-bottom-10">
                    <div class="col-md-12">
                        <div class="btn-group pull-right">                        
                            <button class="btn btn-primary" data-toggle="modal" data-target="#findSimilarModal">
                                <spring:message code="entity.canonicOutput.findSimilar" />
                            </button>                
                            <a href="${pageContext.request.contextPath}/canonicoutput/delete/${canonicOutput.id}" class="btn btn-danger">
                                <spring:message code="general.label.delete" />
                            </a>                        
                        </div> <!-- /btn-group -->
                    </div> <!-- /col-md-12 -->
                </div> <!-- /row -->
            </sec:authorize>

            <div class="row">
                <div class="col-md-5">
                    <div class="panel panel-primary">                        
                        <div class="panel-heading">
                            <spring:message code="general.label.details" />
                        </div>
                        <table class="table table-bordered table-striped">
                            <tr>
                                <td><spring:message code="general.field.id" /></td>
                                <td><c:out value="${canonicOutput.id}" /></td>
                            </tr>
                            <tr>
                                <td><spring:message code="entity.canonicOutput.parents" /></td>
                                <td>
                                    <c:forEach items="${canonicOutput.parents}" var="parent" varStatus="loop">
                                        <a href="${pageContext.request.contextPath}/formula/view/${parent.id}">${parent.id}</a>
                                        ${!loop.last ? ', ' : ''}
                                    </c:forEach>
                                </td>
                            </tr>
                            <tr>
                                <td><spring:message code="entity.formula.configuration" /></td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/configuration/view/${canonicOutput.applicationRun.configuration.id}/">
                                        <c:out value="${canonicOutput.applicationRun.configuration.name}" />
                                    </a>
                                </td>
                            </tr>
                            <tr>
                                <td><spring:message code="entity.revision.hash" /></td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/revision/view/${canonicOutput.applicationRun.revision.id}/">
                                        <c:out value="${canonicOutput.applicationRun.revision.revisionHash}" />
                                    </a>
                                </td>
                            </tr>
                        </table>
                    </div> <!-- /panel -->                
                </div> <!-- /col-md-6 -->

                <div class="col-md-7">
                    <div class="panel panel-success">
                        <div class="panel-heading">
                            <spring:message code="entity.canonicOutput.annotations" />
                        </div> <!-- /panel-heading -->
                        <table id="annotationTable" class="table table-striped">
                            <tbody>
                                <c:choose>
                                    <c:when test="${fn:length(canonicOutput.annotations) == 0}">
                                        <tr class="empty-table">
                                            <td>
                                                <spring:message code="general.table.norecords" />
                                            </td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${canonicOutput.annotations}" var="annotationRow">
                                            <tr>
                                                <td><c:out value="${annotationRow.user.username}" /></td>
                                                <td class="annotation-note-cell"><c:out value="${annotationRow.annotationContent}" /></td>
                                                <sec:authorize access="hasRole('ROLE_USER')">
                                                <td><a href="#" class="annotation-remove" id="${annotationRow.id}"><span class="glyphicon glyphicon-remove"></span></a></td>
                                                </sec:authorize>
                                            </tr>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                        <sec:authorize access="hasRole('ROLE_USER')">
                            <form:form method="POST" 
                                       action="${pageContext.request.contextPath}/annotation/annotate/" 
                                       modelAttribute="annotationAction"
                                       id="annotationForm">
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="input-group">
                                                <div class="input-group-btn">
                                                    <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                                                        <spring:message code="general.label.annotate" /> <span class="caret"></span>
                                                    </button>

                                                    <ul class="dropdown-menu" role="menu">
                                                        <c:forEach items="${annotationValueList}" var="entry">
                                                            <li>
                                                            <a href="#" class="annotation-option" data-annotation="${entry.value}" data-description="${entry.description}">
                                                                <span class="glyphicon glyphicon-${entry.icon}"></span> ${entry.value}
                                                            </a>
                                                        </li>
                                                        </c:forEach>
                                                        <li class="divider"></li>
                                                        <li>
                                                            <a href="#" id="clear-form">
                                                                <spring:message code="general.label.clear.input" />
                                                            </a>
                                                        </li>
                                                    </ul>
                                                </div> <!--/input-group-btn --> 
                                                <form:input type="text" id="annotation-value" path="annotationContent" cssClass="form-control" />                                                
                                                <input type="hidden" name="clazz" value="canonicoutput" />
                                                <input type="hidden" name="entityID" value="<c:out value="${canonicOutput.id}" />" />
                                                <span class="input-group-btn">
                                                    <input type="submit" class="btn btn-primary" value="<spring:message code="general.button.submit" />" />
                                                </span>
                                            </div> <!--/input-group -->
                                        </div> <!-- /col-md-12-->
                                    </div> <!-- /row -->
                                </div> <!-- /panel-body -->
                            </form:form>
                        </sec:authorize>
                    </div> <!-- /panel -->  
                </div> <!-- /col-md-6 -->
            </div> <!-- /row -->

            <div class="row" id="formulaWindow">
                <div class="col-md-12">


                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <spring:message code="entity.formula.xml" />
                            <div class="pull-right">
                                <span class="glyphicon glyphicon-resize-full" id="resizeWindow"></span>
                            </div>
                        </div> <!-- /panel-heading -->
                        <div class="panel-body">
                            <ul class="nav nav-tabs">
                                <li>
                                    <a href="#original" data-toggle="tab">
                                        <spring:message code="entity.canonicOutput.original" />
                                    </a>
                                </li>
                                <li class="active">
                                    <a href="#canonicalized" data-toggle="tab">
                                        <spring:message code="entity.canonicOutput.outputForm" />
                                    </a>
                                </li>
                                <li>
                                    <a href="#diff" data-toggle="tab" onclick="diffView();">
                                        <spring:message code="entity.canonicOutput.diff" />
                                    </a>
                                </li>
                            </ul>

                            <div class="tab-content">
                                <div class="tab-pane active" id="canonicalized">
                                    <div class="panel panel-default">
                                        <div class="panel-heading"><spring:message code="entity.formula.rendered" /></div>
                                        <div class="panel-body">
                                            <div class="well-sm">
                                                <c:out value="${canonicOutput.outputForm}" escapeXml="false" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel panel-default">
                                        <div class="panel-heading"><spring:message code="entity.formula.xml" /></div>
                                        <div class="panel-body">
                                            <pre class="brush: xml">
                                                <c:out value="${canonicOutput.outputForm}" />
                                            </pre>
                                        </div>
                                    </div>
                                </div>

                                <div class="tab-pane" id="original">
                                    <div class="panel panel-default">
                                        <div class="panel-heading"><spring:message code="entity.formula.rendered" /></div>
                                        <div class="panel-body">
                                            <div class="well-sm">
                                                <c:out value="${canonicOutput.parents[0].xml}" escapeXml="false" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel panel-default">
                                        <div class="panel-heading"><spring:message code="entity.formula.xml" /></div>
                                        <div class="panel-body">
                                            <pre class="brush: xml">
                                                <c:out value="${canonicOutput.parents[0].xml}" />
                                            </pre>
                                        </div>
                                    </div>
                                </div> <!-- /tab-pane -->

                                <div class="tab-pane" id="diff">
                                    <!--see footer for the javascript  -->
                                </div> <!-- /tab-pane -->
                            </div> <!-- /tab-content--> 
                        </div> <!-- /panel-body -->
                    </div><!-- /panel -->
                </div> <!--/col-md-12 -->
            </div> <!-- /row -->
            
            <!-- moveMe is for javascript to find next element which is moved when
                            xml preview is set to page wide-->
            <div class="moveMe"></div>
        </div> <!-- /container -->
    </tiles:putAttribute>
</tiles:insertDefinition>
