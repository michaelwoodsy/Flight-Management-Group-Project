//Embedded javascript for google maps API
    var map;
    var path;
    var marker_icon = 'airporticon.png';

    function initMap() {
    //basic map set-up, disabling some map features and setting default map position
        map = new google.maps.Map(document.getElementById('map'), {
            center: new google.maps.LatLng(15.0000, 156.681448),
            zoom: 1,
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

    function clearMap() {
    //re-initialise map
      initMap();
    }

    function displayAirport(flightPath) {
    //places a marker on map, based on airport lat and long.
        var marker = null;
        var markers = flightPath.map(function(location) {
            alert(location);
            marker = new google.maps.Marker({
            position: location,
            title: location.title,
            icon: marker_icon
          });
          return marker;
        });

        // Add a marker clustering to avoid overcrowding map.
        var markerCluster = new MarkerClusterer(map, markers,
            {imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});
    }

    function drawRoute(flightCoordinates) {
    //draws a line connecting dest and source airport
        const routeCoords = new google.maps.Polyline({
            path: flightCoordinates,
            geodesic: true,
            strokeColor: "#FF0000",
            strokeOpacity: 1.0,
            strokeWeight: 2,
          });
        routeCoords.setMap(map);
    }