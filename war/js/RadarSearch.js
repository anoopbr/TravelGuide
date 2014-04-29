var map, service;
var arr = [];
function googleRadarSearch(lat, lng) {
	// alert(lat + " " + lng);
	var pyrmont = new google.maps.LatLng(lat, lng);

	map = new google.maps.Map(document.getElementById('map-canvas'), {
		center : pyrmont,
		zoom : 15
	});
	var request = {
		location : pyrmont,
		radius : 50000,
		types : [ "art_gallery", "amusement_park", "aquarium", "zoo",
				"campground", "casino", "cemetery", "church", "city_hall",
				"courthouse", "hindu_temple", "mosque", "museum",
				"place_of_worship", "rv_park", "stadium", "shopping_mall",
				"synagogue", "university" ]
	};
	infowindow = new google.maps.InfoWindow();
	service = new google.maps.places.PlacesService(map);
	service.radarSearch(request, callback);

	// alert(lat + " " + lng);
	// var APIKey = "AIzaSyDJC_nu-IQJGFOiHMLH6Rafb_yzemvVAsw"
	// var url =
	// "https://maps.googleapis.com/maps/api/place/radarsearch/json?location="
	// + lat
	// + ","
	// + lng
	// + "&radius=50000&types=museum&sensor=false&key="
	// + APIKey;
	// alert(url);
	// $.getJSON(url, function() {
	// alert( "success" );
	// }).done(function(data) {
	// alert("done");
	// alert(JSON.stringify(data));
	// $.each(data.items, function(i, item) {
	// // $( "<img>" ).attr( "src", item.media.m ).appendTo( "#images" );
	// });
	// }).fail(function(jqxhr, textStatus, error) {
	// var err = textStatus + ", " + error;
	// alert("Request Failed: " + err);
	// }).always(function(data) {
	// alert(JSON.stringify(data));
	// alert("complete");
	// });

	for (var i = 10; i < arr.length; i++) {
		$("#places").append("<p>"+i+" || "+JSON.stringify(arr[i])+"</p>");
		getPlaceDetails(arr[i]);
	}
}

function callback(results, status) {
	if (status == google.maps.places.PlacesServiceStatus.OK) {
		for (var i = 0; i < results.length; i++) {
			// $("#places").append("<p>"+results[i].reference+"</p>");
			// alert(results[i].reference);
			arr.push(results[i]);
		}
	} else {
		alert(status);
	}
}

function getPlaceDetails(request) {
	service.getDetails(request, function(place, status) {
		if (status == google.maps.places.PlacesServiceStatus.OK) {
			// $("#places").append("<p>"+request.reference+"</p>");
			// $("#images").append(JSON.stringify(place));
		} else {
//			$("#images").append("<p>In else</p>");
			alert(status);
		}
		var html = "<div class='destinaton-container'>";
		html = html + "<img class='destinaton-type-icon' src='"
				+ place.icon + "' />";
		html = html + "<a class='destinaton-name' href='" + place.website
				+ "'>" + place.name + "</a>"
		html = html + "<span class='destinaton-rating'>" + place.rating
				+ "</span>"
		html = html + "</div>";
		$("#results").append(html);
		var marker = new google.maps.Marker({
			map : map,
			position : place.geometry.location
		});
	});
}