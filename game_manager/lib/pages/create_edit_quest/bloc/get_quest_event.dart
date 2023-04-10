part of 'get_quest_bloc.dart';

@immutable
abstract class GetQuestEvent{}

class SendCreateQuestEvent extends GetQuestEvent {

  final num quest;


  SendCreateQuestEvent(this.quest);

  @override
  String toString() {
    return 'SendCreateQuestEvent(quest: $quest)';
  }
}

class SendEditQuestEvent extends GetQuestEvent {

  final num quest;


  SendEditQuestEvent(this.quest);

  @override
  String toString() {
    return 'SendEditQuestEvent(quest: $quest)';
  }
}