part of 'create_player_bloc.dart';

@immutable
abstract class CreatePlayerState extends Equatable {
  @override
  List<Object> get props => [];
}

class CreatePlayerInitial extends CreatePlayerState {}

class CreatePlayerLoading extends CreatePlayerState {}

class CreatePlayerComplete extends CreatePlayerState {
  final BasicResponse response;

  CreatePlayerComplete(this.response);

  @override
  List<Object> get props => [response];
}
