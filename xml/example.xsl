<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="UTF-8"/>

<xsl:template match="message">
<div class="message">
	<div class="date"><xsl:value-of select="@date"/></div>
	<div><b>From</b>: <xsl:value-of select="from"/></div>
	<div><b>To</b>: <xsl:value-of select="to"/></div>
	<div><b>Subject</b>: <xsl:value-of select="subject"/></div>
	<div><xsl:value-of select="text"/></div>
</div>
</xsl:template>

<xsl:template match="mailbox">
	<html>
	<head>
		<title>Mailbox</title>
		<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
		<link rel="stylesheet" href="style.css"/>
	</head>
	<body>
		<h2>Mailbox</h2>
		<xsl:apply-templates select="message"/>
		</body>
	</html>
</xsl:template>
</xsl:stylesheet>