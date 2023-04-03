import 'package:bloc/bloc.dart';
import 'package:companion_app/repository/quests_objectives_repository/all-objectives-request.dart';
import 'package:companion_app/repository/quests_objectives_repository/all-objectives-response.dart';
import 'package:companion_app/repository/quests_objectives_repository/complete-objective-request.dart';
import 'package:companion_app/repository/quests_objectives_repository'
    '/quests_objectives_repository.dart';
import 'package:companion_app/repository/shared/general_response.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/cupertino.dart';
import 'package:meta/meta.dart';

import '../../../model/barcode_scanner.dart';

part 'objectives_list_event.dart';

part 'objectives_list_state.dart';

class ObjectivesListBloc
    extends Bloc<ObjectivesListEvent, ObjectivesListState> {
  final QuestsObjectivesRepository repository;
  final int playerID;
  final BarcodeScanner scanner;

  ObjectivesListBloc({required this.repository, required this.playerID,
    required this.scanner})
      : super(ObjectivesListInitial()) {

    on<RequestObjectivesEvent>((event, emit) async {
      emit(ObjectivesListLoading());
      AllObjectivesResponse response = await repository
          .getAllObjectives(AllObjectivesRequest(playerID: playerID));
      emit(ObjectiveListLoaded(response));
    });

    on<RequestQRCodeScanEvent>((event, emit) async {
      emit(QRCodeScanInProgress());
      String barcodeScanRes = await scanner.scan();
      var parts = barcodeScanRes.split("_");
      var qrQuestID = int.parse(parts[0]);
      var qrObjectiveID = int.parse(parts[1]);
      var qrLatitude = double.parse(parts[2]);
      var qrLongitude = double.parse(parts[3]);
      var qrCheckLocation = parts[4];

      if ((event.questID == qrQuestID) && (event.objectiveID ==
          qrObjectiveID)) {
        GeneralResponse completionResponse = await repository.completeObjective(
            CompleteObjectiveRequest(
                playerID: playerID,
                questID: event.questID,
                objectiveID: event.objectiveID));

        emit(ObjectiveCompletionComplete(completionResponse));
      } else {
        emit(ObjectiveCompletionFailed());
      }
    });
    // on<CompleteObjectiveEvent>((event, emit) async {
    //   emit(ObjectiveCompletionInProgress());
    //   GeneralResponse response = await repository.completeObjective(
    //       CompleteObjectiveRequest(
    //           playerID: playerID,
    //           questID: event.questID,
    //           objectiveID: event.objectiveID));
    //   emit(ObjectiveCompletionComplete(response));
    // });

    // @override
    // Stream<ObjectivesListState> mapEventToState(ObjectivesListEvent event) async* {
    //   if (event is InitEvent) {
    //     final data = await repository.getAllObjectives(AllObjectivesRequest(playerID: playerID));
    //     yield ObjectiveListLoaded(data);
    //   }
    // }
  }
}
