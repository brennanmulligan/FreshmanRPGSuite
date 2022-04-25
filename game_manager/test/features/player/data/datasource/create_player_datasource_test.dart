import 'dart:convert';

import 'package:game_manager/features/player/player.dart';
import 'package:test/test.dart';
import 'package:mockito/mockito.dart';

import '../../player_mock.mocks.dart';

void main() {
  late MockServiceClient mockServiceClient;
  late CreatePlayerDatasource datasource;

  setUp(() {
    mockServiceClient = MockServiceClient();
    datasource = CreatePlayerDatasourceHTTP(
      sc: mockServiceClient,
    );
  });

  group('Test CreatePlayerDatasource', () {
    test('Test for valid response.', () async {
      final testJSONResponse = jsonDecode('''{
        "responseType" : 0
      }''');
      
      final expected = CreatePlayerResponse.fromJson(
        json: testJSONResponse,
      );

      when(
        mockServiceClient.post(
          endpoint: anyNamed('endpoint'),
          body: anyNamed('body'),
        ),
      ).thenAnswer((realInvocation) async => testJSONResponse);
      
      final actual = await datasource.createPlayer(
        request: const CreatePlayerRequest(
          name: "boby", 
          password: "bbbb123", 
          crew: 1, 
          major: 1, 
          section: 1,
        ),
      );

      verify(mockServiceClient.post(
        endpoint: anyNamed('endpoint'),
        body: anyNamed('body'),
      ));
      expect(actual, expected);
    });
  });
}