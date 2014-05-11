<%@ page import="java.util.*"%>
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
<script type="text/javascript">
	function getWoeid(place) {
		//	place = place.replace(" ","+");
		//	place = place.replace(",","%2C");
		//  alert(place);
		var flickerAPI = "https://api.flickr.com/services/rest/?method=flickr.places.find&api_key=fe15908575c4ea9a4d3fa057dabe9338&query="
				+ place + "&format=json&nojsoncallback=1";
		//	alert(flickerAPI); 
		//var flickerAPI = "http://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=90b4115a0effac6460969450ab23bb4&tags=yokota+air+base&safe_search=1&per_page=20";
		$.getJSON(flickerAPI, function(data) {

		}).done(function(data) {
			//	    	 alert(JSON.stringify(data));
			$.each(data.places.place, function(i, item) {
				//	    		 alert(item.woeid);
				getPics(item.woeid,place);
			});
		});
	}

	function getPics(woeid,place) {
		//var flickerAPI = "http://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=fe15908575c4ea9a4d3fa057dabe9338&format=json&location="+woeid;
		var flickerAPI = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=fe15908575c4ea9a4d3fa057dabe9338"+
				"&woe_id="+ woeid +
				"&format=json&nojsoncallback=1"+
				"tags="+place+
				"per_page=5";
		$.getJSON(flickerAPI, function(data) {

		}).done(
				function(data) {
//					alert(JSON.stringify(data));
					$.each(data.photos.photo, function(i, item) {
						var url = "http://farm" + item.farm
								+ ".staticflickr.com/" + item.server + "/"
								+ item.id + "_" + item.secret + ".jpg";
						//	    		alert(url);
						$("<img height=150px>").attr("src", url).appendTo(
								"#images");
						$("<span>"+place+"</span>").appendTo(
						"#images");
					});
				});
	}
</script>
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
					<button id="find" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-search"></span>
					</button>
				</form>
			</div>
		</div>
		<!-- /.container-fluid -->
	</nav>
	<div id="map-canvas"></div>
	<div id="results">
		<script language="javascript">
			function bustOut(place) {
				getWoeid(place);
			}
		<%List<String> nameList = (List<String>) request.getAttribute("Places");
					for(int i=0;i<nameList.size();i++){
					String place = nameList.get(i);
					out.print("bustOut(\""+place+"\");"); 
					}%>
			
		</script>
	</div>
	<div id="images"></div>
	<div id="places"></div>
</body>
</html>