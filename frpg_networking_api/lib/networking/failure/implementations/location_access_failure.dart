import '../failure.dart';

///
/// Failure for when something goes wrong with accessing a user's location.
///
class LocationAccessFailure extends Failure {
  final String message;

  ///
  /// Constructor.
  ///
  LocationAccessFailure({
    required this.message,
  }) : super(properties: [
          message,
        ]);
}
