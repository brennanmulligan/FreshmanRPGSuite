
import 'package:companion_app/model/position_with_status.dart';
import 'package:geolocator/geolocator.dart';

// redirects everyting into Geolocator.  Built so we can mock the calls in the tests
class GeoLocatorWrapper {
  Future<bool> isLocationEnabled() async {
    bool locationServicesEnabled = await Geolocator.isLocationServiceEnabled();
    return locationServicesEnabled;
  }

  Future<LocationPermission>checkPermission() {
    return Geolocator.checkPermission();
  }

  Future<LocationPermission> requestPermission(){
    return Geolocator.requestPermission();
  }
  Future<PositionWithStatus>getCurrentPosition() async {
    Position pos = await Geolocator.getCurrentPosition();
    return PositionWithStatus(latitude: pos.latitude, longitude: pos.longitude);
  }
}
