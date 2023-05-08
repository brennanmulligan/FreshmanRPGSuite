import 'package:equatable/equatable.dart';

import '../../type_definitions.dart';
import 'action_type_DTO.dart';
import 'objective_completion_type_DTO.dart';
import 'quest_record.dart';

class QuestEditingDataResponse extends Equatable {
  final bool success;
  final List<QuestRecord> quests;
  final List<String> mapNames;
  final List<ActionTypeDTO> completionActionTypes;
  final List<ObjectiveCompletionTypeDTO> objCompletionTypes;

  const QuestEditingDataResponse(this.success, {required this.quests, required this.mapNames, required this.completionActionTypes, required this.objCompletionTypes});

  const QuestEditingDataResponse.allFields({required this.success,required this.quests, required this.mapNames, required this.completionActionTypes, required this.objCompletionTypes});

  factory QuestEditingDataResponse.fromJson({
    required JSON json,
  }) {
    return QuestEditingDataResponse.allFields(
      success: json['success'],
      quests: (json['quests'] as List)
          .map((e) => QuestRecord.fromJson(json: e))
          .toList(),
      mapNames: (json['mapNames'] as List)
          .map((e) => e.toString())
          .toList(),
      completionActionTypes: (json['completionActionTypes'] as List)
          .map((e) => ActionTypeDTO.fromJson(json: e))
          .toList(),
      objCompletionTypes: (json['objCompletionTypes'] as List)
          .map((e) => ObjectiveCompletionTypeDTO.fromJson(json: e))
          .toList(),
    );
  }


  Map<String, dynamic> toJson()
  {
    return {
      'success': success,
      'quests': quests.map((e) => e.toJson()).toList(),
      'mapNames': mapNames,
      'completionActionTypes': completionActionTypes.map((e) => e.toJson()).toList(),
      'objCompletionTypes': objCompletionTypes.map((e) => e.toJson()).toList(),
    };
  }

  @override
  List<Object> get props => [success, quests, mapNames, completionActionTypes, objCompletionTypes];

  @override
  String toString() {
    return 'QuestResponse(quests: $quests, mapNames: $mapNames, completionActionTypes: $completionActionTypes, objCompletionTypes: $objCompletionTypes)';
  }
}