import 'package:game_manager/features/player/data/data.dart';
import 'package:frpg_networking_api/networking/service_client/service_client.dart';

///
/// Template for creating a player
///
abstract class CreatePlayerDatasource {
  final ServiceClient sc;

  ///
  /// Constructor
  ///
  const CreatePlayerDatasource ({
    required this.sc,
  });

  ///
  /// Command to create a player
  ///
  Future<CreatePlayerResponse> createPlayer({
    required CreatePlayerRequest request,
  });
}