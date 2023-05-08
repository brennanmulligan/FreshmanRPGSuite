import 'package:equatable/equatable.dart';
import '../../type_definitions.dart';
import 'action_type_DTO.dart';
import 'objective_record.dart';

class QuestRecord extends Equatable {
  final int id;
  final String title;
  final String description;
  final int xpGained;
  final String? triggerMapName;
  final int triggerRow;
  final int triggerCol;
  final int objectivesForFulfillment;
  final List<ObjectiveRecordDTO> objectives;
  final ActionTypeDTO completionActionType;
  final String startDate;
  final String endDate;
  final bool easterEgg;

  const QuestRecord(
  { required this.id,
    required this.title,
    required this.description,
    required this.xpGained,
    required this.triggerMapName,
    required this.triggerRow,
    required this.triggerCol,
    required this.objectivesForFulfillment,
    required this.objectives,
    required this.completionActionType,
    required this.startDate,
    required this.endDate,
    required this.easterEgg});

  @override
  List<Object?> get props => [id, title, description, xpGained, triggerMapName, triggerRow, triggerCol,
                              objectivesForFulfillment, objectives, completionActionType, startDate, endDate, easterEgg];

  ///
  /// Convert object to JSON.
  ///
  Map<String, dynamic> toJson() {
    return {
      'questID': id,
      'title': title,
      'description': description,
      'experiencePointsGained': xpGained,
      'mapName': triggerMapName,
      'position': {
        'row': triggerRow,
        'column': triggerCol,
      },
      'objectivesForFulfillment': objectivesForFulfillment,
      'objectives': objectives,
      'actionType': completionActionType,
      'startDate': startDate,
      'endDate': endDate,
      'easterEgg': easterEgg
    };
  }

  factory QuestRecord.fromJson({
    required JSON json,
  }) {
    return QuestRecord(
        id: json['questID'],
        title: json['title'],
        description: json['description'],
        xpGained : json['experiencePointsGained'],
        triggerMapName : json['mapName'],
        triggerRow : json['position']['row'],
        triggerCol : json['position']['column'],
        objectivesForFulfillment : json['objectivesForFulfillment'],
        objectives: (json['objectives'] as List)
            .map((e) => ObjectiveRecordDTO.fromJson(json: e))
            .toList(),
        completionActionType : ActionTypeDTO.fromJson(json: json['actionType']),
        startDate : json['startDate'],
        endDate : json['endDate'],
        easterEgg : json['easterEgg']
    );
  }
}

