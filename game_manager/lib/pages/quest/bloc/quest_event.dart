part of 'quest_bloc.dart';

@immutable
abstract class QuestEvent {}

class SendUpsertQuestEvent extends QuestEvent {
  final int id;
  final String? title;
  final String? description;
  final int xpGained;
  final String? triggerMapName;
  final int triggerRow;
  final int triggerCol;
  final int objectivesForFulfillment;
  final int completionActionType;
  final String? startDate;
  final String? endDate;
  final bool easterEgg;

  SendUpsertQuestEvent(
      this.id,
      this.title,
      this.description,
      this.xpGained,
      this.triggerMapName,
      this.triggerRow,
      this.triggerCol,
      this.objectivesForFulfillment,
      this.completionActionType,
      this.startDate,
      this.endDate,
      this.easterEgg);

  @override
  String toString() {
    return 'SendUpsertQuestEvent(id: $id, title: $title, description: $description, xpGained: $xpGained, triggerMapName: $triggerMapName, triggerRow: $triggerRow, triggerCol: $triggerCol, objectivesForFulfillment: $objectivesForFulfillment, completionActionType: $completionActionType, startDate: $startDate, endDate: $endDate, easterEgg: $easterEgg)';
  }
}

class SendGetQuestEditingInformationEvent extends QuestEvent {
  SendGetQuestEditingInformationEvent()
      : super();
  @override
  String toString() {
    return 'GetQuestEditingInformationEvent()';
  }

}

class SendGetQuestInformation extends QuestEvent{
  final String title;

  SendGetQuestInformation(this.title);

  @override
  String toString() {
    return 'QuestInformationEvent(title: $title)';
  }

}