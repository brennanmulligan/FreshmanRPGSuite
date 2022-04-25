import 'package:equatable/equatable.dart';
import 'package:frpg_networking_api/networking/service_client/type_definitions/type_definitions.dart';

class ObjectiveInformation extends Equatable {
  final String description;
  final num questID;
  final num objectiveID;

  ///
  /// Constructor
  ///
  const ObjectiveInformation({
    required this.description,
    required this.questID,
    required this.objectiveID,
  });

  ///
  /// Factory mapping `JSON' to `ObjectiveInformation`
  ///
  factory ObjectiveInformation.fromJson({
    required JSON json,
  }) {
    return ObjectiveInformation(
      description: json['description'],
      questID: json['questID'],
      objectiveID: json['objectiveID'],
    );
  }

  ///
  /// Get properties of object for comparison
  @override
  List<Object?> get props => [
        description,
        questID,
        objectiveID,
      ];

  @override
  String toString() {
    return 'ObjectiveInformation(description: $description, '
        'questID: $questID, objectiveID: $objectiveID)';
  }
}
