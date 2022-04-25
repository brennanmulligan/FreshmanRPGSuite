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
