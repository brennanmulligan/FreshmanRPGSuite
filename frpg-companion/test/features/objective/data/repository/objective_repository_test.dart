import 'package:frpg_companion/features/network/result/result.dart';
import 'package:frpg_companion/features/objective/objective.dart';
import 'package:mockito/mockito.dart';
import 'package:test/test.dart';

import '../../objective_mock.mocks.dart';

void main() {
  late ObjectiveRepository _repository;
  late MockCompleteObjectiveDatasource mockCompleteObjectiveDatasource;
  late MockFetchAllObjectiveDatasource mockFetchAllObjectiveDatasource;

  setUp(() {
    mockCompleteObjectiveDatasource = MockCompleteObjectiveDatasource();
    mockFetchAllObjectiveDatasource = MockFetchAllObjectiveDatasource();
    _repository = ObjectiveRepositoryHTTP(
      completeObjectiveDatasource: mockCompleteObjectiveDatasource,
      fetchAllObjectiveDatasource: mockFetchAllObjectiveDatasource,
    );
  });

  group('Test CompleteObjectiveDatasource.', () {
    test('Test for ResultData.', () async {
      const expected = CompleteObjectiveResponse(
        responseType: ObjectiveResponseType.completed,
      );

      when(mockCompleteObjectiveDatasource.completeObjective(
        request: anyNamed('request'),
      )).thenAnswer((realInvocation) async => expected);

      final actual = await _repository.completeObjective(
        request: const CompleteObjectiveRequest(
          playerID: 0,
          objectiveID: 0,
          questID: 0,
        ),
      );

      verify(mockCompleteObjectiveDatasource.completeObjective(
        request: anyNamed('request'),
      ));
      expect(actual, Result.data(data: expected));
    });

    test('Test for ResultFailure', () async {
      when(mockCompleteObjectiveDatasource.completeObjective(
              request: anyNamed('request')))
          .thenThrow(Exception());

      final actual = await _repository.completeObjective(
        request: const CompleteObjectiveRequest(
          playerID: 0,
          objectiveID: 0,
          questID: 0,
        ),
      );

      expect(actual, isA<ResultFailure>());
    });
  });

  group('Test FetchAllObjectiveDatasource.', () {
    test('Test for ResultFailure', () async {
      when(mockFetchAllObjectiveDatasource.fetchAllObjective(
              request: anyNamed('request')))
          .thenThrow(Exception());

      final actual = await _repository.fetchAllObjectives(
        request: const FetchAllObjectiveRequest(playerID: 0),
      );

      expect(actual, isA<ResultFailure>());
    });

    test('Test for ResultData.', () async {
      const expected = FetchAllObjectiveResponse(information: []);
      
      when(mockFetchAllObjectiveDatasource.fetchAllObjective(
        request: anyNamed('request'),
      )).thenAnswer((realInvocation) async => expected);

      final actual = await _repository.fetchAllObjectives(
        request: const FetchAllObjectiveRequest(
          playerID: 0,
        ),
      );

      verify(mockFetchAllObjectiveDatasource.fetchAllObjective(
        request: anyNamed('request'),
      ));
      expect(actual, Result.data(data: expected));
    });
      
    });

}
