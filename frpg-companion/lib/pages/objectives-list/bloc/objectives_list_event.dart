part of 'objectives_list_bloc.dart';

@immutable
abstract class ObjectivesListEvent {}

class RequestObjectivesEvent extends ObjectivesListEvent {
  final int playerID;

  RequestObjectivesEvent(this.playerID);

  @override
  String toString() {
    return 'RequestObjectivesEvent: $playerID';
  }
}

class InitEvent extends ObjectivesListEvent {
  final int playerID;

  InitEvent(this.playerID);

  @override
  String toString() {
    return 'InitEvent: $playerID';
  }
}

class RequestQRCodeScanEvent extends ObjectivesListEvent {
  final int playerID;
  final int questID;
  final int objectiveID;

  RequestQRCodeScanEvent( this.playerID, this.questID, this.objectiveID);

  @override
  String toString() {
    return 'RequestPhotoEvent';
  }
}


class CompleteObjectiveEvent extends ObjectivesListEvent {
  final int playerID;
  final int questID;
  final int objectiveID;

  CompleteObjectiveEvent(this.playerID, this.questID, this.objectiveID);

  @override
  String toString() {
    return 'CompleteObjectiveEvent{playerID: $playerID, questID: $questID, objectiveID: $objectiveID}';
  }
}
