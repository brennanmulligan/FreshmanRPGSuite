import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:frpg_networking_api/frpg_networking_api.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'player.dart';

///
/// Provider that watches all parts of a player sequence.
///
class PlayerProvider {
  ///
  /// The player controller.
  /// 
  static final playercontroller =
    StateNotifierProvider.autoDispose<PlayerController, PlayerState>(
      (ref) {
        return PlayerController(repository: ref.watch(playerRepository),
      );
  });

  ///
  /// The create player datasource.
  ///
  static final createPlayerDatasource =
    Provider<CreatePlayerDatasource>((ref) {
      return CreatePlayerDatasourceHTTP(
        sc: ref.watch(NetworkProvider.serviceClient),
      );
  });

  ///
  /// The player repository
  ///
  static final playerRepository = Provider<PlayerRepository>((ref) {
    return PlayerRepositoryHTTP(
      createPlayerDatasource: ref.watch(createPlayerDatasource),
      );
  });
}