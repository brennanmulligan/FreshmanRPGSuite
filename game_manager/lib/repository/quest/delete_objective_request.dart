import 'package:equatable/equatable.dart';
import 'package:game_manager/repository/quest/objective_record.dart';

class DeleteObjectiveRequest extends Equatable {
  final ObjectiveRecord objectiveRecord;

  const DeleteObjectiveRequest(this.objectiveRecord);

  @override
  List<Object?> get props => [objectiveRecord];

  Map<String, dynamic> toJson() {
    return {'objectiveRecord': objectiveRecord};
  }

  @override
  String toString() {
    return 'QuestRequest(objectiveRecord: $objectiveRecord)';
  }
}