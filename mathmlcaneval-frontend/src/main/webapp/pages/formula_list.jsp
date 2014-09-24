<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">
        <div class="container content">
            <div class="row">
                <div class="col-md-12">
                    <div class="panel-group" id="accordion">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                                        !Advanced search
                                    </a>
                                </h4>
                            </div>
                            <div id="collapseOne" class="panel-collapse collapse in"><!-- if "in" class is added then search is shown @load -->
                                <div class="panel-body">
                                    <form:form action="${pageContext.request.contextPath}/formula/search/" method="post" commandName="formulaSearchRequestForm">
                                        <div class="row form-row">
                                            <label for="program" class="col-md-2 col-md-offset-2 control-label">!program</label>
                                            <div class="col-md-6">
                                                <form:select items="${programList}" path="program" itemLabel="name" itemValue="id" cssClass="form-control" />
                                            </div>                                       
                                        </div>
                                        <div class="row form-row">
                                            <label for="program" class="col-md-2 col-md-offset-2 control-label">!conf</label>
                                            <div class="col-md-6">
                                                <form:select items="${configurationList}" path="configuration" itemLabel="name" itemValue="id" cssClass="form-control" />
                                            </div>                                       
                                        </div>
                                        <div class="row form-row">
                                            <label for="program" class="col-md-2 col-md-offset-2 control-label">!source document</label>
                                            <div class="col-md-6">
                                                <form:select items="${sourceDocumentList}" path="sourceDocument" itemLabel="name" itemValue="id" cssClass="form-control" />
                                            </div>                                       
                                        </div>
                                        <div id="elementRowsDiv">
                                            <c:choose>
                                                <c:when test="${fn:length(formulaSearchRequestForm.elementRows) eq 0}">
                                                    <div class="row form-row">
                                                        <label for="sourceDocument" class="col-md-2 col-md-offset-2 control-label">!elements</label>
                                                        <div class="col-md-3">
                                                            <form:select items="${elementList}" path="elementRows[0].element" itemLabel="elementName" itemValue="id" cssClass="form-control elementList"/> 
                                                        </div>
                                                        <label for="sourceDocument" class="col-md-1 control-label">!pocet</label> 
                                                        <div class="col-md-2">
                                                            <form:input path="elementRows[0].value" cssClass="form-control" />
                                                        </div>
                                                        <div class="col-md-2">
                                                            <span class="btn btn-sm btn-primary addelementrow"><i class=" glyphicon glyphicon-plus"></i></span>

                                                            <span class="btn btn-sm btn-primary removeelemenrow"><i class=" glyphicon glyphicon-minus"></i></span>
                                                        </div>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:forEach items="${formulaSearchRequestForm.elementRows}" var="entry" varStatus="i">
                                                        <div class="row form-row">
                                                            <label for="sourceDocument" class="col-md-2 col-md-offset-2 control-label">!elements</label>
                                                            <div class="col-md-3">
                                                                <form:select items="${elementList}" path="elementRows[${i.index}].element" itemLabel="elementName" itemValue="id" cssClass="form-control elementList"/> 
                                                            </div>
                                                            <label for="sourceDocument" class="col-md-1 control-label">!pocet</label> 
                                                            <div class="col-md-2">
                                                                <form:input path="elementRows[${i.index}].value" cssClass="form-control" />
                                                            </div>
                                                            <div class="col-md-2">
                                                                <span class="btn btn-sm btn-primary addelementrow"><i class=" glyphicon glyphicon-plus"></i></span>

                                                                <span class="btn btn-sm btn-primary removeelemenrow"><i class=" glyphicon glyphicon-minus"></i></span>
                                                            </div>
                                                        </div>
                                                    </c:forEach>                                                    
                                                </c:otherwise>
                                            </c:choose>                                            
                                        </div>
                                        <div class="row form-row">
                                            <label for="program" class="col-md-2 col-md-offset-2 control-label">!annotation content</label>
                                            <div class="col-md-6">
                                                <form:input path="annotationContent" cssClass="form-control" />
                                            </div>                                       
                                        </div>

                                        <div class="row form-row">
                                            <label for="program" class="col-md-2 col-md-offset-2 control-label">!formula content</label>
                                            <div class="col-md-6">
                                                <form:input path="formulaContent" cssClass="form-control" />
                                            </div>                                       
                                        </div>
                                        <div class="row form-row">
                                            <label for="program" class="col-md-2 col-md-offset-2 control-label">!Canonic outputs</label>
                                            <div class="col-md-6">
                                                <form:input path="coRuns" cssClass="form-control" />
                                            </div>                                       
                                        </div>
                                        <button type="submit" class="btn btn-primary"><spring:message code="general.label.search" /></button>
                                    </form:form>
                                    <div class="hidden" id="elementRowTemplate">
                                        <div class="row form-row">
                                            <label for="sourceDocument" class="col-md-2 col-md-offset-2 control-label">!elements</label>
                                            <div class="col-md-3">
                                                <select name="elementRows[X].element" class="form-control">
                                                    <c:forEach items="${elementList}" var="entry">
                                                        <option value="<c:out value="${entry.id}" />"><c:out value="${entry.elementName}" /></option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <label for="sourceDocument" class="col-md-1 control-label">!pocet</label> 
                                            <div class="col-md-2">
                                                <input type="text" class="form-control" name="elementRows[X].value"/>
                                            </div>
                                            <div class="col-md-2">
                                                <span class="btn btn-sm btn-primary addelementrow"><i class=" glyphicon glyphicon-plus"></i></span>

                                                <span class="btn btn-sm btn-primary removeelemenrow"><i class=" glyphicon glyphicon-minus"></i></span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>                                        
                </div>
            </div>
            <div class="row pull-top-50">
                <div class="col-md-12">
                    <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                        <div class="pull-right">
                            <a href="${pageContext.request.contextPath}/formula/massdelete/" class="btn btn-danger"><spring:message code="general.label.massdelete" /></a>
                        </div>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_USER')">
                        <div class="pull-right">
                            <a href="${pageContext.request.contextPath}/appruns/create/select/" class="btn btn-warning"><spring:message code="entity.formula.canonicalizer.run" /></a>
                        </div>
                    </sec:authorize>
                </div>
            </div>
            <div class="row">
                <c:choose>
                    <c:when test="${fn:length(formulaList) gt 0}">
                        <c:forEach items="${formulaList}" var="entry" varStatus="i">
                            <c:if test="${not i.first and i.index % 4 == 0}">
                            </div>
                            <div class="row">
                            </c:if>
                            <div class="col-md-3 img-thumbnail">
                                <a href="${pageContext.request.contextPath}/formula/view/${entry.id}">
                                    <div class="preview text-center">
                                        <c:out value="${entry.xml}" escapeXml="false" />
                                    </div>
                                </a>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="col-md-12 text-center">
                            <h2 class="text-center"><spring:message code="general.table.norecords" /></h2>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
            <!-- pagination -->
            <div class="text-center"> 
                <form:form method="get" action="${pageContext.request.contextPath}/formula/list/" commandName="pagination">
                    <ul class="pager">
                        <c:if test="${pagination.pageSize != pagination.defaultPageSize}">
                            <c:set var="size" value="&pageSize=${pagination.pageSize}" />
                            <form:hidden path="pageSize" />
                        </c:if>
                        <li><a href="?pageNumber=1${size}">&laquo;</a></li>
                            <c:choose>
                                <c:when test="${pagination.pageNumber == 1}">
                                <li class="disabled"><a href="javascript:;">&lsaquo;</a></li>
                                </c:when>
                                <c:otherwise>
                                <li><a href="?pageNumber=${pagination.pageNumber - 1}${size}">&lsaquo;</a></li>
                                </c:otherwise>
                            </c:choose>
                        <li><form:input type="text" path="pageNumber" size="2" /></li>
                        <li><button type="submit" class="btn btn-primary btn-sm"><spring:message code="pagination.go" /></button></li>
                        <li>/ ${pagination.pages}</li>
                            <c:choose>
                                <c:when test="${pagination.pageNumber == pagination.pages}">
                                <li class="disabled"><a href="javascript:;">&rsaquo;</a></li>
                                </c:when>
                                <c:otherwise>
                                <li><a href="?pageNumber=${pagination.pageNumber + 1}${size}">&rsaquo;</a></li>
                                </c:otherwise>
                            </c:choose>
                        <li><a href="?pageNumber=${pagination.pages}${size}">&raquo;</a></li>
                    </ul>
                </form:form>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>
