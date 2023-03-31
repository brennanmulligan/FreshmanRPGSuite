part of 'create_many_player_bloc.dart';

import 'package:equatable/equatable.dart';

import '../../../repository/player/basic_response.dart';

@immutable
abstract class CreateManyPlayerState extends Equatable {
  @override
  List<Object> get props => [];
}

class CreateManyPlayerInitial extends CreateManyPlayerState {}

class CreateManyPlayerWaitingForBackend extends CreateManyPlayerState {}

class CreateManyPlayerComplete extends CreateManyPlayerState {
  final BasicResponse response;

  CreateManyPlayerComplete(this.response);

  @override
  List<Object> get props => [response];
}