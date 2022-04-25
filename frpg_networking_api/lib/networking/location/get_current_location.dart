import 'package:flutter/foundation.dart';
import 'package:frpg_networking_api/networking/result/result.dart';
import 'package:frpg_networking_api/networking/failure/implementations/location_access_failure.dart';
import 'package:geolocator/geolocator.dart';

///
/// Get the current location of the user.
///
Future<Result<Position>> getCurrentLocation() async {
  ///
  /// Check if the user has location services enabled. If they don't, return
  /// a Result.failure.
  ///
  bool locationServicesEnabled = await Geolocator.isLocationServiceEnabled();
  if (!locationServicesEnabled) {
    return Result.failure(
      failure: LocationAccessFailure(
        message: 'Location services are disabled. Please enable them.',
      ),
    );
  }

  ///
  /// Check if the user has location permissions enabled. If they don't, ask
  /// for permission. If they deny our request, return a Result.failure.
  ///
  LocationPermission hasLocationPermission = await Geolocator.checkPermission();
  if (hasLocationPermission == LocationPermission.denied) {
    hasLocationPermission = await Geolocator.requestPermission();
    if (hasLocationPermission == LocationPermission.denied) {
      return Result.failure(
        failure: LocationAccessFailure(
          message: 'Location permissions are denied. Please allow them.',
        ),
      );
    }
  }

  ///
  /// Check if the user has location permissions permanently denied. If they
  /// do, return a Result.failure.
  ///
  if (hasLocationPermission == LocationPermission.deniedForever) {
    return Result.failure(
      failure: LocationAccessFailure(
        message:
            'Location permissions are permanently denied, we cannot request permissions.',
      ),
    );
  }

  ///
  /// Check if we are unable to determine the user's location permissions.
  /// If we are unable to determine, return a Result.failure.
  ///
  if (hasLocationPermission == LocationPermission.unableToDetermine) {
    return Result.failure(
      failure: LocationAccessFailure(
        message:
            'We are unable to determine if location permissions are allowed. The app will assume permissions are denied.',
      ),
    );
  }

  ///
  ///
  ///
  final locationData = await Geolocator.getCurrentPosition();

  debugPrint('--- BEGIN Location Data Dump ---');
  debugPrint('Longitude: ${locationData.longitude}');
  debugPrint('Latitude: ${locationData.latitude}');
  debugPrint('Accuracy: ${locationData.accuracy}');
  debugPrint('--- END Location Data Dump ---');

  return Result.data(
    data: locationData,
  );
}
