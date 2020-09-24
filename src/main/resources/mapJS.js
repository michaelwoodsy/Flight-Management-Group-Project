//Embedded javascript for google maps API
    var map;

    var markers = [
    {
        "title": 'Christchurch International Airport',
        "lat": '-43.4876',
        "lng": '172.5374'
    },
    {
        "title": 'Auckland International Airport',
        "lat": '-37.0082',
        "lng": '174.7850',
    },
    {
        "title": 'Wellington International Airport',
        "lat": '-41.3276',
        "lng": '174.8076'
    },
    {
        "title": 'Invercargill airport',
        "lat": '-46.4153',
        "lng": '168.3151'
    },
    {
        "title": 'Tauranga Airport',
        "lat": '-37.6725',
        "lng": '176.1982'
    }
    ];

    var path;

    var marker_icon = 'airportIcon.png';

/*
For the line connecting the two airports, path co-ordinates are needed. If we have for information
about the path of the flight, we can input the long and lat data into flightPlanCoordinates to show the curve of
the flight rather than a direct line connecting them
*/


// lat and long for source and dest go in here
    var dest1 = [-43.4876, 172.5374]
    var dest2 = [-37.0082, 174.7850]
    var flightPlanCoordinates = [
            {lat: dest1[0], lng: dest1[1]},
            {lat: dest2[0], lng: dest2[1]}
        ];

    const lineSymbol = {
        path: "M 0,-1 0,1",
        strokeOpacity: 1,
        scale: 4
  };



    function initMap() {
        map = new google.maps.Map(document.getElementById('map'), {

            center: new google.maps.LatLng(markers[0].lat, markers[0].lng),
            zoom: 5.8,
            zoomControl: false,
            mapTypeControl: false,
            scaleControl: false,
            streetViewControl: false,
            rotateControl: false,
            fullscreenControl: false
        });

        var infoWindow = new google.maps.InfoWindow();
        var latlngbounds = new google.maps.LatLngBounds();

//        for (var i = 0; i < markers.length; i++) {
//            var data = markers[i]
//            var Latlng = new google.maps.LatLng(data.lat, data.lng);
//            var marker = new google.maps.Marker({
//                position: Latlng,
//                map: map,
//                title: data.title,
//                icon: marker_icon
//            });
//            latlngbounds.extend(marker.position);
//        }
//
//        displayRoute(dest1, dest2);
        repositionMap(latlngbounds);

    }

    function displayRoute(flightPath) {

     // CLEAR EXISTING MARKERS
//        if (marker1 !== undefined && marker2 !== undefined && path !== undefined) {
//            marker1.setMap(null);
//            marker2.setMap(null);
//            path.setMap(null);
//        }

        if (flightPath.length < 2) {
            return;
        }
        for (var i = 0; i < flightPath.length; i++) {
            var data = flightPath[i]
            var Latlng = new google.maps.LatLng(data.lat, data.lng);
            var marker = new google.maps.Marker({
                position: Latlng,
                map: map,
                title: data.title,
                icon: marker_icon
            });
        }

        // CREATE MARKERS AT START AND FINISH
//        marker1 = new google.maps.Marker({
//            position: {marker[0].},
//            map: map,
//            icon: marker_icon
//        });
//
//        marker2 = new google.maps.Marker({
//            position: flightPath[flightPath.length - 1],
//            map: map,
//            icon: marker_icon
//        });

//        // DRAW POLYLINE FOR ROUTE
//        path = new google.maps.Polyline({
//            path: flightPath,
//            geodesic: true,
//            strokeColor: '#FF0000',
//            strokeOpacity: 1.0,
//            strokeWeight: 2
//        });
//
//        path.setMap(map);

//        var flightPath = new google.maps.Polyline({
//            path: flightPlanCoordinates,
//            strokeColor: 'Blue',
//            strokeOpacity: 0,
//
//            icons: [
//              {
//                icon: lineSymbol,
//                offset: "0",
//                repeat: "25px"
//              }
//            ],
//        });-->
//        google.maps.event.addListener(flightPath, 'click', function() {
//            window.alert("sometext");
//        });

//        flightPath.setMap(map);
    }

    function repositionMap(latlngbounds) {
        var bounds = new google.maps.LatLngBounds();

        for (var i = 0; i < flightPath.length; i++) {
            bounds.extend(flightPath[i]);
        }

        map.fitBounds(bounds);
    }