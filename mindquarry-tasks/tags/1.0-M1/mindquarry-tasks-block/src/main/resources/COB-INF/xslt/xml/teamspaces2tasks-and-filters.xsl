<?xml version="1.0" encoding="UTF-8"?>
<!--
	Copyright (C) 2006-2007 Mindquarry GmbH, All Rights Reserved
	
	The contents of this file are subject to the Mozilla Public License
	Version 1.1 (the "License"); you may not use this file except in
	compliance with the License. You may obtain a copy of the License at
	http://www.mozilla.org/MPL/
	
	Software distributed under the License is distributed on an "AS IS"
	basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
	License for the specific language governing rights and limitations
	under the License.
-->
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xlink="http://www.w3.org/1999/xlink"
	xmlns:xi="http://www.w3.org/2001/XInclude">

	<xsl:param name="base" />

	<!-- This stylesheet will replace the root element
		named "teamspaces" with a new root element "tasks"
		and add includes for the list of tasks per teamspace -->

	<xsl:template match="/teamspaces">
		<tasks xml:base="{$base}">
			<xsl:apply-templates select="teamspace" />
		</tasks>
	</xsl:template>

	<xsl:template match="teamspace">
		<teamspace xlink:href="{normalize-space(id)}">
			<name><xsl:value-of select="name" /></name>
			<description><xsl:value-of select="description" /></description>
			<xi:include href="cocoon:/internal/pipe/tasks/{normalize-space(id)}/list.xml" />
			<xi:include href="cocoon:/internal/pipe/filters/{normalize-space(id)}/list.xml" />
		</teamspace>
	</xsl:template>

</xsl:stylesheet>