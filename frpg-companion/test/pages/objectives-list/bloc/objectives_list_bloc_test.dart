import 'package:bloc_test/bloc_test.dart';
import 'package:companion_app/model/barcode_scanner.dart';
import 'package:companion_app/pages/objectives-list/bloc/objectives_list_bloc.dart';
import 'package:companion_app/repository/quests_objectives_repository/all-objectives-response.dart';
import 'package:companion_app/repository/quests_objectives_repository/objective.dart';
import 'package:companion_app/repository/quests_objectives_repository/quests_objectives_repository.dart';
import 'package:companion_app/repository/shared/general_response.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mockito/annotations.dart';
import 'package:mockito/mockito.dart';

import 'objectives_list_bloc_test.mocks.dart';

class QuestsObjectivesRepositoryMock extends Mock
    implements QuestsObjectivesRepository {}
class BarCodeScannerMock extends Mock
    implements BarcodeScanner {}

@GenerateMocks([QuestsObjectivesRepositoryMock, BarCodeScannerMock])
Future<void> main() async {
  late MockQuestsObjectivesRepositoryMock questRepo;
  late MockBarCodeScannerMock mockScanner;

  setUpAll(() {
    questRepo = MockQuestsObjectivesRepositoryMock();
    mockScanner = MockBarCodeScannerMock();
  });

  group('Objective page tests', () {
    const AllObjectivesResponse goodObjectiveListResponse =
        AllObjectivesResponse(true, objectives: [
      Objective(questID: 3, objectiveID: 4, description: 'stupid objective'),
    ]);

    blocTest<ObjectivesListBloc, ObjectivesListState>(
      'Check flow of states when retrieving all objectives',
      build: () {
        when(questRepo.getAllObjectives(any))
            .thenAnswer((_) async => goodObjectiveListResponse);
        return ObjectivesListBloc(repository: questRepo, playerID: 42,
            scanner: mockScanner);
      },
      act: (bloc) => bloc.add(RequestObjectivesEvent(42)),
      expect: () => [
        ObjectivesListLoading(),
        ObjectiveListLoaded(goodObjectiveListResponse)
      ],
    );

    const GeneralResponse completeObjectiveResponse =
        GeneralResponse(success: true, description: 'Objective Complete');
    blocTest<ObjectivesListBloc, ObjectivesListState>(
      'Check flow of states when completing an objective',
      build: () {
        when(questRepo.completeObjective(any))
            .thenAnswer((_) async => completeObjectiveResponse);
        when(mockScanner.scan())
            .thenAnswer((_) async => "4_13_42.0_42.0_1");
        return ObjectivesListBloc(repository: questRepo, playerID: 42,
            scanner: mockScanner);
      },
      act: (bloc) => bloc.add(RequestQRCodeScanEvent(42, 4, 13)),
      expect: () => [
        QRCodeScanInProgress(),
        ObjectiveCompletionComplete(completeObjectiveResponse)
      ],
    );

    const GeneralResponse completeObjectiveResponseFail =
    GeneralResponse(success: true, description: 'Objective Completion Failed:'
        ' bad quest/objective ID');

    blocTest<ObjectivesListBloc, ObjectivesListState>(
    'Check flow of states when scan for an objective is incorrect',
    build: () {
      when(questRepo.completeObjective(any))
          .thenAnswer((_) async => completeObjectiveResponse);
      when(mockScanner.scan())
          .thenAnswer((_) async => "3_13_42.0_42.0_1");
      return ObjectivesListBloc(repository: questRepo, playerID: 42,
          scanner: mockScanner);
    },
    act: (bloc) => bloc.add(RequestQRCodeScanEvent(42, 4, 13)),
    expect: () => [
      QRCodeScanInProgress(),
      ObjectiveCompletionFailed()
    ],
  );
});

}
