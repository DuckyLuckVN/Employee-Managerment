<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<footer class="footer">
	<div class="container-fluid">
		<nav class="float-left">
			<ul>
				<li>
					<a href="https://www.creative-tim.com">
						<s:message code="nav.bottom.item.introduct"/>
					</a>
				</li>
				<li>
					<a href="https://creative-tim.com/presentation">
						<s:message code="nav.bottom.item.guide"/>
					</a>
				</li>
				<li>
					<a href="http://blog.creative-tim.com">
						<s:message code="nav.bottom.item.contact"/>
					</a>
				</li>
			</ul>
		</nav>
		<div class="copyright float-right">
			©
			<script>
                document.write(new Date().getFullYear())
			</script> Created by <i class="fas fa-user-tie"></i>
			<a href="https://www.creative-tim.com" target="_blank">HaoPS07264</a> - JAVA 5
		</div>
	</div>
</footer>