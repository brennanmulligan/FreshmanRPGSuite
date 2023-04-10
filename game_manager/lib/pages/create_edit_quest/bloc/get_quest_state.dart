part of 'get_quest_bloc.dart';


@immutable
abstract class GetQuestState extends Equatable {
  @override
  List<Object> get props => [];
}

class GetQuestInitial extends GetQuestState {}

class GetQuestLoading extends GetQuestState {}

class GetQuestComplete extends GetQuestState {
  final BasicResponse response;


  GetQuestComplete(this.response);


  @override
  List<Object> get props => [response];
}
