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
	xmlns:i="http://apache.org/cocoon/include/1.0"
	xmlns:collection="http://apache.org/cocoon/collection/1.0">

	<xsl:template match="/">
		<xsl:element name="resources">
			<xsl:apply-templates />
		</xsl:element>
	</xsl:template>	

	<!-- convert all file entries into an include statement for the content of that file -->
	<xsl:template match="collection:resource">
		<!-- <xi:include href="servlet:/{$path}/{@name}.model" /> -->
		<i:include src="servlet:/resource/{../@name}/{@name}" />
	</xsl:template>

</xsl:stylesheet>