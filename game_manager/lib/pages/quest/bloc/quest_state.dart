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

class QuestPageReady extends QuestState {
  final QuestEditingDataResponse response;

  QuestPageReady(this.response);

  @override
  List<Object> get props => [response];

}

class DeleteObjective extends QuestState {}
