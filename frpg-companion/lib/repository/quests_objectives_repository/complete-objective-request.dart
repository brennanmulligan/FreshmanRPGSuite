import 'package:equatable/equatable.dart';

class CompleteObjectiveRequest extends Equatable {
  final int playerID;
  final int questID;
  final int objectiveID;

  const CompleteObjectiveRequest({required this.playerID, required this
      .questID, required this.objectiveID});

  @override
  List<Object?> get props =>  [playerID, questID, objectiveID];

  Map<String, dynamic> toJson() {
    return {'playerID': playerID, 'questID': questID, 'objectiveID': objectiveID};
  }

  @override
  String toString() {
    return 'CompleteObjectiveRequest(PlayerID: $playerID, questID: $questID, '
        'objectiveID: $objectiveID)';
  }
}