import 'dart:io' as io;

import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:game_manager/repository/player/player_repository.dart';
import 'package:meta/meta.dart';

import '../../../repository/player/basic_response.dart';
import '../../../repository/player/change_player_request.dart';
import '../../../repository/player/create_many_players_response.dart';
import '../../../repository/player/create_player_request.dart';
import '../../../repository/player/player_repository.dart';

part 'create_many_players_event.dart';
part 'create_many_players_state.dart';

class CreateManyPlayersBloc extends Bloc<CreateManyPlayersEvent, CreateManyPlayersState> {
  late CreatePlayerRequest data;
  final PlayerRepository playerRepository;

  CreateManyPlayersBloc({
    required this.playerRepository
  }) : super (CreateManyPlayersInitial()) {
    on<SendCreateManyPlayersEvent>((event, emit) async {
      emit(CreateManyPlayersLoading());


      io.File file = event.csvFile;
      List<String> lines = await file.readAsLines();
      List<CreatePlayerWithNameResponse> successful = [];
      List<CreatePlayerWithNameResponse> failed = [];

      lines.removeAt(0);

      for(int i = 0; i < lines.length; i++) {
        List<String> parameters = lines.elementAt(i).split(",");

        if (parameters.length < 5) {
          CreatePlayerWithNameResponse invalidResponse = CreatePlayerWithNameResponse(success: false, description: "invalid player details", playerName: parameters.elementAt(0));
          failed.add(invalidResponse);
        } else {
          BasicResponse currentResponse = await playerRepository.createPlayer(
              CreatePlayerRequest
                (name: parameters.elementAt(0),
                  password: parameters.elementAt(1),
                  crew: num.parse(parameters.elementAt(2)),
                  major: num.parse(parameters.elementAt(3)),
                  section: num.parse(parameters.elementAt(4))));
          //await Future.delayed(const Duration(seconds: 1));

          CreatePlayerWithNameResponse successResponse = CreatePlayerWithNameResponse(
              success: currentResponse.success,
              description: currentResponse.description,
              playerName: parameters.elementAt(0));

          if (currentResponse.success == true) {
            successful.add(successResponse);
          } else {
            failed.add(successResponse);
          }
        }
      }
      emit(CreateManyPlayersComplete(CreateManyPlayersResponse(success: true, description: "Created a list of players", successful: successful, failed: failed)));
    });
  }


}


