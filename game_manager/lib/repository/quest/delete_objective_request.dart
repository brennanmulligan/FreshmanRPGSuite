import 'package:equatable/equatable.dart';
import 'package:game_manager/repository/quest/objective_record.dart';

class DeleteObjectiveRequest extends Equatable {
  final int objectiveId;
  final int questId;

  const DeleteObjectiveRequest(this.objectiveId, this.questId);

  @override
  List<Object?> get props => [objectiveId, questId];

  Map<String, dynamic> toJson() {
    return {'objectiveID': objectiveId, 'questID': questId};
  }

  @override
  String toString() {
    return 'QuestRequest(objectiveID: $objectiveId, questID: $questId)';
  }
}