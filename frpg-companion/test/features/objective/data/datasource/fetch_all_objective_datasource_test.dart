import 'dart:convert';
import 'package:test/test.dart';
import 'package:frpg_companion/features/objective/objective.dart';
import 'package:mockito/mockito.dart';

import '../../objective_mock.mocks.dart';

void main() {
  late MockServiceClient mockServiceClient;
  late FetchAllObjectiveDatasource datasource;

  setUp(() {
    mockServiceClient = MockServiceClient();
    datasource = FetchAllObjectiveDatasourceHTTP(
      sc: mockServiceClient,
    );
  });

  group('Test FetchAllObjectiveDatasource', () {
    test('Test for valid response.', () async {
      final testJSONResponse = jsonDecode('''{
        "objectives": [
    {
      "description": "",
      "questID": 0,
      "objectiveID": 1
    },
    {
      "description": "",
      "questID": 0,
      "objectiveID": 1
    },
    {
      "description": "",
      "questID": 0,
      "objectiveID": 1
    }
  ]
}''');

      final expected = FetchAllObjectiveResponse.fromJson(
        json: testJSONResponse,
      );
      when(
        mockServiceClient.get(
            endpoint: anyNamed('endpoint'), ),
      ).thenAnswer((realInvocation) async => testJSONResponse);

      final actual = await datasource.fetchAllObjective(
        request: const FetchAllObjectiveRequest(
          playerID: 0,
        ),
      );

      verify(mockServiceClient.get(
        endpoint: anyNamed('endpoint'),
      ));
      expect(actual, expected);
    });
  });
}
