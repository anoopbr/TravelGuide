<!DOCTYPE html>
<html>
<head>
<title>Visual Travelogue</title>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
<meta charset="utf-8">
<style>
html,body {
	height: 100%;
	margin: 0px;
	padding: 0px
}
</style>
<link href="bootstrap-3.1.1-dist/css/bootstrap.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<link type="text/css" rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Roboto:300,400,500">
<script
	src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=places"></script>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="js/Autocomplete.js"></script>
<script src="js/RadarSearch.js"></script>

</head>
<body onload="initialize()">

	<nav class="navbar navbar-inverse" role="navigation">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/">Visual Travelogue</a>
			</div>
			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<form class="navbar-form navbar-right" role="search" method="POST"
					action="/travelguide">
					<div class="form-group">
						<input id="autocomplete" type="text"
							placeholder="Enter place name" onFocus="geolocate()"
							class="form-control" size=60 name="keyword" />
					</div>
					<button id="find" type="button" class="btn btn-default"><span class="glyphicon glyphicon-search"></span></button>
				</form>
			</div>
		</div>
		<!-- /.container-fluid -->
	</nav>
<div id="map-canvas"></div>
<div id="results"> </div>
<div id="images"> </div>
<div id="places"> </div>
</body>
</html>