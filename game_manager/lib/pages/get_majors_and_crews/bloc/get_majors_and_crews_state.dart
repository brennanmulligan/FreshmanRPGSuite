part of 'get_majors_and_crews_bloc.dart';

@immutable
abstract class GetMajorsAndCrewsState extends Equatable {
  @override
  List<Object> get props => [];
}

class GetMajorsAndCrewsInitial extends GetMajorsAndCrewsState {}

class GetMajorsAndCrewsLoading extends GetMajorsAndCrewsState {}

class GetMajorCrewComplete extends GetMajorsAndCrewsState {
  final BasicResponse response;

  GetMajorCrewComplete(this.response);

  @override
  List<Object> get props => [response];
}

