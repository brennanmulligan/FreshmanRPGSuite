import 'package:equatable/equatable.dart';
import '../../type_definitions.dart';

class ObjectiveRecordDTO extends Equatable {
  final int id;
  String description;
  int experiencePointsGained;
  final int questID;
  int completionType;

  ObjectiveRecordDTO({required this.id,
        required this.description,
        required this.experiencePointsGained,
        required this.questID,
        required this.completionType});

  @override
  List<Object?> get props => [id, description,
                              experiencePointsGained, questID, completionType];

  ///
  /// Convert object to JSON.
  ///
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'description': description,
      'experiencePointsGained': experiencePointsGained,
      'questID': questID,
      'completionType': completionType,
    };
  }

  factory ObjectiveRecordDTO.fromJson({
    required JSON json,
  }) {
    return ObjectiveRecordDTO(
        id: json["id"],
        description: json['description'],
        experiencePointsGained : json['experiencePointsGained'],
        questID: json['questID'],
        completionType: json['completionType'],
    );
  }
}

