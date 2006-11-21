<?xml version="1.0"?>
<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:fi="http://apache.org/cocoon/forms/1.0#instance"
	exclude-result-prefixes="fi">

  <xsl:import href="block:resources:/xslt/contextpath.xsl" />

  <!-- resources directory for Dojo js, css and the like -->
  <xsl:param name="resources-uri">
		<xsl:value-of select="$pathToRoot" />
		<xsl:text>resources/_cocoon/resources</xsl:text>
  </xsl:param> 
 
  <!-- styling of the page -->
  <xsl:include href="resource://org/apache/cocoon/forms/resources/forms-page-styling.xsl"/>
  <!-- styling of the widgets -->
  
  <xsl:include href="resource://org/apache/cocoon/forms/resources/forms-advanced-field-styling.xsl"/>

  <!--  styling of dojo editor (from lenya) -->
  <xsl:include href="dojo-editor.xsl"/>
  <!--  styling of dojo span  -->
  <xsl:include href="dojo-span.xsl" />
  <!-- styling of toggle buttons -->
  <xsl:include href="dojo-buttons.xsl" /> 
  <!-- styling of calendar -->
  <xsl:include href="dojo-calendar.xsl" />
 
  <xsl:template match="head">
  	<xsl:copy>
      <xsl:apply-templates select="." mode="forms-page"/>
      <xsl:apply-templates select="." mode="forms-field-mindquarry"/>
      <!-- script type="text/javascript" src="{$pathToBlock}scripts/dforms.js">//</script-->
      <xsl:apply-templates select="." mode="forms-dojoarea"/>
      <xsl:apply-templates select="." mode="forms-dojospan"/>
      <xsl:apply-templates select="." mode="forms-dojobuttons"/>
      <xsl:apply-templates select="." mode="forms-dojocalendar"/>
      <xsl:apply-templates/>
	</xsl:copy>
  </xsl:template>
  
  <xsl:template match="head" mode="forms-field-mindquarry">
    <!-- copy any pre-initialization code which can be used e.g. to setup dojo debugging with
         <script> djConfig = {isDebug: true} </script> -->
    <xsl:copy-of select="fi:init/node()"/>
    <script src="{$resources-uri}/ajax/cocoon.js" type="text/javascript"/>
    <script src="{$resources-uri}/forms/js/forms-lib.js" type="text/javascript"/>
    <script type="text/javascript">
        dojo.addOnLoad(forms_onload);
        dojo.require("cocoon.forms.*");
    </script> 
    <script src="{$pathToRoot}/resources/scripts/lightbox.js" />
    <link rel="stylesheet" type="text/css" href="{$pathToRoot}/resources/css/lightbox.css" />
    
    <!-- <script src="{$resources-uri}/forms/mattkruse-lib/AnchorPosition.js" type="text/javascript"/> -->
    <!-- <script src="{$resources-uri}/forms/mattkruse-lib/PopupWindow.js" type="text/javascript"/>-->
    <!-- script src="{$resources-uri}/forms/mattkruse-lib/OptionTransfer.js" type="text/javascript"/-->
    <!-- script src="{$resources-uri}/forms/mattkruse-lib/selectbox.js" type="text/javascript"/-->
    <!-- xsl:apply-templates select="." mode="forms-calendar"/-->
    <!-- xsl:apply-templates select="." mode="forms-htmlarea"/-->
    
    <!-- googlemap-key -->
    <xsl:if test="/*/fi:googlemap">
      <script src="/*/fi:googlemap/fi:key" type="text/javascript"/>
    </xsl:if>

    
    <link rel="stylesheet" type="text/css" href="{$resources-uri}/forms/css/forms.css"/>
  </xsl:template>

  <xsl:template match="body">
  	<xsl:copy>
      <xsl:apply-templates select="." mode="forms-page"/>
      <xsl:apply-templates select="." mode="forms-field"/>
      <xsl:apply-templates/> 
      </xsl:copy>
  </xsl:template>
  
  <!-- put the required ductform field title into the html title -->
  <xsl:template match="title">
  	<title>
  		<xsl:value-of select="/html/body//fi:field[@id='ductform.title']/fi:value" />
  	</title>
  </xsl:template>
  
  <!-- remove invisible stuff (non-selected fields) -->
  <xsl:template match="div[@class='form_block'][not(fi:*)]" />
  
  
  <xsl:template match="fi:group[fi:styling/@layout='default']" mode="group-layout">
  	<dl title="{normalize-space(fi:hint)}" class="ductform">
  		<xsl:apply-templates select="fi:items/*" mode="default" />
  	</dl>
  </xsl:template>
  
  <xsl:template match="fi:items//fi:action[@state='output']"></xsl:template>
  
  <xsl:template match="fi:*|fi:items/*" mode="default">
    <dt><xsl:apply-templates select="." mode="label"/></dt>
    <dd>
    	<xsl:apply-templates select="." />
    </dd>
  </xsl:template>
  
  <xsl:template match="node() | @*" mode="default">
    <xsl:copy>
      <xsl:apply-templates select="node() | @*" mode="default"/>
    </xsl:copy>
  </xsl:template>
  
  
  <xsl:template match="fi:field[fi:styling/@styling='link']">
  	<a href="{normalize-space(fi:value)}">
  		<xsl:apply-templates select="following-sibling::fi:field[fi:styling/@styling='linkcontent']" mode="value" />
  	</a>
  </xsl:template>
  
  <xsl:template match="fi:field[fi:styling/@styling='linkcontent']"/>
  
  <xsl:template match="fi:field[fi:styling/@styling='linkcontent']" mode="value">
  	<span id="{@id}"><xsl:value-of select="fi:value" /></span>
  </xsl:template>
  
 <xsl:template match="fi:help">
    <xsl:variable name="id" select="concat(../@id, ':help')"/>
    <div class="forms-help forms help" id="{$id}" style="display:none">
    	<xsl:apply-templates select="node()"/>
    </div>
    <a id="{$id}:a" href="#{$id}" rel="lightbox" class="help">
      <!-- TODO: i18n key for helppopup -->
      <img src="{$pathToRoot}resources/icons/16x16/apps/help-browser.png" alt="helppopup"/>
    </a>
  </xsl:template>
  
  <xsl:template match="fi:items/fi:label"/>
  
  <xsl:template match="fi:items/*" mode="label">
    <xsl:param name="id"/>
    
    <xsl:variable name="resolvedId">
      <xsl:choose>
        <xsl:when test="$id != ''"><xsl:value-of select="$id"/></xsl:when>
        <xsl:otherwise><xsl:value-of select="concat(@id, ':input')"/></xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    
    <label for="{$resolvedId}" title="{fi:hint}">
      <xsl:apply-templates select="." mode="css"/>
      <xsl:copy-of select="fi:label/node()"/>
    </label>
  </xsl:template>

</xsl:stylesheet>