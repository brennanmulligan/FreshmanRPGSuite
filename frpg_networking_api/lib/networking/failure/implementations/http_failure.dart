import '../failure.dart';

///
/// HTTP failure. Used when something network related happened.
///
class HTTPFailure extends Failure {
  final String message;

  ///
  /// Constructor.
  ///
  HTTPFailure({
    required this.message,
  }) : super(properties: [
          message,
        ]);
}
