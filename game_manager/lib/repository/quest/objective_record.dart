import 'package:equatable/equatable.dart';
import '../../type_definitions.dart';

class ObjectiveRecord extends Equatable {
  final num serialVersionUId;
  final int id;
  final String description;
  final int experiencePointsGained;
  final int questID;
  final int completionType;

  const ObjectiveRecord(
      { required this.serialVersionUId,
        required this.id,
        required this.description,
        required this.experiencePointsGained,
        required this.questID,
        required this.completionType,
      });

  @override
  List<Object?> get props => [serialVersionUId, id, description,
                              experiencePointsGained, questID, completionType];

  ///
  /// Convert object to JSON.
  ///
  Map<String, dynamic> toJson() {
    return {
      'serialVersionUID': serialVersionUId,
      'id': id,
      'description': description,
      'experiencePointsGained': experiencePointsGained,
      'questID': questID,
      'completionType': completionType,
    };
  }

  factory ObjectiveRecord.fromJson({
    required JSON json,
  }) {
    return ObjectiveRecord(
        serialVersionUId: json['serialVersionUID'],
        id: json["id"],
        description: json['description'],
        experiencePointsGained : json['experiencePointsGained'],
        questID: json['questID'],
        completionType: json['completionType'],
    );
  }
}

