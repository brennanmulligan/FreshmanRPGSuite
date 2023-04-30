import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:game_manager/repository/quest/delete_objective_request.dart';
import 'package:game_manager/repository/quest/quest_editing_response.dart';
import 'package:game_manager/repository/quest/upsert_quest_request.dart';
import 'package:meta/meta.dart';

import '../../../repository/player/basic_response.dart';
import '../../../repository/quest/objective_record.dart';
import '../../../repository/quest/quest_editing_request.dart';
import '../../../repository/quest/quest_repository.dart';

part 'quest_event.dart';

part 'quest_state.dart';

class QuestBloc extends Bloc<QuestEvent, QuestState> {
  final QuestRepository questRepository;

  QuestBloc({
    required this.questRepository,
  }) : super(QuestInitial()) {
    on<SendUpsertQuestEvent>((event, emit) async {
      emit(QuestLoading());

      BasicResponse response = await questRepository.upsertQuest(UpsertQuestRequest(id: event.id, title: event.title, description: event.description, xpGained: event.xpGained, triggerMapName: event.triggerMapName, triggerRow: event.triggerRow, triggerCol: event.triggerCol, objectivesForFulfillment: event.objectivesForFulfillment, completionActionType: event.completionActionType, startDate: event.startDate, endDate: event.endDate, easterEgg: event.easterEgg));

      emit(QuestComplete(response));
    });

    on<SendGetQuestEditingInformationEvent>((event, emit) async {
      emit(QuestLoading());

      QuestResponse response = await questRepository.getQuests(const QuestRequest());

      emit(QuestPageReady(response));
    });

    on<DeleteObjectiveEvent>((event, emit) async {
      emit(DeleteObjective());

      BasicResponse response = await questRepository.deleteObjective(DeleteObjectiveRequest(event.objectiveId, event.questId));

      emit(QuestComplete(response));
    });
  }
}
