import 'package:flutter_test/flutter_test.dart';
import 'package:game_manager/pages/create_player/bloc/create_player_bloc.dart';
import 'package:game_manager/repository/player/basic_response.dart';
import 'package:game_manager/repository/player/create_many_players_response.dart';
import 'package:game_manager/repository/player/create_player_request.dart';
import 'package:mockito/annotations.dart';
import 'package:bloc_test/bloc_test.dart';
import 'package:game_manager/pages/create_many_players/bloc/create_many_players_bloc.dart';
import 'package:mockito/mockito.dart';
import 'package:game_manager/repository/player/player_repository.dart';
import 'dart:io' as io;

import 'create_many_players_test.mocks.dart';

class PlayerRepositoryTest extends Mock implements PlayerRepository {}

@GenerateMocks([PlayerRepositoryTest])
Future<void> main() async {
  late MockPlayerRepositoryTest playerRepo;

  setUpAll(() {
    playerRepo = MockPlayerRepositoryTest();
  });

  group('Create Many Players Tests: ', () {
    const CreateManyPlayersResponse validResponse =
    CreateManyPlayersResponse(success: true,
        description: "Created a list of players",
        successful: [
          CreatePlayerWithNameResponse(
              success: true, description: "created", playerName: 'p1'),
          CreatePlayerWithNameResponse(
              success: true, description: "created", playerName: 'p2')
        ],
        failed: []);

    const CreateManyPlayersResponse invalidResponse =
    CreateManyPlayersResponse(success: true,
        description: "Created a list of players",
        successful: [
          CreatePlayerWithNameResponse(
              success: true, description: "created", playerName: 'p1')
        ],
        failed: [
          CreatePlayerWithNameResponse(
            success: false, description: "invalid player details", playerName: 'p2')]);

    const CreateManyPlayersResponse invalidFileResponse =
    CreateManyPlayersResponse(success: false, description: "File format invalid. Fix file and try again", successful: [], failed: []);

    const BasicResponse goodResponse = BasicResponse(
        success: true, description: "created");

    const CreatePlayerRequest createPlayerOneRequest = CreatePlayerRequest(
        name: "p1",
        password: "Testpassword123@",
        crew: 1,
        major: 1,
        section: 1);
    const CreatePlayerRequest createPlayerTwoRequest = CreatePlayerRequest(
        name: "p2",
        password: "Testpassword123@",
        crew: 1,
        major: 1,
        section: 1);
    io.File testCSVFileValid = io.File("${io.Directory.current
        .path}/test/pages/create_many_players/manyPlayersTestFileValid.csv");
    io.File testCSVFileInvalid = io.File("${io.Directory.current
        .path}/test/pages/create_many_players/manyPlayersTestFileInvalid.csv");
    io.File testCSVFileInvalidFormat = io.File("${io.Directory.current
        .path}/test/pages/create_many_players/manyPlayersTestFileInvalidFormat.csv");

    blocTest<CreateManyPlayersBloc, CreateManyPlayersState>(
        'Check flow of states when many players are being created and both users are valid',
        build: () {
          when(playerRepo.createPlayer(any))
              .thenAnswer((_) async => goodResponse);

          when(playerRepo.createPlayer(any))
              .thenAnswer((_) async => goodResponse);
          return CreateManyPlayersBloc(playerRepository: playerRepo);
        },
        act: (bloc) => bloc.add(SendCreateManyPlayersEvent(testCSVFileValid)),
        wait: const Duration(milliseconds: 2000),
        expect: () =>
        [
          CreateManyPlayersLoading(),
          CreateManyPlayersComplete(validResponse)
        ],
        verify: (_) =>
        {
          verify(playerRepo.createPlayer(createPlayerOneRequest)).called(1)
        }
    );

    blocTest<CreateManyPlayersBloc, CreateManyPlayersState>(
        'Check flow of states when many players are being created and a user is invalid',
        build: () {
          when(playerRepo.createPlayer(any))
              .thenAnswer((_) async => goodResponse);

          when(playerRepo.createPlayer(any))
              .thenAnswer((_) async => goodResponse);
          return CreateManyPlayersBloc(playerRepository: playerRepo);
        },
        act: (bloc) => bloc.add(SendCreateManyPlayersEvent(testCSVFileInvalid)),
        wait: const Duration(milliseconds: 2000),
        expect: () =>
        [
          CreateManyPlayersLoading(),
          CreateManyPlayersComplete(invalidResponse)
        ],
        verify: (_) =>
        {
          verify(playerRepo.createPlayer(createPlayerOneRequest)).called(1),
          verify(playerRepo.createPlayer(createPlayerTwoRequest)).called(1)
        }
    );
    blocTest<CreateManyPlayersBloc, CreateManyPlayersState>(
        'Check flow of states when many players are being created and the files format is invalid',
        build: () {

          return CreateManyPlayersBloc(playerRepository: playerRepo);
        },
        act: (bloc) => bloc.add(SendCreateManyPlayersEvent(testCSVFileInvalidFormat)),
        wait: const Duration(milliseconds: 2000),
        expect: () =>
        [
          CreateManyPlayersLoading(),
          CreateManyPlayersComplete(invalidFileResponse)
        ],

    );

  });


}
