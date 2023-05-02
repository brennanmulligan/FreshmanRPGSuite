import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:game_manager/repository/player/all_players_response.dart';
import 'package:game_manager/repository/player/player_repository.dart';
import 'package:meta/meta.dart';

import '../../../repository/player/all_players_request.dart';
import '../../../repository/player/basic_response.dart';
import '../../../repository/player/change_player_request.dart';
import '../../../repository/player/player_repository.dart';

part 'change_password_event.dart';
part 'change_password_state.dart';

class ChangePasswordBloc extends Bloc<ChangePasswordEvent, ChangePasswordState> {
  late ChangePlayerRequest data;
  final PlayerRepository playerRepository;

  ChangePasswordBloc({
    required this.playerRepository,
  }) : super(ChangePasswordInitial()) {
    on<GetPlayerNamesForPageEvent>((event, emit) async {
      emit(ChangePasswordLoading());
      AllPlayersResponse playerResponse = await playerRepository.getAllPlayers(const AllPlayersRequest());
      print("after response");
      emit (PasswordPageReady(playerResponse));
    });

    on<SendChangePasswordEvent>((event, emit) async {
      emit(ChangePasswordLoading());

      BasicResponse response = await playerRepository.changePassword(ChangePlayerRequest
        (username: event.name, password: event.newPassword));

      emit(ChangePasswordComplete(response));
    });
  }
}
