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
    @Default(0) questID,
    @Default(0) objectiveID,
    @Default("") playerName,
    CompleteObjectiveResponse? objectiveResponse,
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

    // Update the state.
    state = state.copyWith(
      showResponse: true,
      objectiveResponse: objectiveResponse,
    );
  }

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
  /// Get the quest ID.
  ///
  num get questID => state.questID;

  ///
  /// Get the objective ID.
  ///
  num get objectiveID => state.objectiveID;

  ///
  ///
  ///
  String get playerName => state.playerName;

  ///
  /// Set the player ID.
  ///
  void setPlayer(num playerID, String name) {
    state = state.copyWith(
      playerID: playerID,
      playerName: name,
    );
  }

  ///
  /// Use a QR code to seed a CompleteObjectiveRequest,
  /// then send it for completion.
  ///
  Future<void> getDataFromCode({required String qrData}) async {
    List<String> data = qrData.split('_');
    final useableData = data.length == 2 ? data : ['-1', '-1'];
    num questID = int.parse(useableData.first);
    num objectiveID = int.parse(useableData.last);
    state = state.copyWith(
      questID: questID,
      objectiveID: objectiveID,
    );
    if (questID != -1 && objectiveID != -1) {
      await completeObjective(
        playerID: state.playerID,
        objectiveID: state.objectiveID,
        questID: state.questID,
      );
    }
  }
}
