<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns="http://www.w3.org/1999/xhtml">
	
	<xsl:import href="contextpath.xsl"/>
	
	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
		</xsl:copy>
	</xsl:template>

	<xsl:template match="html">
		<html xmlns="http://www.w3.org/1999/xhtml">
			<xsl:apply-templates />	
		</html>
	</xsl:template>
	
	<xsl:template match="head">
		<head>
			<xsl:apply-templates select="*"/>
			<link rel="stylesheet" href="{$context.path}blocks/mindquarry-webapp-resources/resources/css/screen.css" media="screen,projection" type="text/css" />
		</head>
	</xsl:template>
	
	<xsl:template match="title">
		<title>Mindquarry: <xsl:value-of select="." /></title>
	</xsl:template>
	
	<xsl:template match="body">
		<body>
			<div class="body">
				<div id="webapp-header">
        				<ul id="webapp-sections">
						<li><a class="navTalk" href="{$context.path}">Talk</a></li>
						<li><a class="navTasks" href="{$context.path}">Tasks</a></li>
						<li><a class="navWiki" href="{$context.path}">Wiki</a></li>
						<li><a class="navFiles" href="{$context.path}blocks/mindquarry-workspace-block/">Files</a></li>
						<li><a class="navTeams" href="{$context.path}blocks/mindquarry-teamspace-block/">Teams</a></li>
					</ul>
				</div>
				<div id="webapp-content">
					<div id="background-repeater">
						<div id="background-lines">
							<div id="background-n">
								<div id="background-s">
									<div id="background-w">
										<div id="background-e">
											<div id="background-nw">
												<div id="background-ne">
													<div id="background-sw">
														<div id="background-se">
															<div id="innercontent">
															<xsl:apply-templates />
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div id="webapp-footer">
					<ul id="webapp-footer-sections">
						<li><a href="{$context.path}">Home</a></li>
						<li><a href="{$context.path}blocks/mindquarry-teamspace-block/">Teams</a></li>
						<li><a href="{$context.path}blocks/mindquarry-workspace-block/">Files</a></li>
						<li><a href="{$context.path}">Wiki</a></li>
						<li><a href="{$context.path}">Tasks</a></li>
						<li><a href="{$context.path}">Talk</a></li>
						<li><a href="{$context.path}/blocks/mindquarry-help-block/">Help</a></li>
						<li><a href="http://www.mindquarry.com">Visit Mindquarry.com</a></li>
						<li><a href="http://www.mindquarry.com/support/">Get Support</a></li>
					</ul>
				</div>
			</div>
		</body>
	</xsl:template>
	
</xsl:stylesheet>
