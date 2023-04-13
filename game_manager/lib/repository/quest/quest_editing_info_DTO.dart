import 'package:equatable/equatable.dart';
import 'package:game_manager/repository/quest/quest_record.dart';

import '../../type_definitions.dart';
import 'action_type_DTO.dart';

class QuestEditingInfoDTO extends Equatable {
  final List<QuestRecord> quests;
  final List<String> mapNames;
  final List<ActionTypeDTO> completionActionTypes;

  const QuestEditingInfoDTO({required this.quests, required this.mapNames, required this.completionActionTypes});

  const QuestEditingInfoDTO.allFields({required this.quests, required this.mapNames, required this.completionActionTypes});

  @override
  List<Object?> get props => [quests, mapNames, completionActionTypes];

  ///
  /// Convert object to JSON.
  ///
  Map<String, dynamic> toJson() {
    return {
      'quests': quests,
      'mapNames' : mapNames,
      'completionActionTypes' : completionActionTypes,
    };
  }

  factory QuestEditingInfoDTO.fromJson({
    required JSON json,
  }) {
    return QuestEditingInfoDTO.allFields(
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
}