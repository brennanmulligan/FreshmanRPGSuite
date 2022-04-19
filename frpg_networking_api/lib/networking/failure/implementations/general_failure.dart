import '../failure.dart';

///
/// General failure. Used for most exceptions.
///
class GeneralFailure extends Failure {
  final Object? exception;
  final StackTrace? stackTrace;

  ///
  /// Constructor.
  ///
  GeneralFailure({
    required this.exception,
    required this.stackTrace,
  }) : super(properties: [
          exception,
          stackTrace,
        ]);
}
