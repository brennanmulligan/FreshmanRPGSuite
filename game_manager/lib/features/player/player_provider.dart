import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';
import '../network/network_provider.dart';
import 'player.dart';

///
/// Provider that watches all parts of a player sequence.
///
class PlayerProvider {
  ///
  /// The create player datasource.
  ///
  static final createPlayerDatasource = Provider<CreatePlayerDatasource>((ref) {
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

  ///
  /// The player controller.
  ///
  static final playerController =
      StateNotifierProvider.autoDispose<PlayerController, PlayerState>((ref) {
    return PlayerController(
      repository: ref.watch(playerRepository),
    );
  });
}
