part of 'create_many_players_bloc.dart';

@immutable
abstract class CreateManyPlayersState extends Equatable {
  @override
  List<Object> get props => [];
}

class CreateManyPlayersInitial extends CreateManyPlayersState {}

class CreateManyPlayersLoading extends CreateManyPlayersState {}

class CreateManyPlayersComplete extends CreateManyPlayersState {
  late final CreateManyPlayersResponse response;

  CreateManyPlayersComplete(this.response);

  @override
  List<Object> get props => [response];
}