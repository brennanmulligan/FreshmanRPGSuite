part of 'quest_bloc.dart';


@immutable
abstract class QuestState extends Equatable {
  @override
  List<Object> get props => [];
}

class QuestInitial extends QuestState {}

class QuestLoading extends QuestState {}

class QuestComplete extends QuestState {
  final BasicResponse response;


  QuestComplete(this.response);


  @override
  List<Object> get props => [response];
}
