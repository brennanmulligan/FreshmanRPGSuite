part of 'objectives_list_bloc.dart';

@immutable
abstract class ObjectivesListState extends Equatable {
  @override
  List<Object> get props => [];
}

class ObjectivesListInitial extends ObjectivesListState {}

class ObjectivesListLoading extends ObjectivesListState {}

class ObjectiveListLoaded extends ObjectivesListState {
  final AllObjectivesResponse response;

  ObjectiveListLoaded(this.response);

  @override
  List<Object> get props => [response];
}

class QRCodeScanInProgress extends ObjectivesListState {}

class ObjectiveCompletionFailed extends ObjectivesListState{}

class ObjectiveCompletionInProgress extends ObjectivesListState {}

class ObjectiveCompletionComplete extends ObjectivesListState {
  final GeneralResponse response;

  ObjectiveCompletionComplete(this.response);

  @override
  List<Object> get props => [response];
}


