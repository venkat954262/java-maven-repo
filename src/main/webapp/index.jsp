
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>3 Column Layout</title>
	<style type="text/css">

		/* Layout */
		body {
			min-width: 630px;
		}

		#container {
			padding-left: 200px;
			padding-right: 190px;
		}
		
		#container .column {
			position: relative;
			float: left;
		}
		
		#center {
			padding: 10px 20px;
			width: 100%;
		}
		
		#left {
			width: 180px;
			padding: 0 10px;
			right: 240px;
			margin-left: -100%;
		}
		
		#right {
			width: 130px;
			padding: 0 10px;
			margin-right: -100%;
		}
		
		#footer {
			clear: both;
		}
		
		/* IE hack */
		* html #left {
			left: 150px;
		}

		/* Make the columns the same height as each other */
		#container {
			overflow: hidden;
		}

		#container .column {
			padding-bottom: 1001em;
			margin-bottom: -1000em;
		}

		/* Fix for the footer */
		* html body {
			overflow: hidden;
		}
		
		* html #footer-wrapper {
			float: left;
			position: relative;
			width: 100%;
			padding-bottom: 10010px;
			margin-bottom: -10000px;
			background: #fff;
		}

		/* Aesthetics */
		body {
			margin: 0;
			padding: 0;
			font-family:Sans-serif;
			line-height: 1.5em;
		}
		
		p {
			color: #555;
		}

		nav ul {
			list-style-type: none;
			margin: 0;
			padding: 0;
		}
		
		nav ul a {
			color: darkgreen;
			text-decoration: none;
		}

		#header, #footer {
			font-size: large;
			padding: 0.3em;
			background: #BCCE98;
		}

		#left {
			background: #DAE9BC;
		}
		
		#right {
			background: #F7FDEB;
		}

		#center {
			background: #fff;
		}

		#container .column {
			padding-top: 1em;
		}
		
	</style>
	
</head>

<body>

	<header id="header"><p>Ratnam...</p></header>

	<div id="container">

		<main id="center" class="column">
			<article>
			
				<h1>Ratnam</h1>
				<p><script>generateText(50)</script></p>
			
			</article>								
		</main>

		<nav id="left" class="column">
			<h3>Ratnam</h3>
			<ul>
				<li><a href="#">Ratnam</a></li>
				
			</ul>
			<h3>Ratnam</h3>
			<ul>
				<li><a href="#">Ratnam</a></li>
				<li><a href="#">Ratnam</a></li>
				<li><a href="#">Ratnam</a></li>
				<li><a href="#">Ratnam</a></li>
				<li><a href="#">Ratnam</a></li>
			</ul>

		</nav>

		<div id="right" class="column">
			<h3>Ratnam</h3>
			<p><script>generateText(1)</script></p>
		</div>

	</div>

	<div id="footer-wrapper">
		<footer id="footer"><p>Ratnam...</p></footer>
	</div>

</body>

</html>