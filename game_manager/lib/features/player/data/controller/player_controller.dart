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
  /// Managed state for plaeyr stuff.
  ///
  const factory PlayerState({
    @Default(false) showResponse,
    // @Default("") name,
    // @Default("") password,
    // @Default(-1) crew,
    // @Default(-1) major,
    // @Default(-1) section,
    CreatePlayerResponse? createPlayerResponse,
  }) = _PlayerState;

  ///
  /// Creating a private singleton instance of the state.
  ///
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
  }) : _repository = repository,
      super(state ?? const PlayerState());
  
  Future<void> createPlayer({
    required String name, required String password, required num crew, 
    required num major, required num section,
  }) async {
    final response = await _repository.createPlayer(
      request: CreatePlayerRequest(
        name: name, 
        password: password, 
        crew: crew, 
        major: major, 
        section: section,
      )
    );
    CreatePlayerResponse createPlayerResponse = response.when(
      data: (data) => data,
      failure: (failure) => const CreatePlayerResponse(
        responseType: PlayerResponseType.networkFailure
      )
    );

    state = state.copyWith(
      showResponse: true,
      createPlayerResponse: createPlayerResponse,
    );
  }
}
