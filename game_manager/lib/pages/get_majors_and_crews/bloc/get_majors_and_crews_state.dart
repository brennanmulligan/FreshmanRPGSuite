part of 'get_majors_and_crews_bloc.dart';

@immutable
abstract class GetMajorsAndCrewsState extends Equatable {
  @override
  List<Object> get props => [];
}

class GetMajorsAndCrewsInitial extends GetMajorsAndCrewsState {}

class GetMajorsAndCrewsLoading extends GetMajorsAndCrewsState {}

class GetMajorCrewComplete extends GetMajorsAndCrewsState {
  final AllMajorsResponse majorResponse;
  final AllCrewsResponse crewResponse;


  GetMajorCrewComplete(this.majorResponse, this.crewResponse);


  @override
  List<Object> get props => [majorResponse, crewResponse];
}

class GetMajorComplete extends GetMajorsAndCrewsState {
  final AllMajorsResponse response;


  GetMajorComplete(this.response);


  @override
  List<Object> get props => [response];
}

class GetCrewComplete extends GetMajorsAndCrewsState {
  final AllCrewsResponse response;


  GetCrewComplete(this.response);


  @override
  List<Object> get props => [response];
}