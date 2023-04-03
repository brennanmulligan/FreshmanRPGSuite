import 'package:flutter/foundation.dart';
import 'package:geolocator/geolocator.dart';
import 'location_utilities.dart';

//make a class that will hold the location data
class PositionWithStatus {
  final double latitude;
  final double longitude;
  final bool valid;
  final String message;

  PositionWithStatus(
      {required this.latitude,
      required this.longitude,
      this.valid = true,
      this.message = ""});

  //make a factory method that will return a Location object
  factory PositionWithStatus.fromPosition(Position position) {
    return PositionWithStatus(
      latitude: position.latitude,
      longitude: position.longitude,
    );
  }

  factory PositionWithStatus.fromFailure({required String message}) {
    return PositionWithStatus(
        latitude: 0.0, longitude: 0.0, valid: false, message: message);
  }

  // calculate the distance between two locations
  double distanceToInMeters(PositionWithStatus other) {
    return Geolocator.distanceBetween(
        latitude, longitude, other.latitude, other.longitude);
  }

  ///


  static Future<PositionWithStatus> getCurrentLocation(GeoLocatorWrapper geoLocator) async {
    ///
    /// Check if the user has location services enabled. If they don't, return
    /// a Result.failure.
    ///
    bool locationServicesEnabled = await geoLocator.isLocationEnabled();
    if (!locationServicesEnabled) {
      return PositionWithStatus.fromFailure(
        message: 'Location services are disabled. Please enable them.',
      );
    }

    ///
    /// Check if the user has location permissions enabled. If they don't, ask
    /// for permission. If they deny our request, return a Result.failure.
    ///
    LocationPermission hasLocationPermission =
        await geoLocator.checkPermission();
    if (hasLocationPermission == LocationPermission.denied) {
      hasLocationPermission = await geoLocator.requestPermission();
      if (hasLocationPermission == LocationPermission.denied) {
        return PositionWithStatus.fromFailure(
          message: 'Location permissions are denied. Please allow them.',
        );
      }
    }

    ///
    /// Check if the user has location permissions permanently denied. If they
    /// do, return a Result.failure.
    ///
    if (hasLocationPermission == LocationPermission.deniedForever) {
      return PositionWithStatus.fromFailure(
        message:
            'Location permissions are permanently denied, we cannot request permissions.',
      );
    }

    ///
    /// Check if we are unable to determine the user's location permissions.
    /// If we are unable to determine, return a Result.failure.
    ///
    if (hasLocationPermission == LocationPermission.unableToDetermine) {
      return PositionWithStatus.fromFailure(
        message:
            'We are unable to determine if location permissions are allowed. The app will assume permissions are denied.',
      );
    }

    ///
    ///
    ///
    final PositionWithStatus currPosition = await geoLocator.getCurrentPosition();

    debugPrint('--- BEGIN Location Data Dump ---');
    debugPrint('Longitude: ${currPosition.longitude}');
    debugPrint('Latitude: ${currPosition.latitude}');
    debugPrint('--- END Location Data Dump ---');

    return currPosition;
  }
}
