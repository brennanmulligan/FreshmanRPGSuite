import 'package:equatable/equatable.dart';

import '../../type_definitions.dart';
import 'action_type_DTO.dart';
import 'quest_record.dart';

class QuestResponse extends Equatable {
  final bool success;
  final List<QuestRecord> quests;
  final List<String> mapNames;
  final List<ActionTypeDTO> completionActionTypes;

  const QuestResponse(this.success, {required this.quests, required this.mapNames, required this.completionActionTypes});

  const QuestResponse.allFields({required this.success,required this.quests, required this.mapNames, required this.completionActionTypes});

  factory QuestResponse.fromJson({
    required JSON json,
  }) {
    return QuestResponse.allFields(
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
    );
  }

  @override
  List<Object> get props => [success, quests, mapNames, completionActionTypes];

  @override
  String toString() {
    return 'QuestResponse(quests: $quests, mapNames: $mapNames, completionActionTypes: $completionActionTypes)';
  }
}