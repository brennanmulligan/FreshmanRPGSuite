import 'package:equatable/equatable.dart';

///
/// Template for failures in code.
///
abstract class Failure extends Equatable {
  final List<Object?> properties;

  ///
  /// Constructor.
  ///
  const Failure({
    required this.properties,
  });

  ///
  /// Get the properties.
  ///
  @override
  List<Object?> get props => properties;
}

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
