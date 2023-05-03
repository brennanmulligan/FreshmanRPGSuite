part of 'create_player_bloc.dart';

@immutable
abstract class CreatePlayerPageState extends Equatable {
  @override
  List<Object> get props => [];
}

class CreatePlayerInitial extends CreatePlayerPageState {}

class CreatePlayerLoading extends CreatePlayerPageState {}

class CreatePlayerComplete extends CreatePlayerPageState {
  final BasicResponse response;

  CreatePlayerComplete(this.response);

  @override
  List<Object> get props => [response];
}

class GetMajorsAndCrewsComplete extends CreatePlayerPageState {
  final GetAllMajorsResponse majorsResponse;
  final GetAllCrewsResponse crewsResponse;

  GetMajorsAndCrewsComplete(this.majorsResponse, this.crewsResponse);

  @override
  List<Object> get props => [majorsResponse, crewsResponse];
}
