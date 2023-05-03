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


      List<String> fileLines = event.fileLines;
      List<CreatePlayerWithNameResponse> successful = [];
      List<CreatePlayerWithNameResponse> failed = [];

      List<String> parameters = fileLines.elementAt(0).split(",");
      if(!parameters.contains("playerName") || !parameters.contains("password") || !parameters.contains("crew") || !parameters.contains("major") || !parameters.contains("section")){
        CreateManyPlayersResponse failedResponse = const CreateManyPlayersResponse(success: false, description: "File format invalid. Fix file and try again", successful: [], failed: []);
        emit(CreateManyPlayersComplete(failedResponse));
      }else{
        int playerNameIndex = parameters.indexOf("playerName");
        int passwordIndex = parameters.indexOf("password");
        int crewIndex = parameters.indexOf("crew");
        int majorIndex = parameters.indexOf("major");
        int sectionIndex = parameters.indexOf("section");

        for(int i = 1; i < fileLines.length; i++) {
          parameters = fileLines.elementAt(i).split(",");

          if (parameters.length < 5) {
            CreatePlayerWithNameResponse invalidResponse = CreatePlayerWithNameResponse(success: false, description: "invalid player details", playerName: parameters.elementAt(0));
            failed.add(invalidResponse);
          } else {
            BasicResponse currentResponse = await playerRepository.createPlayer(
              CreatePlayerRequest
                (playerName: parameters.elementAt(playerNameIndex),
                  password: parameters.elementAt(passwordIndex),
                  crew: num.parse(parameters.elementAt(crewIndex)),
                  major: num.parse(parameters.elementAt(majorIndex)),
                  section: num.parse(parameters.elementAt(sectionIndex))));
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
      }
    });
  }


}


