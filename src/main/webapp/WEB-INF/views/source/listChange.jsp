<%@ include file="/WEB-INF/views/common/include/taglib.jspf"%>
<script type="text/javascript">
	$(function() {
		$("#sel_repository").val('<c:out value="${repositoryKey}" />');
		retrieveRepositoryChangeList();
		$("#sel_repository").change(changeRepository);
	});
	
	function changeRepository(){
		frm_repository.action = "<c:url value="/source/changes"/>" + "/" + $("#sel_repository > option:selected").val();
		frm_repository.submit();
	};
	
	function retrieveRepositoryChangeList(){
		//$("#tbl_changeList tbody tr:not(.sample)").remove();
		$("#tbl_changeList tfoot span:not(.loading)").removeClass('display-none').addClass('display-none');
		$("#tbl_changeList tfoot span.loader").removeClass('display-none');
		_paging.path = '<c:out value="${path}" />';
		_paging.repositoryKey = $("#sel_repository > option:selected").val();
		$.getJSON( "<c:url value="/source/changes/list"/>",
				_paging,
				function(data) {
					var model = data.model;
					_paging.start = data.end;
					var repositoryKey = '<c:out value="${repositoryKey}" />';
					var hrefRoot = '<c:url value="/source/changes"/>';
					var path = '<c:out value="${path}" />';
					for( var inx = 0 ; inx < model.length ; inx++ ){
						var row = $("#tbl_changeList > tbody > .sample").clone();
						$(row).find(".revision a").attr('href',(hrefRoot + "/" + repositoryKey + (path.length<1?"":"/") + path + "?rev=" + model[inx].revision).replace("//", "/"));
						$(row).find(".revision font a").text('r'+model[inx].revision);
						$(row).children(".message").text(model[inx].message);
						$(row).children(".date").text(haksvn.date.convertToEasyFormat(new Date(model[inx].date)));
						$(row).children(".author").text(model[inx].author);
						if( model[inx].reviewSummarySimple.isReviewed){
							var totalScore = model[inx].reviewSummarySimple.totalScore;
							var isPositive = totalScore > 0;
							$(row).children(".score").text((isPositive?"+":"")+totalScore)
								.removeClass("neutral").addClass(isPositive?"positive":"negative");	
						}
						$(row).removeClass("sample");
						$('#tbl_changeList > tbody').append(row);
					}
					
					if( model.length < 1 ){
						$("#tbl_changeList tfoot span:not(.nodata)").removeClass('display-none').addClass('display-none');
						$("#tbl_changeList tfoot span.nodata").removeClass('display-none');
					}else if( !data.hasNext ){
						$("#tbl_changeList tfoot").removeClass('display-none').addClass('display-none');
					}else{
						$("#tbl_changeList tfoot span:not(.showmore)").removeClass('display-none').addClass('display-none');
						$("#tbl_changeList tfoot span.showmore").removeClass('display-none');
					}
		});
	};
	
	var _paging = {start:-1,end:-1,limit:50};
</script>
<form id="frm_repository" action=""></form>
<div class="content-page">
	<h1></h1>
	<div class="col w10 last">
		<div class="content">
			<div class="box">
				<div class="head"><div></div></div>
				<div class="desc">
					<p>
						<label>Repository Name</label> 
						<select id="sel_repository">
							<c:forEach items="${repositoryList}" var="repository">
								<option value="<c:out value="${repository.repositoryKey}"/>">
									<c:out value="${repository.repositoryName}" />
								</option>
							</c:forEach>
						</select>
					</p>
				</div>
				<div class="bottom"><div></div></div>
			</div>
			<div>
				<p>
					<font class="path">Path:
						<c:set var="repoChangesPathLink" value="${pageContext.request.contextPath}/source/changes/${repositoryKey}"/>
						<c:set var="repoBrowsePathLink" value="${pageContext.request.contextPath}/source/browse/${repositoryKey}"/>
						/<a href="${repoChangesPathLink}">[SVN root]</a>
						<c:forEach var="pathFrag" items="${fn:split(path, '/')}" varStatus="loop">
							<c:set var="repoChangesPathLink" value="${repoChangesPathLink}/${pathFrag}"/>
							<c:set var="repoBrowsePathLink" value="${repoBrowsePathLink}/${pathFrag}"/>
							<c:choose>
								<c:when test="${!loop.last}">/<a href="${repoChangesPathLink}"><c:out value="${pathFrag}" /></a></c:when>
								<c:when test="${(loop.last) && (svnSource.isFolder)}">
									<c:out value="${fn:length(path) > 0 ? '/':''}" /><a href="${repoChangesPathLink}"><c:out value="${pathFrag}" /></a>
								</c:when>
								<c:otherwise>
									/<c:out value="${pathFrag}" />&nbsp;<span class="italic"><a href="${repoBrowsePathLink}?rev=-1">(view source)</a></span>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</font>
					<c:if test="${svnSource.isFolder}">
						<select id="sel_lowerSvnSource">
							<option value="">/</option>
							<c:forEach items="${lowerSvnSourceList}" var="lowerSvnSource">
								<option value="<c:out value="${lowerSvnSource.title}"/>">
									/<c:out value="${lowerSvnSource.title}" />
								</option>
							</c:forEach>
						</select>
						<script type="text/javascript">
							$("#sel_lowerSvnSource").change(function(){
								haksvn.block.on();
								location.href = ("<c:out value="${repoChangesPathLink}/" />" + $(this).val()).replace("//", "/");
							});
						</script>
					</c:if>
				</p>
			</div>
			<table id="tbl_changeList">
				<thead>
					<tr>
						<th width="100px">Revision</th>
						<th>Commit Log Message</th>
						<th width="100px">Date</th>
						<th width="100px">Author</th>
						<th class="score">Score</th>
					</tr>
				</thead>
				<tbody>
					<tr class="sample">
						<td class="revision">
							<font class="path font12 open-window"><a href=""></a></font>
						</td>
						<td class="message"></td>
						<td class="date"></td>
						<td class="author"></td>
						<td class="score neutral">N/A</td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="5" style="text-align:center;">
							<span class="showmore display-none"><font class="path"><a onclick="retrieveRepositoryChangeList()">Show More</a></font></span>
							<span class="loader display-none"><img src="<c:url value="/images/ajax-loader.gif"/>" /></span>
							<span class="nodata">no data</span>
						</td>
					</tr>
				</tfoot>
			</table>
			
		</div>
	</div>
	
	<div class="clear"></div>
</div>
