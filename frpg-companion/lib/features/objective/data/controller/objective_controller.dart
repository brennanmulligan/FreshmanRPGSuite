import 'dart:math';

import 'package:flutter/foundation.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:frpg_companion/features/objective/data/data.dart';
part 'objective_controller.freezed.dart';

///
/// Create a state for the controller.
///
@freezed
class ObjectiveState with _$ObjectiveState {
  ///
  /// Managed state for the objective stuff.
  ///
  const factory ObjectiveState({
    @Default(false) showResponse,
    @Default(0) playerID,
    @Default("") playerName,
    CompleteObjectiveResponse? objectiveResponse,
    FetchAllObjectiveResponse? fetchAllObjectiveResponse,
  }) = _ObjectiveState;

  ///
  /// Create a private singleton instance of the state.
  ///
  const ObjectiveState._();
}

///
/// Create a controller for all objective data.
///
class ObjectiveController extends StateNotifier<ObjectiveState> {
  final ObjectiveRepository _repository;

  ///
  /// Constructor.
  ///
  ObjectiveController({
    required ObjectiveRepository repository,
    ObjectiveState? state,
  })  : _repository = repository,
        super(state ?? const ObjectiveState());

  Future<void> fetchAllObjective({
    required num playerId,
  }) async {
    final response = await _repository.fetchAllObjectives(
      request: FetchAllObjectiveRequest(
        playerID: playerID,
      ),
    );
    FetchAllObjectiveResponse fetchAllObjectiveResponse = response.when(
      data: (data) => data,
      failure: (failure) => const FetchAllObjectiveResponse(
        information: [],
      ),
    );

    state = state.copyWith(
      fetchAllObjectiveResponse: fetchAllObjectiveResponse,
    );
  }

  // Update the state.

  ///
  /// Complete objective.
  ///
  Future<void> completeObjective({
    required num playerID,
    required num objectiveID,
    required num questID,
    num latitude = 0,
    num longitude = 0,
  }) async {
    final response = await _repository.completeObjective(
      request: CompleteObjectiveRequest(
        playerID: playerID,
        objectiveID: objectiveID,
        questID: questID,
      ),
    );
    // When we get a response, create an object and set its state.
    CompleteObjectiveResponse objectiveResponse = response.when(
      data: (data) => data,
      failure: (failure) => const CompleteObjectiveResponse(
        responseType: ObjectiveResponseType.networkFailure,
      ),
    );

    state = state.copyWith(
      showResponse: true,
      objectiveResponse: objectiveResponse,
    );

    await fetchAllObjective(
      playerId: state.playerID,
    );
  }

  ///
  /// Get list of objectives
  ///
  FetchAllObjectiveResponse? get fetchAllObjectiveResponse =>
      state.fetchAllObjectiveResponse;

  ///
  /// Get the objective response.
  ///
  CompleteObjectiveResponse? get objectiveResponse => state.objectiveResponse;

  ///
  /// Get the flag for showing a response.
  ///
  bool get showResponse => state.showResponse;

  ///
  /// Get the player ID
  ///
  num get playerID => state.playerID;

  ///
  ///
  ///
  String get playerName => state.playerName;

  ///
  ///
  ///
  void setPlayer({
    required String playerName,
    required num playerID,
  }) async {
    state = state.copyWith(
      playerName: playerName.capitalize(),
      playerID: playerID,
    );
  }

  ///
  /// Use a QR code to seed a CompleteObjectiveRequest,
  /// then send it for completion.
  ///
  Future<void> getDataFromCode({
    required String qrData,
    required ObjectiveInformation info,
    required num currentLong,
    required num currentLat,
  }) async {
    List<String> data = qrData.split('_');
    final useableData = data.length == 4 ? data : ['-1', '-1', '-1', '-1'];
    num questID = int.parse(useableData[0]);
    num objectiveID = int.parse(useableData[1]);
    num latitude = double.parse(useableData[2]);
    num longitude = double.parse(useableData[3]);

    debugPrint('Theirs lon:$longitude lat:$latitude');
    debugPrint('Ours lon:$currentLong lat:$currentLat');

    const tolerance = 0.01;

    const R = 6371;

    final dLat = (latitude - currentLat) * (pi / 180.0);
    final dLon = (longitude - currentLong) * (pi / 180.0);

    final a = (sin(dLat / 2) * sin(dLat / 2)) +
        (cos((currentLat) * (pi / 180.0)) * cos((latitude) * (pi / 180.0))) *
            (sin(dLon / 2) * sin(dLon / 2));

    final c = 2 * atan2(sqrt(a), sqrt(1 - a));
    final d = R * c;

    final latDiff =
        num.parse((currentLat.abs() - latitude.abs()).abs().toStringAsFixed(4));
    var lonDiff = num.parse(
        (currentLong.abs() - longitude.abs()).abs().toStringAsFixed(4));

    debugPrint('$lonDiff $latDiff D -> $d');

    if (d > tolerance) {
      const failure = CompleteObjectiveResponse(
        responseType: ObjectiveResponseType.outOfRange,
      );
      state = state.copyWith(
        showResponse: true,
        objectiveResponse: failure,
      );
    } else {
      if (questID != -1 &&
          objectiveID != -1 &&
          info.questID == questID &&
          info.objectiveID == objectiveID) {
        await completeObjective(
          playerID: state.playerID,
          objectiveID: objectiveID,
          questID: questID,
        );
      } else {
        const failure = CompleteObjectiveResponse(
          responseType: ObjectiveResponseType.objectiveNotValid,
        );
        state = state.copyWith(
          showResponse: true,
          objectiveResponse: failure,
        );
      }
    }
  }

  /// the scanned qr has to match the expected objective

}

extension StringExtension on String {
  String capitalize() {
    return "${this[0].toUpperCase()}${substring(1).toLowerCase()}";
  }
}
