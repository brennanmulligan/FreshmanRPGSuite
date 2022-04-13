import 'package:equatable/equatable.dart';
import 'package:frpg_companion/features/network/network.dart';

import 'objective_response_type.dart';

///
/// Object to response for completing an object.
///
class CompleteObjectiveResponse extends Equatable {
  final ObjectiveResponseType responseType;

  ///
  /// Constructor.
  ///
  const CompleteObjectiveResponse({
    required this.responseType,
  });

  ///
  /// Factory mapping `JSON` to `CompleteObjectiveResponse`.
  ///
  factory CompleteObjectiveResponse.fromJson({
    required JSON json,
  }) {
    return CompleteObjectiveResponse(
      responseType: ObjectiveResponseType.values[json['responseType']],
    );
  }

  ///
  /// Get properties of object for comparison.
  ///
  @override
  List<Object?> get props => [
        responseType,
      ];

  ///
  /// Convert object to string.
  ///
  @override
  String toString() {
    return 'CompleteObjectiveResponse(responseType: $responseType)';
  }
}
