import 'package:bloc/bloc.dart';
import 'package:companion_app/repository/quests_objectives_repository/all-objectives-request.dart';
import 'package:companion_app/repository/quests_objectives_repository/all-objectives-response.dart';
import 'package:companion_app/repository/quests_objectives_repository/complete-objective-request.dart';
import 'package:companion_app/repository/quests_objectives_repository'
    '/quests_objectives_repository.dart';
import 'package:companion_app/repository/shared/general_response.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/cupertino.dart';
import 'package:geolocator/geolocator.dart';
import 'package:meta/meta.dart';

import '../../../model/barcode_scanner.dart';
import 'package:companion_app/model/position_with_status.dart';

import '../../../model/location_utilities.dart';

part 'objectives_list_event.dart';

part 'objectives_list_state.dart';

class ObjectivesListBloc
    extends Bloc<ObjectivesListEvent, ObjectivesListState> {
  final QuestsObjectivesRepository repository;
  final int playerID;
  final BarcodeScanner scanner;
  final GeoLocatorWrapper geoLocator;

  ObjectivesListBloc(
      {required this.repository,
      required this.playerID,
      required this.scanner,
      required this.geoLocator})
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

      List<String> parts;
      int qrQuestID, qrObjectiveID;
      double qrLatitude, qrLongitude;
      String qrCheckLocation;

      try {
        parts = barcodeScanRes.split("_");
        qrQuestID = int.parse(parts[0]);
        qrObjectiveID = int.parse(parts[1]);
        qrLatitude = double.parse(parts[2]);
        qrLongitude = double.parse(parts[3]);
        qrCheckLocation = parts[4];
      } on FormatException catch (e) {
        emit(QRCodeCheckFailed());
        return;
      } on RangeError catch (e) {
        emit(QRCodeCheckFailed());
        return;
      }

      if (qrCheckLocation == "1") {
        PositionWithStatus location = await PositionWithStatus.getCurrentLocation(geoLocator);
        if (!location.valid) {
          emit(LocationCheckFailed("Location Permissions Not Granted"));
          return;
        }
        if (!geoLocator.locationMatches(
            location, qrLatitude, qrLongitude)) {
          emit(LocationCheckFailed(
              "You are not close enough to the target location"));
          return;
        }
      }

      if ((event.questID != qrQuestID)
          || (event.objectiveID != qrObjectiveID)) {
        emit(QRCodeCheckFailed());
        return;
      }

      GeneralResponse completionResponse =
          await repository.completeObjective(CompleteObjectiveRequest(
              playerID: playerID,
              questID: event.questID,
              objectiveID: event.objectiveID));
      emit(RestfulCompletionRequestComplete(completionResponse));
    });
  }
}
