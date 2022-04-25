import 'package:frpg_networking_api/networking/result/result.dart';
import 'package:game_manager/features/player/data/data.dart';

///
/// Template for a player repository.
///
abstract class PlayerRepository {
  final CreatePlayerDatasource createPlayerDatasource;

  ///
  /// Constructor.
  ///
  const PlayerRepository({
    required this.createPlayerDatasource,
  });

  ///
  /// Template for creating a player.
  ///
  Future<Result<CreatePlayerResponse>> createPlayer({
    required CreatePlayerRequest request,
  });
}