part of 'create_player_bloc.dart';

@immutable
abstract class CreatePlayerState {}

class CreatePlayerInitial extends CreatePlayerState {}
class CreatePlayerLoading extends CreatePlayerState {}
class CreatePlayerLoaded extends CreatePlayerState {
  final CreatePlayerResponse data;
  CreatePlayerLoaded(this.data);
}
class CreatePlayerSuccess extends CreatePlayerState{
  final CreatePlayerResponse data;
  CreatePlayerSuccess(this.data);
}
class CreatePlayerFailure extends CreatePlayerState{
}
