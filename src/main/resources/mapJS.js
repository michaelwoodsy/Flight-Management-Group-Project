//Embedded javascript for google maps API
    var map;
    var path;
    var marker_icon = 'airportIcon.png';

/*
For the line connecting the two airports, path co-ordinates are needed. If we have for information
about the path of the flight, we can input the long and lat data into flightPlanCoordinates to show the curve of
the flight rather than a direct line connecting them
*/


//// lat and long for source and dest go in here
//    var dest1 = [-43.4876, 172.5374]
//    var dest2 = [-37.0082, 174.7850]
//    var flightPlanCoordinates = [
//            {lat: dest1[0], lng: dest1[1]},
//            {lat: dest2[0], lng: dest2[1]}
//        ];
//
//    const lineSymbol = {
//        path: "M 0,-1 0,1",
//        strokeOpacity: 1,
//        scale: 4
//  };



    function initMap() {
        map = new google.maps.Map(document.getElementById('map'), {

            center: new google.maps.LatLng(-41.2985, 173.2441),
            zoom: 5.1,
            zoomControl: false,
            mapTypeControl: false,
            scaleControl: false,
            streetViewControl: false,
            rotateControl: false,
            fullscreenControl: false
        });

        var infoWindow = new google.maps.InfoWindow();
        var latlngbounds = new google.maps.LatLngBounds();

    }

    function displayRoute(flightPath) {

        if (flightPath.length < 2) {
            return;
        }
//        for (var i = 0; i < flightPath.length; i++) {
//            var data = flightPath[i]
//            var Latlng = new google.maps.LatLng(data.lat, data.lng);
//            var marker = new google.maps.Marker({
//                position: Latlng,
//                map: map,
//                title: data.title,
//                icon: marker_icon
//            });
//        }

        var markers = flightPath.map(function(location, i) {
          return new google.maps.Marker({
            position: location,
            icon: marker_icon
          });
        });

        // Add a marker clusterer to manage the markers.
        var markerCluster = new MarkerClusterer(map, markers,
            {imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});

    }