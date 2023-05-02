import 'package:bloc_test/bloc_test.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:game_manager/pages/change_password/bloc/change_password_bloc.dart';
import 'package:game_manager/repository/player/all_players_response.dart';
import 'package:game_manager/repository/player/basic_response.dart';
import 'package:game_manager/repository/player/player.dart';
import 'package:game_manager/repository/player/player_repository.dart';
import 'package:mockito/annotations.dart';
import 'package:mockito/mockito.dart';

import 'change_password_test.mocks.dart';

class PlayerRepositoryTest extends Mock implements PlayerRepository {}

@GenerateMocks([PlayerRepositoryTest])
Future<void> main() async{
  late MockPlayerRepositoryTest playerRepo;

  setUpAll(() {
    playerRepo = MockPlayerRepositoryTest();
  });

  group('Change Password Tests: ', () {
    const BasicResponse goodResponse =
    BasicResponse(success: true, description: "changed");
    AllPlayersResponse playerResponse = AllPlayersResponse(
        true,
        players: [
          Player(
              playerName: "test",
              playerID: 1
          )
        ]);

    blocTest<ChangePasswordBloc, ChangePasswordState>(
      'Check flow of states for page building',
      build: () {
        when(playerRepo.getAllPlayers(any))
            .thenAnswer((_) async => playerResponse);
        return ChangePasswordBloc(playerRepository: playerRepo);
      },
      act: (bloc) => bloc.add(GetPlayerNamesForPageEvent()),
      wait: const Duration(milliseconds: 500),
      expect: () => [ChangePasswordLoading(), PasswordPageReady(playerResponse)],
    );

    blocTest<ChangePasswordBloc, ChangePasswordState>(
      'Check flow of states for submittion',
      build: () {
        when(playerRepo.changePassword(any))
            .thenAnswer((_) async => goodResponse);
        return ChangePasswordBloc(playerRepository: playerRepo);
      },
      act: (bloc) => bloc.add(SendChangePasswordEvent("fred", "newpw")),
      wait: const Duration(milliseconds: 500),
      expect: () => [ChangePasswordLoading(), ChangePasswordComplete(goodResponse)],
    );
  });
}