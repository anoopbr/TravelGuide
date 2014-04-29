var placeSearch, autocomplete;

function initialize() {
	// Create the autocomplete object, restricting the search
	// to geographical location types.
	
	autocomplete = new google.maps.places.Autocomplete(
	/** @type {HTMLInputElement} */
	(document.getElementById('autocomplete')), {
		types : [ 'geocode' ]
	});
	// When the user selects an address from the dropdown,
	// populate the address fields in the form.
	google.maps.event.addListener(autocomplete, 'place_changed', function() {
		var place = autocomplete.getPlace();
//		var myStringArray = ["art_gallery", "amusement_park","aquarium","zoo","campground"];
//		for(var i in myStringArray)
//		{
//		googleRadarSearch(place.geometry.location.lat(),
//				place.geometry.location.lng(),myStringArray[i]);
//		}
		
		var element;
		element = document.getElementById("results");
		if (element) {
		    element.innerHTML = "";
		}
		element = document.getElementById("places");
		if (element) {
		    element.innerHTML = "";
		}
//		alert(place.geometry.location.lat()+"|"+place.geometry.location.lng());
		googleRadarSearch(place.geometry.location.lat(),
				place.geometry.location.lng());
	});
}

// [START region_fillform]
function fillInAddress() {
	// Get the place details from the autocomplete object.
	var place = autocomplete.getPlace();

	for ( var component in componentForm) {
		document.getElementById(component).value = '';
		document.getElementById(component).disabled = false;
	}

	// Get each component of the address from the place details
	// and fill the corresponding field on the form.
	for (var i = 0; i < place.address_components.length; i++) {
		var addressType = place.address_components[i].types[0];
		if (componentForm[addressType]) {
			var val = place.address_components[i][componentForm[addressType]];
			document.getElementById(addressType).value = val;
		}
	}
}
// [END region_fillform]

// [START region_geolocation]
// Bias the autocomplete object to the user's geographical location,
// as supplied by the browser's 'navigator.geolocation' object.
function geolocate() {
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(function(position) {
			var geolocation = new google.maps.LatLng(position.coords.latitude,
					position.coords.longitude);
			autocomplete.setBounds(new google.maps.LatLngBounds(geolocation,
					geolocation));
		});
	}
}

