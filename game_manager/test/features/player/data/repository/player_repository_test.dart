import 'package:game_manager/features/player/player.dart';
import 'package:frpg_networking_api/networking/result/result.dart';
import 'package:test/test.dart';
import 'package:mockito/mockito.dart';

import '../../player_mock.mocks.dart';

void main() {
  late PlayerRepository _repository;
  late MockCreatePlayerDatasource mockCreatePlayerDatasource;

  setUp(() {
    mockCreatePlayerDatasource = MockCreatePlayerDatasource();
    _repository = PlayerRepositoryHTTP(
      createPlayerDatasource: mockCreatePlayerDatasource,
    );
  });

  group('Test CreatePlayerDatasource.', () {
    test('Test for ResultData.', () async {
      const expected = CreatePlayerResponse(
        success: true,
        description: "Created"
      );

      when(mockCreatePlayerDatasource.createPlayer(
        request: anyNamed('request'),
      )).thenAnswer((realInvocation) async => expected);

      final actual = await _repository.createPlayer(
        request: const CreatePlayerRequest(
          name: "some dude", 
          password: "passs123", 
          crew: 1, 
          major: 2, 
          section: 3,
        ),
      );

      verify(mockCreatePlayerDatasource.createPlayer(
        request: anyNamed('request'),
      ));
      expect(actual, Result.data(data: expected));
    });

    test('Test for ResultFailure', () async {
      when(mockCreatePlayerDatasource.createPlayer(
        request: anyNamed('request'))).thenThrow(Exception());
      
      final actual = await _repository.createPlayer(
        request: const CreatePlayerRequest(
          name: "sadasasd", 
          password: "wrong", 
          crew: 1, 
          major: 2, 
          section: 3,
        ),
      );

      expect(actual, isA<ResultFailure>());
    });

  });
}