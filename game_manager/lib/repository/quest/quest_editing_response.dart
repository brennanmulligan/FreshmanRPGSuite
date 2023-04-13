import 'package:equatable/equatable.dart';

import '../../type_definitions.dart';
import 'quest_editing_info_DTO.dart';

class QuestResponse extends Equatable {
  final bool success;
  final QuestEditingInfoDTO questEditingInfoDTO;

  const QuestResponse(this.success, {required this.questEditingInfoDTO});

  const QuestResponse.allFields({required this.success,required this.questEditingInfoDTO});

  factory QuestResponse.fromJson({
    required JSON json,
  }) {
    return QuestResponse.allFields(
      success: json['success'],
      questEditingInfoDTO: QuestEditingInfoDTO.fromJson(json: json['questEditingInfoDTO']),
    );
  }

  @override
  List<Object> get props => [success, questEditingInfoDTO];

  @override
  String toString() {
    return 'QuestResponse(questEditingInfoDTO: $questEditingInfoDTO)';
  }
}