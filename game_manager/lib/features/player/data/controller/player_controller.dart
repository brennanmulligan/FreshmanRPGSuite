import 'package:flutter/foundation.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:game_manager/features/player/data/data.dart';
part 'player_controller.freezed.dart';

///
/// Create a state for the controller.
///
@freezed
class PlayerState with _$PlayerState {
  ///
  /// Managed state for player stuff.
  ///
  const factory PlayerState({
    @Default(false) showResponse,
    //@Default(true) providedInvalidInfo,
    CreatePlayerResponse? createPlayerResponse,
  }) = _PlayerState;

  const PlayerState._();
}

class PlayerController extends StateNotifier<PlayerState> {
  final PlayerRepository _repository;

  ///
  /// Constructor.
  ///
  PlayerController({
    required PlayerRepository repository,
    PlayerState? state,
  })  : _repository = repository,
        super(state ?? const PlayerState());

  Future<void> createPlayer(
      String name, String password, num crew, num major, num section) async {
    debugPrint("in controller");
    final response = await _repository.createPlayer(
        request: CreatePlayerRequest(
      name: name,
      password: password,
      crew: crew,
      major: major,
      section: section,
    ));
    CreatePlayerResponse createPlayerResponse = response.when(
      data: (data) => data,
      failure: (failure) => const CreatePlayerResponse(
        responseType: PlayerResponseType.networkFailure,
      ),
    );

    state = state.copyWith(
      //providedInvalidInfo: createPlayerResponse.responseType == -1,
      showResponse: true,
      createPlayerResponse: createPlayerResponse,
    );
    debugPrint("out controller");
  }

  ///
  /// Get providedInvalidInfo
  ///
  //bool get providedInvalidInfo => state.providedInvalidInfo;

  get createPlayerResponse => state.createPlayerResponse;
}
