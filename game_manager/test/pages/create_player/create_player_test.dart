import 'package:flutter_test/flutter_test.dart';
import 'package:game_manager/repository/player/basic_response.dart';
import 'package:game_manager/repository/player/get_all_crews_response.dart';
import 'package:game_manager/repository/player/get_all_majors_response.dart';
import 'package:mockito/annotations.dart';
import 'package:bloc_test/bloc_test.dart';
import 'package:game_manager/pages/create_player/bloc/create_player_bloc.dart';
import 'package:mockito/mockito.dart';
import 'package:game_manager/repository/player/player_repository.dart';

import 'create_player_test.mocks.dart';

class PlayerRepositoryTest extends Mock implements PlayerRepository {}

@GenerateMocks([PlayerRepositoryTest])
Future<void> main() async {
  late MockPlayerRepositoryTest playerRepo;

  setUpAll(() {
    playerRepo = MockPlayerRepositoryTest();
  });

  group('Create Player Tests: ', () {
    const BasicResponse goodResponse =
        BasicResponse(success: true, description: "created");

    const GetAllCrewsResponse goodCrewsResponse = GetAllCrewsResponse(true, crews: []);
    const GetAllMajorsResponse goodMajorsResponse = GetAllMajorsResponse(true, majors: []);

    blocTest<CreatePlayerBloc, CreatePlayerPageState>(
      'Check flow of states',
      build: () {
        when(playerRepo.createPlayer(any))
            .thenAnswer((_) async => goodResponse);
        return CreatePlayerBloc(playerRepository: playerRepo);
      },
      act: (bloc) => bloc.add(SendCreatePlayerEvent("fred", "pw", 1, 2, 3)),
      wait: const Duration(milliseconds: 500),
      expect: () => [CreatePlayerLoading(), CreatePlayerComplete(goodResponse)],
    );

    blocTest<CreatePlayerBloc, CreatePlayerPageState>(
      'emits [GetMajorsAndCrewsLoading, GetMajorsAndCrewsLoaded] when SendCreateMajorsCrewEvent is added',
      build: () {
        when(playerRepo.getAllMajors(any))
            .thenAnswer((_) async => goodMajorsResponse);
        when(playerRepo.getAllCrews(any))
            .thenAnswer((_) async => goodCrewsResponse);
        return CreatePlayerBloc(playerRepository: playerRepo);
      },
      act: (bloc) => bloc.add(SendGetMajorsAndCrewsEvent()),
      wait: const Duration(milliseconds: 2000),
      expect: () => [CreatePlayerLoading(), GetMajorsAndCrewsComplete(goodMajorsResponse, goodCrewsResponse)],
    );
  });
}
