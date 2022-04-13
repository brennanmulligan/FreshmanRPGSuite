import 'dart:convert';
import 'package:test/test.dart';
import 'package:frpg_companion/features/objective/objective.dart';
import 'package:mockito/mockito.dart';

import '../../objective_mock.mocks.dart';

void main() {
  late MockServiceClient mockServiceClient;
  late CompleteObjectiveDatasource datasource;

  setUp(() {
    mockServiceClient = MockServiceClient();
    datasource = CompleteObjectiveDatasourceHTTP(
      sc: mockServiceClient,
    );
  });

  group('Test CompleteObjectiveDatasource', () {
    test('Test for valid response.', () async {
      final testJSONResponse = jsonDecode('''{
        "responseType": 0
      }''');

      final expected = CompleteObjectiveResponse.fromJson(
        json: testJSONResponse,
      );

      when(
        mockServiceClient.post(
          endpoint: anyNamed('endpoint'),
          body: anyNamed('body'),
        ),
      ).thenAnswer((realInvocation) async => testJSONResponse);

      final actual = await datasource.completeObjective(
        request: const CompleteObjectiveRequest(
          playerID: 0,
          objectiveID: 0,
          questID: 0,
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
