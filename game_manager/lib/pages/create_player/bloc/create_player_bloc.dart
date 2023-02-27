import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/cupertino.dart';

import '../../../repository/player_repository.dart';
import '../models/create_player_request.dart';
import '../models/create_player_response.dart';

part 'create_player_event.dart';
part 'create_player_state.dart';

class CreatePlayerBloc extends Bloc<CreatePlayerEvent, CreatePlayerState> {
  late CreatePlayerRequest data;
  final PlayerRepository playerRepository;

  CreatePlayerBloc({
    required this.playerRepository
  }) : super (CreatePlayerInitial()) {
    on<SendCreatePlayerEvent>((event, emit) async {
      emit(CreatePlayerLoading());
      CreatePlayerResponse response = await playerRepository.createPlayer(CreatePlayerRequest
        (name:
      event.name, password: event.password, crew: event.crew,
          major: event.major, section: event.section));

        emit(CreatePlayerComplete(response));

    });
  }


}


