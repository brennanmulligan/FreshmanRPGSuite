import 'package:bloc_test/bloc_test.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:game_manager/pages/quest/bloc/quest_bloc.dart';
import 'package:game_manager/repository/player/basic_response.dart';
import 'package:game_manager/repository/quest/quest_editing_response.dart';
import 'package:game_manager/repository/quest/quest_repository.dart';
import 'package:mockito/annotations.dart';
import 'package:mockito/mockito.dart';

import 'quest_test.mocks.dart';

class QuestRepositoryTest extends Mock implements QuestRepository {}

@GenerateMocks([QuestRepositoryTest])
Future<void> main() async {
  late MockQuestRepositoryTest questRepo;

  setUpAll(() {
    questRepo = MockQuestRepositoryTest();
  });

  group('Quest Tests: ', () {
    const BasicResponse good = BasicResponse(success: true, description: "Worked");

    blocTest<QuestBloc, QuestState>(
      "Check upsert flow",
      build: () {
        when(questRepo.upsertQuest(any)).thenAnswer((_) async => good);
        return QuestBloc(questRepository: questRepo);
      },
      act: (bloc) => bloc.add(SendUpsertQuestEvent(1, "", "", 0, "", 1, 2, 3, 4, "", "", true)),
      wait: const Duration(milliseconds: 500),
      expect: () => [QuestLoading(), QuestComplete(good)],
    );

    const QuestResponse goodEditingResponse = QuestResponse(true, quests: [], mapNames: [], completionActionTypes: [], objCompletionTypes: []);

    blocTest<QuestBloc, QuestState>(
      "Check get flow",
      build: () {
        when(questRepo.getQuests(any)).thenAnswer((_) async => goodEditingResponse);
        return QuestBloc(questRepository: questRepo);
      },
      act: (bloc) => bloc.add(SendGetQuestEditingInformationEvent()),
      wait: const Duration(milliseconds: 500),
      expect: () => [QuestLoading(), QuestPageReady(goodEditingResponse)],
    );
  });
}
