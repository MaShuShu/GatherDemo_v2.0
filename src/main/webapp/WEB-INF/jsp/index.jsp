<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../jsp/base/web.jsp"%>
<title>黑 &amp; 白</title>
<!-- meta -->
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body>
	<div class="container">
		<header id="site-header">
			<div class="row">
				<div class="col-md-4 col-sm-5 col-xs-8">
					<div class="logo">
						<h1>
							<a href="index.html"><b>黑</b> &amp; 白</a>
						</h1>
					</div>
				</div>
				<!-- col-md-4 -->
				<div class="col-md-8 col-sm-7 col-xs-4">
					<nav class="main-nav" role="navigation">
						<div class="navbar-header">
							<button type="button" id="trigger-overlay" class="navbar-toggle">
								<span class="ion-navicon"></span>
							</button>
						</div>

						<div class="collapse navbar-collapse"
							id="bs-example-navbar-collapse-1">
							<ul class="nav navbar-nav navbar-right">
								<li class="cl-effect-11"><a href="index.html"
									data-hover="Home">Home</a></li>
								<li class="cl-effect-11"><a href="full-width.html"
									data-hover="Blog">Blog</a></li>
								<li class="cl-effect-11"><a href="about.html"
									data-hover="About">About</a></li>
								<li class="cl-effect-11"><a href="<%=$root %>/contact.do"
									data-hover="Publications">Publications</a></li>
							</ul>
						</div>
						<!-- /.navbar-collapse -->
					</nav>
					<div id="header-search-box">
						<a id="search-menu" href="#"><span id="search-icon"
							class="ion-ios-search-strong"></span></a>
						<div id="search-form" class="search-form">
							<form role="search" method="get" id="searchform" action="#">
								<input type="search" placeholder="Search" required>
								<button type="submit">
									<span class="ion-ios-search-strong"></span>
								</button>
							</form>
						</div>
					</div>
				</div>
				<!-- col-md-8 -->
			</div>
		</header>
	</div>
	<div class="copyrights">
		Collect from <a href="http://www.cssmoban.com/">网页模板</a>
	</div>

	<div class="content-body">
		<div class="container">
			<div class="row">
				<main class="col-md-8">
				<c:set var="end" value="..."></c:set>
				<c:forEach var="blog" items="${blog }" varStatus="status">
					<article class="post post-"+${status.index + 1}>
						<header class="entry-header">
						<h1 class="entry-title">
							<a href="<%=$root %>/${blog.id}">${blog.title }</a>
						</h1>
						<div class="entry-meta">
							<span class="post-category"><a href="#">${blog.username }</a></span>
							<span class="post-date"><a href="#"><time class="entry-date" datetime="${blog.createtime }">${blog.createtime }</time></a></span>
							<span class="post-author"><a href="#">${blog.groupname }</a></span>
							<span class="comments-link"><a href="#">${blog.tag }</a></span>
						</div>
						<div class="entry-content clearfix">
							<p>${fn:length(blog.context)<500?blog.context:fn:substring(blog.context,0,500)}</p>
							<div class="read-more cl-effect-14">
							<a href="#" class="more-link">Continue reading <span
								class="meta-nav">→</span></a>
						</div>
						</div>
					</article>
				</c:forEach>
				
				<div class="read-more cl-effect-14" style="border:1px #cccccc solid; background-color:#cccccc;">
					<a href="#" class="more-link">More<span class="meta-nav">→</span></a>
				</div>
				
				</main>
				<aside class="col-md-4">
					<div class="widget widget-recent-posts">
						<h3 class="widget-title">Recent Posts</h3>
						<ul>
							<li><a href="#">Adaptive Vs. Responsive Layouts And
									Optimal Text Readability</a></li>
							<li><a href="#">Web Design is 95% Typography</a></li>
							<li><a href="#">Paper by FiftyThree</a></li>
						</ul>
					</div>
					<div class="widget widget-archives">
						<h3 class="widget-title">Archives</h3>
						<ul>
							<li><a href="#">November 2014</a></li>
							<li><a href="#">September 2014</a></li>
							<li><a href="#">January 2013</a></li>
						</ul>
					</div>

					<div class="widget widget-category">
						<h3 class="widget-title">Category</h3>
						<ul>
							<li><a href="#">Web Design</a></li>
							<li><a href="#">Web Development</a></li>
							<li><a href="#">SEO</a></li>
						</ul>
					</div>
				</aside>
			</div>
		</div>
	</div>
	<footer id="site-footer">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<p class="copyright">
						&copy; 2014 ThemeWagon.com -More Templates <a
							href="http://www.cssmoban.com/" target="_blank" title="模板之家">模板之家</a>
						- Collect from <a href="http://www.cssmoban.com/" title="网页模板" target="_blank">网页模板</a>
						<shiro:user><a href="<%=$root %>/sys/index.do"> - 后台管理</a></shiro:user>
					</p>
				</div>
			</div>
		</div>
	</footer>

	<!-- Mobile Menu -->
	<div class="overlay overlay-hugeinc">
		<button type="button" class="overlay-close">
			<span class="ion-ios-close-empty"></span>
		</button>
		<nav>
			<ul>
				<li><a href="<%=$root %>/">首页</a></li>
				<li><a href="full-width.html">博客</a></li>
				<li><a href="about.html">关于</a></li>
				<li><a href="<%=$root %>/contact.do">发布博客</a></li>
			</ul>
		</nav>
	</div>
	<script src="<%=$root %>/js/blog/script.js"></script>
</body>
</html>