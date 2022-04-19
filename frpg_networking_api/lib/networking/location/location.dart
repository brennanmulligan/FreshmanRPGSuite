import 'package:equatable/equatable.dart';

///
/// Object that represents a user's location.
///
class Location extends Equatable {
  final num longitude;
  final num latitude;

  ///
  /// Constructor.
  ///
  const Location({
    required this.longitude,
    required this.latitude,
  });

  ///
  /// All properties of a Location object.
  ///
  @override
  List<Object?> get props => [
        longitude,
        latitude,
      ];

  ///
  /// Converts a location object to a string.
  ///
  @override
  String toString() {
    return 'Location(longitude: $longitude, latitude: $latitude)';
  }
}
